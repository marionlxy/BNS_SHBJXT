package com.krm.voteplateform.web.voteplan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.voteplateform.web.voteplan.model.PtVotePlan;

public interface PtVotePlanMapper {
	
	List<Map<String,Object>> listAll();

	int insert(PtVotePlan ptVotePlan);

	int recordCount(@Param("votePlanid") String votePlanid);

	List<Map<String, Object>> listAll(Map<String, Object> map);

}
