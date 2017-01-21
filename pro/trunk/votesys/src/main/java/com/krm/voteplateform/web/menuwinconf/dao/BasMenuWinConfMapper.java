package com.krm.voteplateform.web.menuwinconf.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.voteplateform.web.menuwinconf.model.BasMenuWinConf;
import com.krm.voteplateform.web.menuwinconf.model.BasProAttch;
import com.krm.voteplateform.web.menwingrouconf.model.BasMenWinGrouConf;

/**
 * 操作相关
 */
public interface BasMenuWinConfMapper {
	
	
	
	/**
	 * 新增保存
	 * @param info
	 */
	void saveBasMenuWinConf(BasMenuWinConf basMenuWinConf);
	
	/**
	 * 修改
	 * @param info
	 */
	void updateBasMenuWinConf(BasMenuWinConf basMenuWinConf);

	/**
	 * 删除
	 * @param info
	 */
	void deleteBasMenuWinConf(BasMenuWinConf basMenuWinConf);
	
	/**
	 * 根据编号查询信息
	 * @param placeId
	 * @return
	 */
	BasMenuWinConf findById(Map<String, String> map);
	
	/**
	 * 检查名称是否重复
	 * @param placeId
	 * @return
	 */
	List<Map<String,Object>> findByIdByName(Map<String,Object> map);
	
	List<Map<String, Object>> listMenuWin(Map<String, String> map);
	
	List<Map<String, Object>> listAllMenuWin(Map<String, String> map);
	/**
	 * 
	 * findSelectGroupList:查询下拉. <br/>
	 * @author lixy
	 * @param map
	 * @return
	 * @since JDK 1.7
	 */
	List<Map<String, Object>> findSelectGroupList(Map<String, String> map);

	void deleteGroupId(Map<String, String> map);

	Map<String, Object> findGroupByID(Map<String, String> map);

	Map<String, Object> findWindGatherById(Map<String, String> map);

	int updateWinGather(Map<String, Object> map);

	Map<String, Object> getWinDetailById(Map<String, String> map);

	List<Map<String, Object>> getBasMenWinGroupConf(Map<String, String> map);


	String getDataSourceNameById(Map<String, String> map3);
	
	//获取所有tab信息及对应的内容
	public List<Map<String, Object>> getWinConfList(Map<String, Object> params);
	
	//获取拓展明细列表信息
	public List<Map<String, Object>> getExtDetList(Map<String, Object> params);
	
	//获取模板名称
	public Map<String, Object> getTempaletName(Map<String, Object> params);

	//获取扩展明细数据
	List<Map<String, Object>> getExtDetDataList(Map<String, Object> params);

	//获取附件类型
	List<Map<String, Object>> getattchTypeList(Map<String, Object> params);

	//获取附件
	List<Map<String, Object>> getProAttchList(HashMap<String, Object> params);
	
	//获取序号
	public List<Map<String, Object>> getOrder(Map<String, Object> params);
	
	//获取组序号
	public Map<String, Object> getGroupOrder(Map<String, Object> params);
	
	//根据组id更改相应的序号
	public int updateOrdersByGroup(Map<String, Object> params);
	
	public int changeGroupOrder(Map<String, Object> params);

	public List<Map<String, Object>> checkFileName(Map<String, Object> params);

	public BasProAttch getOneAttchType(Map<String, Object> params);

	public int updateBasMenuWinConfList(Map<String, Object> pmap);
	
	//获取窗口高度和宽度
	public Map<String, Object> selectWinSize(Map<String, Object> params);
}
