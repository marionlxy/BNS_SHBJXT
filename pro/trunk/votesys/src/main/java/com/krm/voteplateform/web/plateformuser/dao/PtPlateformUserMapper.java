package com.krm.voteplateform.web.plateformuser.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.voteplateform.web.plateformuser.model.PtPlateformUser;

public interface PtPlateformUserMapper {

	List<Map<String, Object>> getUserByOrgId(@Param("id") String id);

	List<Map<String, Object>> toPtUserList(Map<String, Object> map);

	List<Map<String, Object>> toPtUserListByCondition(Map<String, Object> map);

	PtPlateformUser getPtUserById(@Param("puid") String puid);

}
