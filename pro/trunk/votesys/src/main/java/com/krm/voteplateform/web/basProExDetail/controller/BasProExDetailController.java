package com.krm.voteplateform.web.basProExDetail.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.basProExDetail.service.BasProExDetailService;


@Controller
public class BasProExDetailController extends BaseController{

	@Resource
	private BasProExDetailService basProExDetailService;
	
}
