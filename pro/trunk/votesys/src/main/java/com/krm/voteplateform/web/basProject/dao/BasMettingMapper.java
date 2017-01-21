package com.krm.voteplateform.web.basProject.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;
import com.krm.voteplateform.web.basProject.model.BasMetting;

public interface BasMettingMapper {

	List<Map<String, Object>> findCurrentMeeting(HashMap<String, String> map);

	/**
	 * 查询出所有完成会议信息
	 * 
	 * @author zhangYuHai
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> findCompletMetting(Map<String, Object> map);

	List<Map<String, Object>> findPrepatOrMeeting(HashMap<String, String> map);

	List<Map<String, Object>> VoteHistoryMetting(BasMetting basMetting, Pagination<Map<String, Object>> page);

	BasMetting selectBasMettingDetil(Map<String, Object> param);

	List<Map<String, Object>> findOrComplement(HashMap<String, String> map);

	BasMetting selectCompleMetting(Map<String, Object> param);

	public List<Map<String, Object>> getConfirmList(Map<String, Object> params);

	public List<Map<String, Object>> getAbsentDeptList(Map<String, Object> params);

	public List<Map<String, Object>> countVote(Map<String, Object> params);

	public List<Map<String, Object>> selectVoted(Map<String, Object> params);

	public Map<String, Object> selectFomula(Map<String, Object> params);

	public Map<String, Object> countOneMind(Map<String, Object> params);

	Map<String, Object> findSpecMetting(Map<String, Object> map);

	/**
	 * 查询会议表中会议编码最大值
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> selectMaxMettiongCode(Map<String, Object> params);

	/**
	 * 查询最近6条记录
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectRecentlyMettingList(HashMap<String, String> map);
	
	Map<String, Object> findOneSysConfByType(@Param("type") String type);
	
	public List<Map<String, Object>> selectVotedDetails(Map<String, Object> params);

	/**
	 * 添加创建预备会议信息
	 * @param basMetting
	 * @return
	 */
	Boolean saveMenu4MettingList(BasMetting basMetting);

	//添加议题时获取特殊的项目编码
	BasMetting findParamsMetting(Map<String, Object> map);
}
