package com.krm.voteplateform.web.ptoutsysrelation.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.ptoutsysrelation.model.PtOutSysRelation;

public interface PtOutSysRelationService {

	List<Map<String, Object>> getRelationList();

	boolean saveOutCommission(PtOutSysRelation ptOutSysRelation);

	List<Map<String, Object>> getSysCommissionList();

	Map<String, Object> getRelationMap(String id);

	boolean saveUpdateOutCommission(PtOutSysRelation ptOutSysRelation);

	boolean selectVotesCode(Map<String,Object> paramMap);

}
