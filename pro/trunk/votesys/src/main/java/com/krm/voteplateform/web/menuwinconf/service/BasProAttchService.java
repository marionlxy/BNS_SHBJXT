package com.krm.voteplateform.web.menuwinconf.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.menuwinconf.model.BasProAttch;

public interface BasProAttchService {
	public int bachInsertFile(List<BasProAttch> list);
	
	//获取附件类型
	List<Map<String, Object>> getattchTypeList(Map<String, Object> params);

	public List<Map<String, Object>> getProAttchList(HashMap<String, Object> params);

	public boolean checkFileName(Map<String, Object> params);
	
	public BasProAttch getOneAttchType(Map<String, Object> params);
	
	public int deleteOneAttchType(Map<String, Object> params);
}
