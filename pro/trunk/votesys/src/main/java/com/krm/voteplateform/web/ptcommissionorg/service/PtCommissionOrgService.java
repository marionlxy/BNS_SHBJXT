package com.krm.voteplateform.web.ptcommissionorg.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.plateformorg.model.PtPlateformOrg;
import com.krm.voteplateform.web.ptcommissionorg.model.PtCommissionOrg;

public interface PtCommissionOrgService {

	List<Map<String, Object>> toOrgList();

	PtPlateformOrg getPtPlateformOrgById(String id);

	boolean saveOrg(PtCommissionOrg ptCommissionOrg);

	boolean saveOrgs(List<PtCommissionOrg> list);

	Map<String, Object> getOrg(String code, String porgid);

	boolean saveOrgUpdate(PtCommissionOrg ptCommissionOrg);

	boolean deleteOrg(PtCommissionOrg ptCommissionOrg);

	boolean ascOrder(PtCommissionOrg ptCommissionOrg,PtCommissionOrg ptCommissionOrg1);

	boolean desOrder(PtCommissionOrg ptCommissionOrg,PtCommissionOrg ptCommissionOrg1);

	int getMaxOrgOrder();

	List<Map<String, Object>> getCodeListOrg(Map<String, Object> mpparam);

}
