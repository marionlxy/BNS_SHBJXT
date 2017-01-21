package com.krm.voteplateform.web.sysrole.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SysRoleMapper {

	List<Map<String, Object>> listRoles(@Param("code") String code);

	List<Map<String, Object>> listFunctionsByRole(Map<String, Object> map);

	List<Map<String, Object>> listAuthorityByRole();

	Map<String, Object> getRoleById(@Param("id") String id);

	List<Long> getRoleFuntionsByRoleId(@Param("crid") String crid);

	List<Map<String, Object>> getRoleAuthoritysByRoleId(@Param("crid") String crid);

	void deleteRoleFunctionsById(@Param("crid") String crid);

	void deleteRoleAuthoritysById(@Param("crid") String crid);

}
