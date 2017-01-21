package com.krm.voteplateform.web.basProject.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.basProject.model.BasMetting;

public interface BasMettingService {

	/**
	 * 是否存在当前会议
	 * 
	 * @author zhangYuHai
	 * @return
	 */
	Map<String, Object> findCurrentMeeting();

	/**
	 * 查询出完成会议信息
	 * 
	 * @author zhangYuHai
	 * @param basMetting
	 * @return
	 */
	List<Map<String, Object>> findCompletMetting(BasMetting basMetting);

	Map<String, Object> findPrepatOrMeeting();

	/**
	 * 将会议指定为当前会议
	 * 
	 * @author zhangYuHai
	 * @param basMetting
	 * @return
	 */
	Boolean updateToCurrent(BasMetting basMetting);

	/**
	 * 将会议指定会预备会议
	 * 
	 * @author zhangYuHai
	 * @param basMetting
	 * @return
	 */
	Boolean updateToPreparatory(BasMetting basMetting);

	Boolean updateToComplete(BasMetting basMetting);

	BasMetting findeMettingDetail(Map<String, Object> param);

	/**
	 * 是否为完成会议
	 * 
	 * @author zhangYuHai
	 * @return
	 */
	Map<String, Object> findOrComplement(String projectId);

	/**
	 * 反显出完成会议信息
	 * 
	 * @param param
	 * @return
	 */
	BasMetting viewCompleMetting(Map<String, Object> param);

	public List<Map<String, Object>> getConfirmList(Map<String, Object> params);

	public boolean empower(Map<String, Object> params);

	public int deleteCommitteeMember(Map<String, Object> params);

	public List<Map<String, Object>> getAbsentDeptList(Map<String, Object> params);

	public List<Map<String, Object>> countVote(Map<String, Object> params);

	public List<Map<String, Object>> selectVoted(Map<String, Object> params);

	public Map<String, Object> selectFomula(Map<String, Object> params);

	public Map<String, Object> countOneMind(Map<String, Object> params);

	
	

	//获取会议编号与名称的特殊处理
	public Map<String, Object>specReflectMetting();
	/**
	 * 查询最近6条会议记录
	 * 
	 * @return
	 */
	List<Map<String, Object>> selectRecentlyMettingList();
	
	List<Map<String, Object>> selectVotedDetails(Map<String, Object> params);

	/**
	 * 添加创建预备会议信息
	 * @param basMetting
	 * @return
	 */
	Boolean saveMenu4MettingList(BasMetting basMetting);

	//添加议题时获取特殊的项目编码
	BasMetting findParamsMetting(String mettingId);

}
