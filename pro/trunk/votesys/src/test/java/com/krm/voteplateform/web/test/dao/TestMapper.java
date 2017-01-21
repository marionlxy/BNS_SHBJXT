package com.krm.voteplateform.web.test.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;


public interface TestMapper {

	/**
	 * 测试分页
	 * 
	 * @param page
	 * @param otherMap 其他条件
	 * @return
	 */
	public List<Map<String, Object>> findTestPage(Pagination<Map<String, Object>> page, Map<String, Object> otherMap);

	public int insertTemp1(Map<String, String> param);
}
