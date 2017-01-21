package com.krm.voteplateform.web.commission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.commission.service.PtCommissionService;

@Controller
@RequestMapping("pt/ptcommission")
public class PtCommissionController extends BaseController {

	@Autowired
	private PtCommissionService ptCommissionService;

}
