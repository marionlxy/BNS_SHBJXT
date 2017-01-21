package com.krm.voteplateform.web.menulistconf.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.menulistconf.model.BasMenuListConf;
import com.krm.voteplateform.web.sys.model.BasDict;


public interface MenuListConfMapper {

	List<Map<String, Object>> getMenuAll(Map<String, String> map);

	List<Map<String, Object>> listField(Map<String, String> map);

	List<String> getTypeList();

	List<Map<String, Object>> getDataSource(Map<String, String> map);

	Map<String, Object> getMenuById(Map<String, String> map);

	int updateTemplateById(Map<String, Object> map);

	Map<String, Object> getMenuByRepid(Map<String, String> map);

	List<Map<String, Object>> getMenuUseFulAll(Map<String, String> map);

	List<Map<String, Object>> findMenuListConf(Map<String, String> map);
	
	String getDataSourceNameById(Map<String, String> map3);

}
