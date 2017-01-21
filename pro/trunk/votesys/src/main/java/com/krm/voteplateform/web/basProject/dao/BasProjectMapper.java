package com.krm.voteplateform.web.basProject.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.krm.voteplateform.common.mybatis.plugins.DyTableModel;
import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;
import com.krm.voteplateform.web.basProject.model.BasProject;
import com.krm.voteplateform.web.basProject.model.BasProjectExp;

public interface BasProjectMapper {

	/**
	 * 查询所有未审项目列表信息
	 * @author zhangYuHai
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> findNoAuditoItemList(Map<String, String> map);
	/**
	 * 已审项目列表分页信息查询
	 * @author zhangYuHai
	 * @param basProject
	 * @param page
	 * @return
	 */

	List<Map<String, Object>> findAuditoItemList(BasProject basProject, Pagination<Map<String, Object>> page);


	List<BasProject> findMenuListConf(Map<String, String> map);

	List<Map<String, Object>> exportData(Map<String, Object> map);

	/**
	 * 
	 * @author zhangYuHai
	 * @param map
	 * @return  findProjectMetting
	 */
	List<Map<String, Object>> findProjectByIdList(HashMap<String, String> map);
	/**
	 * 查询出秘书端完成会议列表信息
	 * @author zhangYuHai
	 * @param map
	 * @return
	 */
	//List<Map<String, Object>> projectByMetting(Map<String, String> map);

	/**
	 * 根据项目id退出 预备会议
	 * @author zhangYuHai
	 * @param map
	 * @return
	 */
	public Boolean updateQuitMetById(Map<String, String> map);

	BasProject selectBasProjectAll(Map<String, Object> param);

	List<Map<String, Object>> findCurMetting(HashMap<String, String> map);
	
	/**
	 * 查询出预备会议列表信息
	 * @author zhangYuHai
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> findfindPrepaMettList(HashMap<String, String> map);
	/**
	 * 查询出表决权限
	 * @author zhangYuHai
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> findAuthsProjectByIdList(Map<String, String> map);
	
	List<Map<String, Object>> findMaxProjectOrder(HashMap<String, String> map);
	
	//查询出会议的最大序号
	//Map<String, Object> findMaxProjectOrder(Map<String, String> map);
	
	//将议题添加 到会议中去
	Integer updateProjectToMetting(BasProject basProject);
	
	Map<String, Object> getProjectOrder(Map<String, Object> map);
	
	/**
	 * 找到比projectOrder小1的最大projectOrder
	 * @param map
	 * @return
	 */
	
	BasProject findMaxprojectOrder(Map<String, Object> map);
	//修改排序号升序
	int updateProjectOrder(BasProject basProject);
	//修改排序号降序
	BasProject findMinprojectOrder(Map<String, Object> map);
	//添加预备会议与完成会议添加议题时需要重新生成项目编号
	BasProject findParamsList(Map<String, Object> params);

	
	
	


}
