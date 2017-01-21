package com.krm.voteplateform.web.basProject.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;
import com.krm.voteplateform.web.basProExDetail.model.BasProExDetail;
import com.krm.voteplateform.web.basProject.model.BasProject;

public interface BasProjectService {

	/**
	 * 查询所有未审项目列表信息
	 * @author zhangYuHai
	 * @author zhangYuHai
	 * @return
	 */
	List<Map<String, Object>> findNoAuditoItemList();

	/**
	 * 根据未审Id删除未审列表信息
	 * @author zhangYuHai
	 * @param basProject
	 * @return
	 */
	Boolean removeBasProject(BasProject basProject);
	/**
	 * 查询所有已审项目列表信息
	 * @author zhangYuHai
	 * @param page
	 * @param basProject
	 * @return
	 */
	List<Map<String, Object>> findAuditoItemList(Pagination<Map<String, Object>> page, BasProject basProject);

	/**
	 * 导出已审项目列表数据
	 * @param basProject
	 * @return
	 */
	List<Map<String, Object>> exportData(BasProject basProject);

	/**
	 * 根据会议mettingId 查询出对应的项目列表信息
	 * @author zhangYuHai
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> findProjectByIdList(String mettingId);


	/**
	 * 退出 会议
	 * @param id
	 * @return
	 */
	Boolean updateQuitMetting(String projectId);

	BasProject selectBasProject(Map<String, Object> param);

	List<Map<String, Object>> findCurMetting(String id);

	Boolean saveItemList(Object objName);

	Integer updateByProjectId(String projectId, String mettingId);

	
	
	/**
	 * 添加方法项目
	 * @param basProject
	 * @return
	 */
	Boolean saveProjectByFunctionCode(BasProject basProject);


	public BasProject selectProjectById(String projectId);


	

	/**
	 * 循环添加议题
	 * @param list
	 * @return
	 */
	Boolean saveForDetailList(List<BasProExDetail> detailForm);

	/**
	 * 修改未审基本信息
	 * @param basProject
	 * @return
	 */
	Boolean updateProjectByFunctionCode(BasProject basProject);

	Map<String, Object> findProjectOne(Map<String, String> map);
	
	public int updateProjectState(Map<String, Object> params);
	/**
	 * 指定为再议功能
	 * @param projectId
	 * @return
	 */

	Boolean curMenu5toreDis(BasProject basProject);

	//项目升序 
	boolean ascProjectOrder(Map<String, Object> params);

	//项目降序 
	boolean descProjectOrder(BasProject basProject);

	//添加预备会议与完成会议添加议题时需要重新生成项目编号
	BasProject findParamsList(String projectId);

	

}
