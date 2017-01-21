package com.krm.voteplateform.web.bascode.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.bascode.model.BasCode;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;

/**
 * 操作相关
 */
public interface BasCodeService {
	
	
	/**
	 * 新增保存
	 * @param basCode
	 * @return 
	 */
	Boolean saveBasCode(BasCode basCode,String code);
	
	/**
	 * 修改
	 * @param basCode
	 */
	Boolean updateBasCode(String code,BasCode basCode);

	/**
	 * 删除
	 * @param basCode
	 */
	Boolean removeBasCode(String code,BasCode basCode);
	
	/**
	 * 根据编号查询细信息
	 * @param id
	 * @return
	 */
	BasCode loadById(String code,String id);
	/**
	 *查询编号组编号信息
	 * @author lixy
	 * @param currentCommissionCode
	 * @param cgId
	 * @return
	 * @since JDK 1.7
	 */
	List<Map<String, Object>> selectByCode(String currentCommissionCode, String cgId);
	

}
