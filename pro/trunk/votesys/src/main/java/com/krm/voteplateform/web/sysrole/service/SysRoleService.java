package com.krm.voteplateform.web.sysrole.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.PtCommissionFunction.model.PtCommissionFunction;
import com.krm.voteplateform.web.authorityrole.model.PtAuthorityRole;
import com.krm.voteplateform.web.commission.model.PtCommissionRole;

public interface SysRoleService {

	List<Map<String, Object>> listRoles();

	List<Map<String, Object>> listFunctionsByRole(String roleCategory);

	List<Map<String, Object>> listAuthorityByRole();

	Boolean saveRole(PtCommissionRole ptCommissionRole, String resIds, List<PtAuthorityRole> list2);

	Map<String, Object> getRoleById(String id);

	List<Long> getRoleFuntionsByRoleId(String crid);

	List<Map<String, Object>> getRoleAuthoritysByRoleId(String crid);

	boolean saveRoleUpdate(PtCommissionRole ptCommissionRole, List<PtCommissionFunction> list,
			List<PtAuthorityRole> list2);

	boolean deleteRoleById(String crid);

}
