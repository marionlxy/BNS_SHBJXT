package com.krm.voteplateform.web.ptuserrole.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PtCommissionUserRoleMapper {

	List<Map<String, Object>> findAllUser(@Param("code") String code);

	Map<String, Object> getUserRoleById(Map<String, Object> mapUr);

}
