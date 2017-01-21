package com.krm.voteplateform.web.sys.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.sys.model.BasDict;

/**
 * 
 *
 * @author 
 * @version 
 * @since 
 */
public interface SysDicMapper {

	List<Map<String, Object>> findSysList(Map<String, String> map);

	/**
	 * @author zhangYuHai
	 * @param map
	 * @return 查询字典编辑映射名称与精度页面
	 */
	BasDict selectDicFields(Map<String, String> map);

	/**
	 * 
	 * @param map
	 * @return
	 */
	int updateField(Map<String, Object> map);

	int updateFields(Map<String, Object> map);
	/*
	 * 查询下拉列表
	 * */
	List<Map<String, Object>> findSelectSysList(Map<String, String> map);

	
	
}
