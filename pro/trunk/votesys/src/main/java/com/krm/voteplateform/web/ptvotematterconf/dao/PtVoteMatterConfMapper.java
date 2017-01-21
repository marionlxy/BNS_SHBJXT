package com.krm.voteplateform.web.ptvotematterconf.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.ptvotematterconf.model.PtVoteMatterConf;

/**
 * 操作相关
 */
public interface PtVoteMatterConfMapper {
	
	
	/**
	 * 新增保存
	 * @param info
	 */
	void savePtVoteMatterConf(PtVoteMatterConf ptVoteMatterConf);
	
	void insertBatchs(List<PtVoteMatterConf> lst);
	//展示列表
	List<Map<String, Object>> showadd(Map<String, Object> params);
	//回写修改的方法
	PtVoteMatterConf selectUpdate(Map<String, String> map);
	//修改之后的保存
	int lastSave(PtVoteMatterConf ptVoteMatterConf);

	List<Map<String, Object>> showResultadd(Map<String, Object> params);

	List<Map<String, Object>> findBtnInfoList(Map<String, String> map);

	String selectlot(String code);

	List<PtVoteMatterConf> selecontmind(Map<String, Object> map);
	
}
