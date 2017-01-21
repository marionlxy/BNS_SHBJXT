package com.krm.voteplateform.web.pageTemplet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PtVotePageTempletMapper {

	List<Map<String, Object>> getAllPt();

	Map<String, Object> findPageTempletById(String id);

	List<Map<String, Object>> validateByName(@Param("id") String id);

}
