package com.krm.voteplateform.web.menwingrouconf.dao;

import com.krm.voteplateform.web.menwingrouconf.model.BasMenWinGrouConf;

/**
 * 菜单窗体拓展明细分组配置表操作相关
 */
public interface BasMenWinGrouConfMapper {
	
	
	
	/**
	 * 新增保存菜单窗体拓展明细分组配置表
	 * @param info
	 */
	void saveBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf);
	
	/**
	 * 修改菜单窗体拓展明细分组配置表
	 * @param info
	 */
	void updateBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf);

	/**
	 * 删除菜单窗体拓展明细分组配置表
	 * @param info
	 */
	void deleteBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf);
	
	/**
	 * 根据编号查询菜单窗体拓展明细分组配置表信息
	 * @param placeId
	 * @return
	 */
	BasMenWinGrouConf findById(String id);
	


}
