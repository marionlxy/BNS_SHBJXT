package com.krm.voteplateform.web.plateformorg.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.voteplateform.web.plateformorg.model.PtPlateformOrg;

public interface PtPlateformOrgMapper {

	List<Map<String, Object>> listOrg(@Param("code") String code);

	List<Map<String, Object>> toPtOrgList(@Param("code") String code);

	PtPlateformOrg getPtPlateformOrgById(@Param("id") String id);

}
