package com.krm.voteplateform.autotask.task;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.google.common.collect.Lists;
import com.krm.voteplateform.autotask.dao.BaseAutoTaskDao;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.common.utils.LogUtils;
import com.krm.voteplateform.web.task.run.TaskJobRun;

/**
 * 同步的文件入库处理
 * 
 * @author JohnnyZhang
 */
public class FileToTempDbTask {

	private static Logger logger = LogUtils.getTaskLog();

	private BaseAutoTaskDao baseAutoTaskDao;
	private static final String PERSON_FILE_NAME = "OPMIS_HR_PSN_999009_?.txt";
	private static final String ORG_FILE_NAME = "OPMIS_HR_ORG_999009_?.txt";
	private static final String ITEMCODE_FILE_NAME = "OPMIS_HR_ITEMCODE_999009_?.txt";
	private static final String SPLIT_CHAR = "";
	// 删除临时用户表数据
	private static final String DELETE_TEMP_USER_SQL = "DELETE FROM TEMP_FS_OPMIS_HR_PSN";
	// 添加临时用户表数据
	private static final String INSERT_USER_SQL = "INSERT INTO TEMP_FS_OPMIS_HR_PSN(C_OID,C_NAME,C_CODE,C_EMPLOYEESTATUS,"
			+ "C_LARGEEMPTYPE,C_EMPTYPE,C_JOBCODE,C_DUTY,C_ORGCODE,C_UNITCODE,C_GENDER,C_BIRTHDAY,C_IDCARD,C_BEGINWORKDATE,C_FINANCEDATE,"
			+ "C_MOBILE,C_KNOWLEDGE,C_DEGREE,C_ZCLEVEL,C_ZGZC,C_HIREDATE,C_JOINUNITDATE,C_DIMISSIONDATE,C_OPERATETIME,C_IDTYPE,C_USERNAME) "
			+ "VALUES(?)";
	// 删除临时机构表数据
	private static final String DELETE_TEMP_ORG_SQL = "DELETE FROM TEMP_FS_OPMIS_HR_ORG";
	// 添加临时机构表数据
	private static final String INSERT_ORG_SQL = "INSERT INTO TEMP_FS_OPMIS_HR_ORG(C_OID_ORGUNIT,C_CODE,C_NAME,C_CATEGORY,"
			+ "C_LAY,C_JGLX,C_JGJGBZ,C_QZHTBZ,C_STATUS,C_PARENTUNITCODE,C_ORGUNITCODE,C_ZZHM,C_POSTALCODE,C_ISLEAF,C_YYZK,C_VALIDDATE,"
			+ "C_YWKSSJ,C_YWJSSJ,C_ADDRESS,C_FZRBH,C_HXCODE) VALUES(?)";
	// 删除数据字典表数据
	private static final String DELETE_ITEMCODE_SQL = "DELETE FROM TEMP_FS_OPMIS_HR_ITEMCODE";
	// 添加数据字典表数据
	private static final String INSERT_ITEMCODE_SQL = "INSERT INTO TEMP_FS_OPMIS_HR_ITEMCODE(C_CLASS_CODE,C_CLASS_NAME,C_ITEM_CODE,C_ITEM_NAME) VALUES(?)";

	public FileToTempDbTask() {
	}

	private List<List<String>> getSqlList(List<String> readLines, String baseSql, String type) {
		List<List<String>> sqlList = Lists.newArrayList();
		if (readLines != null && readLines.size() > 0) {
			List<String> tempList = null;
			String instring = null;
			if (readLines != null && readLines.size() > 0) {
				for (int i = 0; i < readLines.size(); i++) {
					if (i % 1000 == 0) {
						tempList = Lists.newArrayList();
						sqlList.add(tempList);
					}
					instring = readLines.get(i);
					instring = StringUtils.replace(instring, SPLIT_CHAR, ",");
					instring = StringUtils.replace(instring, ",,", ",null,");
					instring = StringUtils.replace(instring, ",,", ",null,");
					instring = "'" + StringUtils.replace(instring, ",", "','")+"'";
					instring = StringUtils.replace(instring, "'null'", "null");
					if ("0".equals(type)) {// 用户
						tempList.add(StringUtils.replace(INSERT_USER_SQL, "?", instring));
					} else if ("1".equals(type)) {// 机构
						tempList.add(StringUtils.replace(INSERT_ORG_SQL, "?", instring));
					} else if ("2".equals(type)) {// 编码
						tempList.add(StringUtils.replace(INSERT_ITEMCODE_SQL, "?", instring));
					}
				}
			}
		}
		return sqlList;
	}
	
