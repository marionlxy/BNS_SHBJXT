package com.krm.voteplateform.web.log.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.mybatis.plugins.pagination.Page;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.log.dao.PtLogMapper;
import com.krm.voteplateform.web.log.model.PtLog;
import com.krm.voteplateform.web.log.service.PtLogService;

@Service("ptLogService")
public class PtLogServiceImpl implements PtLogService<PtLog> {

	@Resource
	private PtLogMapper ptLogMapper;
	
	@Resource
	private SQLManager sqlManager;

	@Override
	public boolean saveLog(PtLog ptLog) {
		int insert = ptLogMapper.insert(ptLog);
		return true;
	}

	@Override
	public List<Map<String, Object>> listAll() {
		return ptLogMapper.listAll();
	}

	@Override
	public List<Map<String, Object>> findPageInfo(Map<String, Object> map) {
		return ptLogMapper.findPageInfo(map);
	}

	@Override
	public List<Map<String, Object>> findPageInfo1(Page page, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ptLogMapper.findPageInfo1(page,map);
	}

	@Override
	public boolean saveLogTest(Map<String, Object> map) {
		PtLog ptLog = new PtLog();
		ptLog.setDescription("22222");
		ptLog.setMethod("test1");
		ptLog.setRemoteAddr("192.168.1.1");
		sqlManager.setTableNamePrefix("test");
//		int insert = sqlManager.insert(PtLog.class, ptLog);
		
		ptLog.setId("1");
		sqlManager.updateTemplateById(ptLog);
//		sqlManager.updateTemplateById(c, paras);
		
//		sqlManager.deleteById(clazz, pkValue)
		
		
//		PtLog record = new PtLog();
//		record.setId(UUIDGenerator.getUUID());
//		record.setDescription("test2");
//		ptLogMapper.insert(record );
//		int i = 1/0;
		return false;
	}
	
}
