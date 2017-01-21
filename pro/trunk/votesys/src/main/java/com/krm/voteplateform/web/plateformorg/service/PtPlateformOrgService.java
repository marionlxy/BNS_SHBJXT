package com.krm.voteplateform.web.plateformorg.service;

import java.util.List;
import java.util.Map;

public interface PtPlateformOrgService {

	List<Map<String, Object>> getOrgAll();

	List<Map<String, Object>> getUsersByOrgId(String id);

	List<Map<String, Object>> toPtOrgList();

}
