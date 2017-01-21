package com.krm.voteplateform.web.downloadmodel.service;

import java.util.List;
import java.util.Map;

public interface DownoadModelService {

	List<Map<String, Object>> getDicData(String code);

	List<Map<String, Object>> getAttachType(String code);

	List<Map<String, Object>> getCodeGroup(String code);

}
