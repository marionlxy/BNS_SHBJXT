package com.krm.voteplateform.web.mettingmem.service;

import java.util.Map;






import com.krm.voteplateform.web.mettingmem.model.BasProComeMem;

/**
 * 操作相关
 */
public interface BasProComeMemService {
	
	
	/**
	 * 新增保存
	 * @param basProComeMem
	 */
	Boolean saveBasProComeMem(Map<String,String> map);
	
	/**
	 * 修改
	 * @param basProComeMem
	 */
	void updateBasProComeMem(BasProComeMem basProComeMem);

	/**
	 * 删除
	 * @param basProComeMem
	 */
	void removeBasProComeMem(BasProComeMem basProComeMem);
	
	/**
	 * 根据编号查询细信息
	 * @param id
	 * @return
	 */
	BasProComeMem loadById(String id);

	Boolean loadByMapId(Map<String, Object> objectmap);

	/*批量保存投票意见*/
	Boolean saveBatchBasProComeMemList(Map<String, Object> params);
	

	

}
