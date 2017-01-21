package com.krm.voteplateform.web.voteplan.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.voteplan.model.PtVotePlan;

public interface PtVotePlanService {
	
	public List<Map<String, Object>> listAll();
	
	public boolean saveVotePlan(PtVotePlan ptVotePlan);

	void delete(String id);

	void stop(String id);

	public boolean isUseFlag(String id);

	public boolean startById(String id);

	public List<Map<String, Object>> listAll(Map<String, Object> map);

}
