package com.krm.voteplateform.web.ptuserrole.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.ptuserrole.model.PtCommissionUserRole;

public interface PtCommissionUserRoleService {

	List<Map<String, Object>> getCurrentCommissionUsers();

	boolean deleteById(String id);

	boolean addPtAdmin(PtCommissionUserRole ptCommissionUserRole);

}
