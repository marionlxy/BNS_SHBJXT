package com.krm.voteplateform.web.resource.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.krm.voteplateform.web.login.model.LoginUser;
import com.krm.voteplateform.web.resource.model.BasResource;

public interface BaseResourceService {
	List<Map<String, Object>> listResource();

	Object getObject(String id);

	boolean saveObject(BasResource resource);

	List<BasResource> getTreeList();

	List<Map<String, Object>> loadResourceById(String cgId);

	List<Map<String, Object>> findMode();

	boolean updateMenu(BasResource baseResource);
	
	List<Map<String, Object>> findUserResourceByUserId(HttpServletRequest request,LoginUser loginUser);

}
