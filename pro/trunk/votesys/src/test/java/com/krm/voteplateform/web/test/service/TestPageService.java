package com.krm.voteplateform.web.test.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;

public interface TestPageService {

	List<Map<String, Object>> findTestPage(Pagination<Map<String, Object>> page, Map<String, Object> otherMap);

	Object updateTest();
	
	//Mybatis insert
	int insertTemp1();
}