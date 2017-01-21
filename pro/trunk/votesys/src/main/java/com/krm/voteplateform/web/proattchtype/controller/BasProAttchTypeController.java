package com.krm.voteplateform.web.proattchtype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.proattchtype.service.BasProAttchTypeService;

@Controller
@RequestMapping("pt/attchtype")
public class BasProAttchTypeController extends BaseController{

	@Autowired
	private BasProAttchTypeService basProAttchTypeService;
}
