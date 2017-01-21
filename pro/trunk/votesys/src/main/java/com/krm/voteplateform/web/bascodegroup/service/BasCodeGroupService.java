package com.krm.voteplateform.web.bascodegroup.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;

/**
 * 操作相关
 */
public interface BasCodeGroupService {
	
	
	/**
	 * 新增保存
	 * @param basCodeGroup
	 */
	Boolean saveBasCodeGroup(BasCodeGroup basCodeGroup,String code);
	
	/**
	 * 修改
	 * @param basCodeGroup
	 * @return 
	 */
	Boolean updateBasCodeGroup(BasCodeGroup basCodeGroup,String code);

	/**
	 * 删除
	 * @param basCodeGroup
	 */
	Boolean removeBasCodeGroup(BasCodeGroup basCodeGroup,String code);
	
	/**
	 * 根据编号查询细信息
	 * @param id
	 * @return
	 */
	BasCodeGroup loadById(String code,String id);
	/**
	 * 查询所有code
	 * @param id
	 * @return
	 */
	List<BasCodeGroup> selectAlls(String codeparameter);
	/**
	 * 查询所有下拉
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> findBasCodeGroupList();
	
	/*验证名称不能重复*/
	boolean selectName(Map<String, Object> paramap);
	

}
