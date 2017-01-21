package com.krm.voteplateform.autotask.task;

import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.krm.voteplateform.autotask.dao.BaseAutoTaskDao;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.common.utils.LogUtils;
import com.krm.voteplateform.web.task.run.TaskJobRun;
import com.krm.voteplateform.web.util.PropertiesUtils;

/**
 * 同步临时数据到实际表中
 * 
 * @author JohnnyZhang
 */
public class TemDataToRealDataTask {
	private static Logger logger = LogUtils.getTaskLog();

	// 默认密码
	private static String DEFAULT_PASS = PropertiesUtils.getValue("synuser.default.pass");

	// 删除用户
	private static final String DELETE_USER_SQL = "DELETE FROM PT_PLATEFORM_USER";
	// 删除机构
	private static final String DELETE_ORG_SQL = "DELETE FROM PT_PLATEFORM_ORG";
	// 插入用户数据
	private static final String INSERT_USER_SQL = "INSERT INTO PT_PLATEFORM_USER A (A.ID,A.PORG_ID,A.USER_NAME,A.REAL_NAME,A.POST,A.PHONE) "
			+ "SELECT B.C_OID,B.C_ORGCODE,B.C_USERNAME,B.C_NAME,C.C_ITEM_NAME,B.C_MOBILE FROM TEMP_FS_OPMIS_HR_PSN B LEFT JOIN TEMP_FS_OPMIS_HR_ITEMCODE C "
			+ "ON B.C_DUTY = C.C_ITEM_CODE AND C.C_CLASS_CODE='CODE_ZW'";
	// 插入用户密码表数据
	private static final String INSERT_USERPASSWORD_SQL = "INSERT INTO PT_PLATEFORM_USER_PASS A(A.ID,A.LOGIN_PASSWORD) "
			+ "SELECT B.ID,'"
			+ DEFAULT_PASS
			+ "' AS LOGIN_PASSWORD FROM PT_PLATEFORM_USER B WHERE B.ID NOT IN(SELECT ID FROM PT_PLATEFORM_USER_PASS)";
	// 插入机构表数据
	private static final String INSERT_ORG_SQL = "INSERT INTO PT_PLATEFORM_ORG A (A.ID,A.PARENT_ID,A.ORGNAME,A.ORGTYPE,A.CODE) "
			+ "SELECT B.C_CODE,B.C_PARENTUNITCODE,B.C_NAME,B.C_JGLX,B.C_ORGUNITCODE FROM TEMP_FS_OPMIS_HR_ORG B";

	/**
	 * 同步用户及机构临时表数据到本地数据
	 * 
	 * @param taskDate 任务日期
	 * @param taskId 任务编号
	 * @throws Exception
	 */
	public void saveTemp2Real(String taskDate, String taskId) throws Exception {
		logger.info("开始同步用户及机构临时表数据到本地数据，任务日期{},任务编号{}", taskDate, taskId);
		BaseAutoTaskDao baseAutoTaskDao = SpringContextHolder.getBean("baseAutoTaskDao");
		baseAutoTaskDao.init();
		JdbcTemplate jdbcTemplate = baseAutoTaskDao.getJdbcTemplate();
		TransactionDefinition definition = baseAutoTaskDao.getTransactionDefinition();
		PlatformTransactionManager platformTransactionManager = baseAutoTaskDao.getPlatformTransactionManager();
		TransactionStatus status = null;
		try {
			status = platformTransactionManager.getTransaction(definition);
			logger.info("开始删除用户表中数据");
			int update = jdbcTemplate.update(DELETE_USER_SQL);
			logger.info("共删除" + update + "条数据");

			logger.info("开始同步用户表中数据");
			update = jdbcTemplate.update(INSERT_USER_SQL);
			logger.info("共同步" + update + "条数据");

			logger.info("开始删除机构表中数据");
			update = jdbcTemplate.update(DELETE_ORG_SQL);
			logger.info("共删除" + update + "条数据");

			logger.info("开始同步机构表中数据");
			update = jdbcTemplate.update(INSERT_ORG_SQL);
			logger.info("共同步" + update + "条数据");

			logger.info("开始同步用户密码表中数据");
			update = jdbcTemplate.update(INSERT_USERPASSWORD_SQL);
			logger.info("共同步" + update + "条数据");

			baseAutoTaskDao.commit(status);// 事务提交
		} catch (Exception e) {
			TaskJobRun.writeErrLog(e, taskId, taskDate, "同步用户及机构临时表数据到本地数据发生异常");
			logger.error("同步用户及机构临时表数据到本地数据发生异常", e);
			baseAutoTaskDao.rollback(status);// 事务回滚
			throw e;
		}
	}
}
