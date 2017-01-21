package com.krm.voteplateform.web.authorityrole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.authorityrole.service.PtAuthorityRoleService;

@Controller
@RequestMapping("pt/authorityRole")
public class PtAuthorityRoleController extends BaseController{

	@Autowired
	private PtAuthorityRoleService ptAuthorityRoleService;
}
