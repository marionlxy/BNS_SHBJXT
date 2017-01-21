package com.krm.voteplateform.web.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.util.PageOfficeUtils;
import com.zhuozhengsoft.pageoffice.PDFCtrl;

@Controller
@RequestMapping("test")
public class TestJspController extends BaseController {

	@RequestMapping("toIndex1")
	public String toIndex1(HttpServletRequest request) {
		return "views/test/test1";
	}

	@RequestMapping("toPdf")
	public String toPdf(HttpServletRequest request) {
		PageOfficeUtils.buildPdfProps(request, "D:\\home\\vote\\upload\\CENT\\attchFiles\\111.pdf");
		return "views/test/PDF";
	}
}
