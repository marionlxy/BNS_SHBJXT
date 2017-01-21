package com.krm.voteplateform.web.menwingrouconf.service;

import com.krm.voteplateform.web.menwingrouconf.model.BasMenWinGrouConf;

/**
 * 菜单窗体拓展明细分组配置表操作相关
 */
public interface BasMenWinGrouConfService {
	
	
	/**
	 * 新增保存菜单窗体拓展明细分组配置表
	 * @param basMenWinGrouConf
	 */
	void saveBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf);
	
	/**
	 * 修改菜单窗体拓展明细分组配置表
	 * @param basMenWinGrouConf
	 */
	void updateBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf);

	/**
	 * 删除菜单窗体拓展明细分组配置表
	 * @param basMenWinGrouConf
	 */
	void removeBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf);
	
	/**
	 * 根据编号查询菜单窗体拓展明细分组配置表细信息
	 * @param id
	 * @return
	 */
	BasMenWinGrouConf loadById(String id);
	

}
