package com.krm.voteplateform.web.ptoutsysrelation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PtOutSysRelationMapper {

	List<Map<String, Object>> getRelationList();

	Map<String, Object> getRelationMap(@Param("id") String id);

	Integer selectVotesCode(Map<String, Object> map);

}
