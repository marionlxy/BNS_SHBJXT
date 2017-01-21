package com.krm.voteplateform.web.reflectUpdate.controller;

import java.util.Map;

import com.krm.voteplateform.common.prehandle.IRequestHandler;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.web.basProject.service.BasMettingService;

public class SpecMettingDate implements IRequestHandler {

	@Override
	public Map<String, Object> exec(Map<String, Object> param) {
		BasMettingService basMettingService = SpringContextHolder.getBean("basMettingService");
		return basMettingService.specReflectMetting();
	}
	
	
}
