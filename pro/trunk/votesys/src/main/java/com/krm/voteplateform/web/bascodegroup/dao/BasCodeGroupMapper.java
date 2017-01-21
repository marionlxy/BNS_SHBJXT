package com.krm.voteplateform.web.bascodegroup.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;

/**
 * 操作相关
 */
public interface BasCodeGroupMapper {
	
	
	
	/**
	 * 新增保存
	 * @param info
	 */
	void saveBasCodeGroup(@Param("code") String code,@Param("basCodeGroup") BasCodeGroup basCodeGroup);
	
	/**
	 * 修改
	 * @param info
	 */
	void updateBasCodeGroup(BasCodeGroup basCodeGroup);

	/**
	 * 删除
	 * @param info
	 */
	void deleteBasCodeGroup(BasCodeGroup basCodeGroup);
	
	/**
	 * 根据编号查询信息
	 * @param placeId
	 * @return
	 */
	BasCodeGroup findById(@Param("code") String code,@Param("id") String id);
	
	/**
	 * 查询所有
	 * @param placeId
	 * @return
	 */
	List<BasCodeGroup> selectAlls(@Param("code") String code);
	
	/**
	 * 查询下拉列表
	 * @param 
	 * @return
	 */
	List<Map<String, Object>> findBasCodeGroupList(Map<String, Object> paramap);


}
