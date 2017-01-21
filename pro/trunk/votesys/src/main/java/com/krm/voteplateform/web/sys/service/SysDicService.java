package com.krm.voteplateform.web.sys.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.sys.model.BasDict;

public interface SysDicService {

	/**
	 * @查询编辑项目申请分类明细字典数据
	 * @param code session获取
	 * @return
	 */
	List<Map<String, Object>> findSysList(String category);
	/**
	 * 
	 * @param id
	 * @param codes
	 * @return
	 */
	BasDict selectDicFields(String id);
	/**
	 * 
	 * @param id
	 * @param mapCnName
	 * @param mapPrecision
	 * @param codes
	 * @return
	 */
	//boolean editDicField(String id,String mapCnName);
	boolean editDicFields(String id, String mapCnName);
	/**
	 * 查询下拉列表
	 * @author lixy
	 * @return
	 * @since JDK 1.7
	 */
	List<Map<String, Object>> findSelectSysList(String resid,String functionCode);
	
	Boolean saveUpState(String basdicts);
	
	
	
	

	
	
	
	
	//List<Map<String, Object>> findSysTypeList(String category);

	
	

	

	
	

	

}
