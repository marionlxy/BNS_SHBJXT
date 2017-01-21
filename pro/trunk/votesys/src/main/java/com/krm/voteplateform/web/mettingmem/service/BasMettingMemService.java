package com.krm.voteplateform.web.mettingmem.service;

import java.util.Map;








import com.krm.voteplateform.web.mettingmem.model.BasMettingMem;

/**
 * 操作相关
 */
public interface BasMettingMemService {
	
	
	/**
	 * 新增保存
	 * @param map
	 */
	void saveBasMettingMem(Map<String, Object> map);
	
	
	/**
	 * 修改
	 * @param basMettingMem
	 */
	void updateBasMettingMem(BasMettingMem basMettingMem);

	/**
	 * 删除
	 * @param basMettingMem
	 */
	void removeBasMettingMem(BasMettingMem basMettingMem);
	
	/**
	 * 根据编号查询细信息
	 * @param id
	 * @return
	 */
	BasMettingMem loadById(String id);


	Boolean findCommisionVoteAuths(Map<String, String> map);

	/*批量保存*/
	Boolean saveMemMettingList(Map<String, Object> params);


}
