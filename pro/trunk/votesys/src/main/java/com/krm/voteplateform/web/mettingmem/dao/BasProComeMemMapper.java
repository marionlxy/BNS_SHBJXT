package com.krm.voteplateform.web.mettingmem.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.mettingmem.model.BasProComeMem;

/**
 * 操作相关
 */
public interface BasProComeMemMapper {
	
	
	
	/**
	 * 新增保存
	 * @param info
	 */
	void saveBasProComeMem(BasProComeMem basProComeMem);
	
	/**
	 * 修改
	 * @param info
	 */
	void updateBasProComeMem(BasProComeMem basProComeMem);

	/**
	 * 删除
	 * @param info
	 */
	void deleteBasProComeMem(BasProComeMem basProComeMem);
	
	/**
	 * 根据编号查询信息
	 * @param placeId
	 * @return
	 */
	BasProComeMem findById(String id);
	/**
	 * 根据编号查询信息
	 * @param placeId
	 * @return
	 */
	List<Map<String, Object>> loadByMapId(Map<String, Object> objectmap);
	


}
