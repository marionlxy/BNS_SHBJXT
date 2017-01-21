package com.krm.voteplateform.web.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.voteplateform.web.constants.SessionKeys;

@Controller
public class IndexController {

	@RequestMapping({ "login", "/", "" })
	public String login(HttpServletRequest request) {
		request.getSession().setAttribute(SessionKeys.USER_KEY, "test");
		return "redirect:index";
	}

	@RequestMapping({ "index" })
	public String index(HttpServletRequest request) {
		return "index";
	}

	@RequestMapping("aa/test1")
	public String test1(HttpServletRequest request, Model model) {
		model.addAttribute("a1", "a1");
		request.setAttribute("a2", "a2");
		return "plateform/test1";
	}
}
