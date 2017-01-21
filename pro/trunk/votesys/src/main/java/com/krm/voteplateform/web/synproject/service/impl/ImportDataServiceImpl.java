package com.krm.voteplateform.web.synproject.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.VoteSqlManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.common.utils.LogUtils;
import com.krm.voteplateform.web.basProject.model.BasProject;
import com.krm.voteplateform.web.menuwinconf.model.BasProAttch;
import com.krm.voteplateform.web.synproject.model.BasProjectXmlBean;
import com.krm.voteplateform.web.synproject.service.ImportDataService;

@Service("importDataService")
public class ImportDataServiceImpl implements ImportDataService {

	@Resource
	private VoteSqlManager voteSqlManager;

	@Resource
	private JdbcTemplate jdbcTemplate;

	private static final String SELECT_SQL = "select project_id as projectId from ${tp}_bas_project where other_sys_key = ?";

	@Override
	public void saveData(BasProjectXmlBean basProjectXmlBean) {
		LogUtils.getSynProject().info("开始向DB中添加数据...");
		BasProject basProject = basProjectXmlBean.getBasProject();
		List<BasProAttch> listAttch = basProjectXmlBean.getListBasProAttch();
		String code = basProjectXmlBean.getCode();
		// 取得其他系统主键
		String otherSysKey = basProjectXmlBean.getId();
		basProject.setOtherSysKey(otherSysKey);
		String jsonString = JSON.toJSONString(basProject);
		LogUtils.getSynProject().info("开始处理项目信息{}...", jsonString);
		this.deleteRecords(otherSysKey, code);
		SQLManager sqlManager = voteSqlManager.getSqlManager();
		sqlManager.setTableNamePrefix(code);
		int insert = sqlManager.insert(BasProject.class, basProject);
		LogUtils.getSynProject().info("插入项目信息{}结束，插入条数{}.", jsonString, insert);
		String projectId = basProject.getProjectId();
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		for (BasProAttch basProAttch : listAttch) {
			basProAttch.setProjectId(projectId);
			basProAttch.setOtherStsValue(otherSysKey);
			basProAttch.setCreateBy("sysCreate");
			basProAttch.setCreateTime(nowTimestamp);
			basProAttch.setUpdateBy("sysCreate");
			basProAttch.setUpdateTime(nowTimestamp);
			basProAttch.setDelFlag("0");
		}
		String jsonString2 = JSON.toJSONString(listAttch);
		LogUtils.getSynProject().info("开始插入批量插入项目附件信息{}...", jsonString2);
		voteSqlManager.insertBatch(code, BasProAttch.class, listAttch);
		LogUtils.getSynProject().info("插入批量插入项目附件信息{}结束,应插入{}条", jsonString2, listAttch.size());
	}

	private void deleteRecords(String otherSysKey, String code) {
		SQLManager sqlManager = voteSqlManager.getSqlManager();
		// 防止误删
		String tpk = StringUtils.isNotEmpty(otherSysKey) ? otherSysKey : "1_abc2$_0";
		String selSql = StringUtils.replace(SELECT_SQL, "${tp}", code);
		List<Map<String, Object>> query = jdbcTemplate.queryForList(selSql, tpk);
		LogUtils.getSynProject().info("运行SQL文:{},参数:{}，查询当前项目表中是否含有数据,查询结果条数为{}", selSql, tpk,
				query != null ? query.size() : 0);
		// 存入需要删除的附件表的相关外键
		List<String> list = Lists.newArrayList();
		if (query != null && query.size() > 0) {
			for (Map<String, Object> map : query) {
				Object object = map.get("projectId");
				if (object != null && StringUtils.isNotEmpty(object.toString())) {
					list.add("'" + object.toString() + "'");
				}
			}
			sqlManager.setTableNamePrefix(code);
			sqlManager.deleteById(BasProject.class, tpk);
			if (!list.isEmpty()) {
				String dels = "delete from " + code + "_bas_pro_attch where project_id in("
						+ StringUtils.join(list, ",") + ")";
				LogUtils.getSynProject().info("运行SQL文:{}删除项目附件表数据", dels);
				SQLReady p = new SQLReady(dels);
				int executeUpdate = sqlManager.executeUpdate(p);
				LogUtils.getSynProject().info("运行SQL文:{}删除项目附件表数据，删除了{}条", dels, executeUpdate);
			}
		}
	}
}
