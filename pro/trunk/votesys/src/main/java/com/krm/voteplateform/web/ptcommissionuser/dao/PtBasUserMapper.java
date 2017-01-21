package com.krm.voteplateform.web.ptcommissionuser.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.voteplateform.web.plateformorg.model.PtPlateformOrg;
import com.krm.voteplateform.web.plateformuserpass.PtPlateformUserPass;
import com.krm.voteplateform.web.ptcommissionuser.model.PtCommissionUser;
import com.krm.voteplateform.web.ptuserrole.model.PtCommissionUserRole;

public interface PtBasUserMapper{
	//委员会用户管理
	List<Map<String, Object>> selectdate(Map<String, String> map);

	List<Map<String, Object>>  findCommisionUserInfoByIds(Map<String, String> parammap);

	List<Map<String, Object>> findCommisionVoteAuths(Map<String, String> map);

	PtCommissionUser findMaxOrfer(Map<String, Object> map);

	PtCommissionUser findMinOrder(Map<String, Object> map);

	int updateUserOrder(PtCommissionUser ptCommissionUser);

	Map<String, Object> getUserById(@Param("puid") String puid,@Param("code") String code);

	int saveUserUpdate(PtCommissionUser ptCommissionUser);

	int getMaxOrder(@Param("code") String code);

	int addUsers(PtCommissionUser ptCommissionUser);

	int existUser(Map<String, Object> map);

	Map<String, Object> getUserPassword(@Param("puid") String puid);

	int updateUserPassword(PtPlateformUserPass ptPlateformUserPass);

	int updateUserRole(PtCommissionUserRole ptCommissionUserRole);

	int updateUserState(PtCommissionUser updateUser);

	List<Map<String, Object>> findHostUserIds(Map<String, Object> param);
	
}