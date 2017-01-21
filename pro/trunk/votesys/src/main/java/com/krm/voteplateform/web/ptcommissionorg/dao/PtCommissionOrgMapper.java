package com.krm.voteplateform.web.ptcommissionorg.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.voteplateform.web.ptcommissionorg.model.PtCommissionOrg;

public interface PtCommissionOrgMapper {

	List<Map<String, Object>> toOrgList(Map<String,Object> map);

	Map<String, Object> getOrg(@Param("code") String code,@Param("porgid") String porgid);

	int saveOrgUpdate(PtCommissionOrg ptCommissionOrg);

	int deleteOrg(PtCommissionOrg ptCommissionOrg);

	PtCommissionOrg findMaxOrfer(Map<String, Object> map);

	int updateOrder(PtCommissionOrg ptCommissionOrg);

	PtCommissionOrg findMinOrder(Map<String, Object> map);

	int getMaxOrgOrder(@Param("code") String code);

	List<Map<String, Object>> getCodeListOrg(Map<String, Object> mpparam);

}
