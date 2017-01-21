package com.krm.voteplateform.web.ptvoterestmpl.service;

import java.util.Map;

import com.krm.voteplateform.web.ptvoterestmpl.model.PtVoteResTmpl;

/**
 * 操作相关
 */
public interface PtVoteResTmplService {
	
	
	/**
	 * 新增保存
	 * @param ptVoteResTmpl
	 */
	void savePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl);
	
	/**
	 * 修改
	 * @param ptVoteResTmpl
	 */
	void updatePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl);

	/**
	 * 删除
	 * @param ptVoteResTmpl
	 */
	void removePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl);
	
	/**
	 * 根据编号查询细信息
	 * @param id
	 * @return
	 */
	PtVoteResTmpl loadById(String id);
	
	/**
	 * 根据不同条件组合查询，可分页查询
	 * @param page
	 * @param 
	 */
	//void loadPtVoteResTmplList(Pagination<PtVoteResTmpl> page,Map<String,Object> params);

	public PtVoteResTmpl findByCode(Map<String,Object> params);
}
