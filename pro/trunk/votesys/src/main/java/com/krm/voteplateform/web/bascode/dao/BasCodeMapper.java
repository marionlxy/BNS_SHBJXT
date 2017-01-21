package com.krm.voteplateform.web.bascode.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.voteplateform.web.bascode.model.BasCode;

/**
 * 操作相关
 */
public interface BasCodeMapper {
	
	
	
	/**
	 * 新增保存
	 * @param info
	 */
	void saveBasCode(BasCode basCode);
	
	/**
	 * 修改
	 * @param info
	 */
	void updateBasCode(BasCode basCode);

	/**
	 * 删除
	 * @param info
	 */
	void deleteBasCode(BasCode basCode);
	
	/**
	 * 根据编号查询信息
	 * @param placeId
	 * @return
	 */
	BasCode findById(@Param("code") String code,@Param("id") String id);
	/**
	 * 根据编号组查询
	 * @param placeId
	 * @return
	 */
	List<Map<String, Object>> selectByCode(@Param("code") String code,@Param("id") String cgId);

	List<Map<String, Object>> getCodeGroup(Map<String, Object> map);
	


}
