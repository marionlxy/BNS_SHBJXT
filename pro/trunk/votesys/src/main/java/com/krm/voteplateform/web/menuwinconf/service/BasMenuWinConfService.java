package com.krm.voteplateform.web.menuwinconf.service;

import java.util.List;
import java.util.Map;









import com.krm.voteplateform.web.menuwinconf.model.BasMenuWinConf;
import com.krm.voteplateform.web.menwingrouconf.model.BasMenWinGrouConf;

/**
 * 操作相关
 */
public interface BasMenuWinConfService {
	
	
	/**
	 * 新增保存
	 * @param basMenuWinConf
	 */
	Boolean saveBasMenuWinConf(BasMenuWinConf basMenuWinConf,Map<String,Object> pmap);
	
	/**
	 * 修改
	 * @param basMenuWinConf
	 */
	void updateBasMenuWinConf(BasMenuWinConf basMenuWinConf);

	/**
	 * 删除
	 * @param basMenuWinConf
	 */
	void removeBasMenuWinConf(BasMenuWinConf basMenuWinConf);
	
	/**
	 * 根据编号查询细信息
	 * @param id
	 * @return
	 */
	BasMenuWinConf loadById(String id);
	
	/**
	 * 查询名称是否重复
	 * @param id
	 * @return
	 */
	boolean selectName(Map<String, Object> map);

	List<Map<String, Object>> getMenuWinAll(String functionCode);

	void deleteMById(String id);
	
	/**
	 * 下拉框
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> findSelectGroupList(String functionCode);

	/**
	 * 新增保存采集框
	 * @param basMenuWinConf
	 */
	Boolean saveBasMenuGatherConf(BasMenuWinConf basMenuWinConf);
	/**
	 * 新增保存明细框
	 * @param basMenuWinConf
	 */
	Boolean saveBasMenuDetailConf(BasMenuWinConf basMenuWinConf, List<BasMenWinGrouConf> lst,String[] chkArrs);
	
	Map<String, Object> findGroupByID(String id);

	Boolean saveGroupUpdate(BasMenuWinConf basMenuWinConf, Map<String, Object> map);

	Map<String, Object> findWindGatherById(String id);

	Boolean saveWindGatherUpdate(BasMenuWinConf basMenuWinConf);

	Map<String, Object> getWinDetailById(String id);

	List<Map<String, Object>> getBasMenWinGroupConf(String bmwcId);

	Boolean saveBasMenuDetailConfUpdate(BasMenuWinConf basMenuWinConf, List<BasMenWinGrouConf> lst, String[] chkArrs);
	
	public List<Map<String, Object>> getWinConfList(Map<String, Object> params);
	
	public Map<String, Object>  selectGroupCode(String tableName,  String dataSource);
	
	public List<Map<String, Object>> getExtDetList(Map<String, Object> params);
	
	public Map<String, Object> getTempaletName(Map<String, Object> params);
	
	public List<Map<String, Object>> getExtDetDataList(Map<String, Object> params);
	
	public List<Map<String, Object>> getOrder(Map<String, Object> params);
	
	public Map<String, Object> getGroupOrder(Map<String, Object> params);
	
	public int updateOrdersByGroup(Map<String, Object> params);
	
	public int changeGroupOrder(Map<String, Object> params);
	
	public Map<String, Object> selectWinSize(Map<String, Object> params);
}
