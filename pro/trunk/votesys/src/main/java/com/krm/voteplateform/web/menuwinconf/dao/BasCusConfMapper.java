package com.krm.voteplateform.web.menuwinconf.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BasCusConfMapper {
	
	int findOne(Map<String, String> map);

	void delete(Map<String, String> map);

}
