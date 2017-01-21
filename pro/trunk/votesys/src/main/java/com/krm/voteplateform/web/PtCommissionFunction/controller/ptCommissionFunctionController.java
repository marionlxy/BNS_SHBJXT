package com.krm.voteplateform.web.PtCommissionFunction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.PtCommissionFunction.service.PtCommissionFunctionService;

@Controller
@RequestMapping("pt/roleFunction")
public class ptCommissionFunctionController extends BaseController{

	@Autowired
	private PtCommissionFunctionService ptCommissionFunctionService;
}
