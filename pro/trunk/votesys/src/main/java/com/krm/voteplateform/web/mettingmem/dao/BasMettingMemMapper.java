package com.krm.voteplateform.web.mettingmem.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.mettingmem.model.BasMettingMem;

/**
 * 操作相关
 */
public interface BasMettingMemMapper {
	
	
	
	/**
	 * 新增保存
	 * @param info
	 */
	void saveBasMettingMem(BasMettingMem basMettingMem);
	
	/**
	 * 修改
	 * @param info
	 */
	void updateBasMettingMem(BasMettingMem basMettingMem);

	/**
	 * 删除
	 * @param info
	 */
	void deleteBasMettingMem(BasMettingMem basMettingMem);
	
	/**
	 * 根据编号查询信息
	 * @param placeId
	 * @return
	 */
	BasMettingMem findById(String id);

	List<Map<String, Object>>  findByMapId(Map<String, Object> map);
	
	


}
