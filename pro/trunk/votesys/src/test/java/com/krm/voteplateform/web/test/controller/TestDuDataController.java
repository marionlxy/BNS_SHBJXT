package com.krm.voteplateform.web.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.test.model.Tm;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("test")
public class TestDuDataController extends BaseController {

	@RequestMapping("submitDu")
	@ResponseBody
	public Result submitDu(Tm tms) {
		return Result.successResult();
	}

	@RequestMapping("submitDu1")
	public String submitDu(HttpServletRequest request, Tm tm) {
		List<Tm> tms = tm.getTms();
		for (Tm tm2 : tms) {
			MultipartFile file = tm2.getFile();
			System.out.println(file.getOriginalFilename());
		}
		return "test/index";
	}

}
