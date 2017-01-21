package com.krm.voteplateform.web.log.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.common.mybatis.plugins.DyTableModel;
import com.krm.voteplateform.common.mybatis.plugins.pagination.Page;
import com.krm.voteplateform.web.log.model.PtLog;

public interface PtLogMapper {

	int insert(PtLog record);
	
	List<Map<String,Object>> listAll();
	
	List<Map<String,Object>> findPageInfo(Map<String, Object> map);
	List<Map<String,Object>> findPageInfo1(Page page,Map<String, Object> map);
	List<Map<String,Object>> findPageInfo2(Page page,DyTableModel dyTableModel);
	List<Map<String,Object>> findLists(Map<String, Object> map);
	int insertTest(Map<String,Object> map);
}
