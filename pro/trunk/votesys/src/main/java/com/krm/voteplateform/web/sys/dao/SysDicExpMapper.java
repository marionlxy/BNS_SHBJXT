package com.krm.voteplateform.web.sys.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.sys.model.BasExDetDic;
import com.krm.voteplateform.web.sys.model.BasExpandGroup;

public interface SysDicExpMapper {


	List<Map<String, Object>> findDicExpList(Map<String, String> map);

	List<Map<String, Object>> findDicExpDetial(Map<String, String> map);

	int basExDetDicAll(Map<String, String> map);

	int insertbasExDetDic(Map<String, String> map);

	List<Map<String, Object>> selectBasDict(Map<String, String> map);

	BasExpandGroup updateExpGroup(Map<String, String> map);

	BasExDetDic findDicGroupFild(Map<String, String> map);

	Boolean editDicGroupFild(Map<String, String> map);
	/**
	 * 
	 * findSelectDicExpList:(查询扩展下拉). <br/>
	 * @author lixy
	 * @param map
	 * @return
	 * @since JDK 1.7
	 */
	List<Map<String, Object>> findSelectDicExpList(Map<String, String> map);

	List<Map<String, Object>> findExpGroupList(Map<String, String> map);

	Map<String, Object> getBasExpandGroupById(Map<String, String> map);

	List<Map<String, Object>> findGroupList(Map<String, String> map);

	Boolean updateByGroupId(Map<String, String> map);

	//是否存在相同的扩展组名
	Integer isExistGroupName(Map<String, String> map);


}

								

