package com.krm.voteplateform.web.ptvotematterconf.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.ptvotematterconf.model.PtVoteMatterConf;

/**
 * 操作相关
 */
public interface PtVoteMatterConfService {
	
	
	/**
	 * 新增保存
	 * @param ptVoteMatterConf
	 */
	void savePtVoteMatterConf(PtVoteMatterConf ptVoteMatterConf);
	/**
	 * 查询显示列表
	 * @param ptVoteMatterConf
	 */
	List<Map<String, Object>> selectAll(Map<String, Object> params);
	List<Map<String, Object>> selecResultList(Map<String, Object> params);
	//回写下拉框里面的数值
	PtVoteMatterConf selectchoose(String id);
	//修改之后的保存
	boolean updateSave(PtVoteMatterConf ptVoteMatterConf);
	//查询按钮信息
	 List<Map<String, Object>> findBtnInfo(Map<String, String> map);
	//查询票数
	 String selectticket(String code);
	String selectonemind(Map<String, Object> map);
	
	

	
}
