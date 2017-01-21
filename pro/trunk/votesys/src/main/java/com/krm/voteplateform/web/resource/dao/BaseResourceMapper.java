package com.krm.voteplateform.web.resource.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.resource.model.BasResource;

public interface BaseResourceMapper {
	List<Map<String,Object>> listResource(Map<String, String> map);
	
	Object getObject(Map<String, String> map);

	int update(BasResource baseResource,String code);

	List<Map<String, Object>> listMenuResource(Map<String, String> mp);

	List<BasResource> getAllResources(Map<String, String> map);

	List<Map<String, Object>> getResourceById(Map<String, String> map);

	List<Map<String, Object>> findAllMode(Map<String, String> map);

	int update2(Map<String, Object> map);

	List<Map<String, Object>> findUserResourceByUserId(Map<String, Object> map);

	int hasChilds(Map<String, Object> mapfind);

	List<Map<String, Object>> listChilds(Map<String, Object> mapfind);

	Map<String, Object> getParenTMenu(Map<String, Object> map);
	
	/*查询系统固定菜单*/
	List<Map<String, Object>>  listSyStemMenu(Map<String, Object> map);

	Map<String, Object> getParentidByType(Map<String, Object> map);

}