	/**
	 * 他系统传递的OA数据
	 * 
	 * @param path
	 * @param taskDate
	 * @param taskId
	 */
	public void fileToDb(String path, String taskDate, String taskId) throws Exception {
		logger.info("开始进行同步人力资源文件入库处理，参数[请求文件路径:{},任务日期:{},任务编号:{}]", path, taskDate, taskId);
		this.baseAutoTaskDao = SpringContextHolder.getBean("baseAutoTaskDao");
		this.baseAutoTaskDao.init();
		// 格式化日期
		taskDate = taskDate.replace("-", "");
		// 若路径不以路径分隔符号结束
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		path += taskDate;
		JdbcTemplate jdbcTemplate = baseAutoTaskDao.getJdbcTemplate();
		TransactionDefinition definition = baseAutoTaskDao.getTransactionDefinition();
		PlatformTransactionManager platformTransactionManager = baseAutoTaskDao.getPlatformTransactionManager();
		TransactionStatus status = null;
		try {
			// 1.同步数据字典数据
			logger.info("1.开始同步人力资源数据字典数据>>>");
			String filepath = path + File.separator + StringUtils.replace(ITEMCODE_FILE_NAME, "?", taskDate);
			File realFile = new File(filepath);
			List<String> readLines = FileUtils.readLines(realFile, "GBK");
			List<List<String>> readTxtToItemSql = getSqlList(readLines, INSERT_ITEMCODE_SQL, "2");
			long insertCount = batchUpdateUtil(readTxtToItemSql, platformTransactionManager, jdbcTemplate, status,
					definition, DELETE_ITEMCODE_SQL);
			logger.info("同步人力资源数据字典数据{}条", insertCount);
			// 2.同步人力资源用户表数据
			logger.info("2.开始同步人力资源用户表数据>>>");
			// 人员文件实际路径
			filepath = path + File.separator + StringUtils.replace(PERSON_FILE_NAME, "?", taskDate);
			realFile = new File(filepath);
			readLines = FileUtils.readLines(realFile, "GBK");
			List<List<String>> readTxtToUserSql = getSqlList(readLines, INSERT_USER_SQL, "0");
			insertCount = batchUpdateUtil(readTxtToUserSql, platformTransactionManager, jdbcTemplate, status,
					definition, DELETE_TEMP_USER_SQL);
			logger.info("同步人力资源用户表数据{}条", insertCount);
			// 3.同步人力资源机构表数据
			logger.info("3.开始同步人力资源机构表数据>>");
			filepath = path + File.separator + StringUtils.replace(ORG_FILE_NAME, "?", taskDate);
			realFile = new File(filepath);
			readLines = FileUtils.readLines(realFile, "GBK");
			List<List<String>> readTxtToOrgSql = getSqlList(readLines, INSERT_ORG_SQL, "1");
			insertCount = batchUpdateUtil(readTxtToOrgSql, platformTransactionManager, jdbcTemplate, status,
					definition, DELETE_TEMP_ORG_SQL);
			logger.info("同步人力资源机构表数据{}条", insertCount);
			logger.info("同步人力资源文件处理完成!");
		} catch (Exception e) {
			logger.error("同步人力资源文件处理完成发生异常！", e);
			TaskJobRun.writeErrLog(e, taskId, taskDate, "同步人力资源文件处理完成发生异常！");
			throw e;
		}
	}

	/**
	 * 批量处理SQL文
	 * 
	 * @param sqlList sql文List
	 * @param platformTransactionManager
	 * @param jdbcTemplate
	 * @param status
	 * @param definition
	 * @param otherSql 其他SQL文件
	 * @return
	 * @throws Exception
	 */
	private long batchUpdateUtil(List<List<String>> sqlList, PlatformTransactionManager platformTransactionManager,
			JdbcTemplate jdbcTemplate, TransactionStatus status, TransactionDefinition definition, String otherSql)
			throws Exception {
		long insertCount = 0;// 记录插入的数量
		boolean flag = (otherSql == null || "".equals(otherSql));
		if (!sqlList.isEmpty()) {
			int index = 0;
			for (List<String> list : sqlList) {// 每1000条SQL执行一次
				try {
					status = platformTransactionManager.getTransaction(definition);
					if (index == 0 && !flag) {
						index = 1;
						jdbcTemplate.update(otherSql);
					}
					jdbcTemplate.batchUpdate((String[]) list.toArray(new String[list.size()]));
					insertCount += list.size();
					baseAutoTaskDao.commit(status);
				} catch (Exception e) {
					logger.error("执行发生SQL文发生异常", e);
					baseAutoTaskDao.rollback(status);
					throw e;
				}
			}
		} else {
			if (!flag) {
				try {
					status = platformTransactionManager.getTransaction(definition);
					jdbcTemplate.update(otherSql);
					baseAutoTaskDao.commit(status);
				} catch (Exception e) {
					logger.error("执行发生SQL文发生异常", e);
					baseAutoTaskDao.rollback(status);
					throw e;
				}
			}
		}
		return insertCount;
	}

}
