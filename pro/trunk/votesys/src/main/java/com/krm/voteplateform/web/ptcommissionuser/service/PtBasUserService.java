
package com.krm.voteplateform.web.ptcommissionuser.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.plateformuser.model.PtPlateformUser;
import com.krm.voteplateform.web.plateformuserpass.PtPlateformUserPass;
import com.krm.voteplateform.web.ptcommissionuser.model.PtCommissionUser;
import com.krm.voteplateform.web.ptuserrole.model.PtCommissionUserRole;

public interface PtBasUserService{
	//展示表中数据的方法
	List<Map<String, Object>> showList();
	//退出委员会的操作
	Boolean deleteById(String puid);
	//查询用户信息
	List<Map<String, Object>> findCommisionUserInfoByIds(Map<String, String> parammap);
	
	boolean ascOrder(PtCommissionUser ptCommissionUser,PtCommissionUser ptCommissionUser1);
	boolean desOrder(PtCommissionUser ptCommissionUser,PtCommissionUser ptCommissionUser1);
	Map<String, Object> getUserById(String puid, String code);
	List<Map<String, Object>> getRoleList();
	boolean saveUserUpdate(PtCommissionUser ptCommissionUser, PtPlateformUser ptPlateformUser,
			PtCommissionUserRole ptCommissionUserRole,PtPlateformUserPass ptPlateformUserPass);
	List<Map<String, Object>> listOrgs();
	int getMaxOrder();
	boolean addUsers(List<PtCommissionUser> list,List<PtCommissionUserRole> listUr);
	//查询委员会用户信息
	List<Map<String, Object>> findCommisionUserList(Map<String, String> params);
	boolean updateState(PtCommissionUser ptCommissionUser);
	Map<String, Object> getUserRoleById(String string, String code);
	//查询主持人信息
	List<Map<String, Object>> findHostUserIds(Map<String, Object> param);
	
	
	
}