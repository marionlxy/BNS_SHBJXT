package com.krm.voteplateform.web.log.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.common.mybatis.plugins.pagination.Page;

public interface PtLogService<PtLog> {

	public boolean saveLog(PtLog ptLog);

	public List<Map<String, Object>> listAll();
	
	public List<Map<String, Object>> findPageInfo(Map<String, Object> map);
	
	public List<Map<String, Object>> findPageInfo1(Page page,Map<String, Object> map);
	
	public boolean saveLogTest(Map<String,Object> map);
}
