package com.krm.voteplateform.web.ptvoterestmpl.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.voteplateform.web.ptvoterestmpl.model.PtVoteResTmpl;

/**
 * 操作相关
 */
public interface PtVoteResTmplMapper {
	
	
	/**
	 * 新增保存
	 * @param info
	 */
	void savePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl);
	
	/**
	 * 修改
	 * @param info
	 */
	void updatePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl);

	/**
	 * 删除
	 * @param info
	 */
	void deletePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl);
	
	/**
	 * 根据编号查询细信息
	 * @param placeId
	 * @return
	 */
	PtVoteResTmpl findById(String id);
	
	/**
	 * 根据不同条件组合查询，可分页查询
	 * @param page
	 * @param 
	 */
	//List<PtVoteResTmpl> findPtVoteResTmplList(@Param(value="page") Pagination<PtVoteResTmpl> page,@Param(value="map") Map<String,Object> params);

	Integer countPtVoteResTmpl(@Param(value="map") Map<String,Object> params);
	int saveUpdate2(PtVoteResTmpl ptVoteResTmpl);
	public PtVoteResTmpl findByCode(Map<String,Object> params);
}
