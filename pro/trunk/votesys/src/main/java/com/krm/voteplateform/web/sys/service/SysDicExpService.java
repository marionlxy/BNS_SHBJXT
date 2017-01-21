package com.krm.voteplateform.web.sys.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.sys.model.BasExDetDic;
import com.krm.voteplateform.web.sys.model.BasExpandGroup;

public interface SysDicExpService {

	List<Map<String, Object>> findDicExpList();

	List<Map<String, Object>> findDicExpDetial(String id);

	Boolean saveExpGroup(BasExpandGroup basExpandGroup);

	boolean deleteExpGroup(BasExpandGroup basExpandGroup);

	BasExpandGroup updateExpGroup(String id);

	Boolean saveUpExpGroup(BasExpandGroup basExpandGroup);

	BasExDetDic findDicGroupFild(String gropId,String expDicId);

	Boolean editDicGroupFild(String groupId, String mapCnName, String id);
	/**
	 * 
	 * findSelectDicExpList:(查询下拉框). <br/>
	 * @author lixy
	 * @return
	 * @since JDK 1.7
	 */
	List<Map<String, Object>> findSelectDicExpList();
	/**
	 * 
	 * findExpGroupList:(查询下拉明扩展明细). <br/>
	 * @author lixy
	 * @param id
	 * @return
	 * @since JDK 1.7
	 */
	List<Map<String, Object>> findExpGroupList(String id);

	Boolean saveUpStateExps(String basdicts);
	
	Map<String, Object> getBasExpandGroupById(String bxgId);

	List<Map<String, Object>> findGroupList(String id);

	/**
	 * 根据groupId 删除BAS_EX_DET_DIC(拓展明细字典表)里的数据
	 * @author zhangYuHai
	 * @param groupId
	 * @return
	 */
	Boolean deleteByGroupId(String groupId);

	//是否存在相同的组名
	Integer isExist(String groupName);



}
