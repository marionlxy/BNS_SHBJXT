package com.krm.voteplateform.web.dynamicsource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.dynamicsource.service.PtDynamicSourceService;
	@Controller
	@RequestMapping("pt/PtDynamicSource")
public class PtDynamicSourceController  extends BaseController {
	
		@Autowired
		private  PtDynamicSourceService ptDynamicSourceService;
		
		
	
	
}