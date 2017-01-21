package com.krm.voteplateform.web.function.service;

import java.util.List;
import java.util.Map;


import com.krm.voteplateform.web.function.model.BasFunConf;

public interface PtBasFunConfService {
	//查询列表
	List<Map<String, Object>> selectAll(String Mlcid);
	//增加保存
	boolean savaFunctionTable(BasFunConf BasFunConf);
	//验证编码
	boolean selectSysCode(String functionCode);
	//修改
	BasFunConf selectCommtext(String id);
	//保存更新状态(是否启用)
	Boolean saveUpdate(String basdicts);
	//字段tempName的连表  查询
	List<Map<String, Object>> selectpull();
	//传第二个值name
	String gettempNameById(String tempId);
	//获取所有功能列表
	List<Map<String, Object>> findBtnBasFunConfWind(Map<String, String> params);
	
}
