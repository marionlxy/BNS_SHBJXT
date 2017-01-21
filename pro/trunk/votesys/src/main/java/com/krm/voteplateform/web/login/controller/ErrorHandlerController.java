package com.krm.voteplateform.web.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.voteplateform.common.utils.LogUtils;

@Controller
@RequestMapping("/ErrorHandler")
public class ErrorHandlerController {

	@RequestMapping
	public String errorHandler(HttpServletRequest request) {
		LogUtils.logPageError(request);
		return "error/error";
	}

}
