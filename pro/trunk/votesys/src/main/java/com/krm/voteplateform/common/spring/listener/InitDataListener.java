package com.krm.voteplateform.common.spring.listener;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ServletContextAware;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.web.synproject.contants.SynProjectContants;

/**
 * 初始化数据逻辑
 * 
 * @author JohnnyZhang
 */
public class InitDataListener implements InitializingBean, ServletContextAware {

	private static Logger logger = LoggerFactory.getLogger(InitDataListener.class);

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.info("容器初始化数据开始....");
		FileUtils.createDirectory(SynProjectContants.OTHER_FILES_PUBLIC);
		List<Map<String, Object>> queryForList = jdbcTemplate
				.queryForList("SELECT ID,CODE,RELATION_CODE,RELATION_NAME,NOTIFY_ADDR FROM PT_OUT_SYS_RELATION");
		logger.info("查询外部表结束，查询结果为:{}", JSON.toJSONString(queryForList));
		if (queryForList == null || queryForList.size() == 0) {
			return;
		}
		List<String> list = Lists.newArrayList();
		for (Map<String, Object> map : queryForList) {
			String code = map.get("CODE").toString();
			String relationCode = map.get("RELATION_CODE").toString();
			list.add(code + File.separator + relationCode);
			SpringContextHolder.putRelationOtherSysMap(code + "_" + relationCode, map);
		}
		// 创建文件夹
		createDir(list);
		logger.info("容器初始化数据结束.");
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * 创建文件夹
	 * 
	 * @param paths
	 */
	private void createDir(List<String> paths) {
		File file = null;
		for (int i = 0; i < paths.size(); i++) {
			for (int j = 0; j < SynProjectContants.OTHERSYS_FILE_ARRAY.length; j++) {
				file = new File(SynProjectContants.OTHERSYS_FILE_ARRAY[j] + File.separator + paths.get(i));
				file.mkdirs();
				file.setWritable(true, false);
			}
		}
	}

}
