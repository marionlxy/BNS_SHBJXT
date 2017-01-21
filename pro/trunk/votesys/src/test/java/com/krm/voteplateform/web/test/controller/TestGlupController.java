package com.krm.voteplateform.web.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.glue.GlueCodeFactory;
import com.krm.voteplateform.common.glue.model.PtDynamicSource;
import com.krm.voteplateform.common.utils.ExceptionCrashUtils;

/**
 * 动态执行Java代码测试
 * 
 * @author JohnnyZhang
 */
@Controller
public class TestGlupController {

	@RequestMapping("test/gulpIndex")
	public String toGulpIndex(HttpServletRequest request, Model model) {
		return "test/glup";
	}

	@RequestMapping("test/insertGlup")
	@ResponseBody
	public Result insertGlup(HttpServletRequest request, String dyCode) {
		PtDynamicSource pds = new PtDynamicSource();
		pds.setDescription("测试");
		pds.setId("1111");
		pds.setJavaCode(dyCode);
		pds.setDyType("1");
		GlueCodeFactory.getNowGlueTextLoader().insert(pds);
		return Result.successResult();
	}

	@RequestMapping("test/getGlupEx")
	@ResponseBody
	public Result getGlupEx(HttpServletRequest request, String id) {
		Result reslut = new Result();
		try {
			String glue = GlueCodeFactory.glue(id, "111", "222", "333", "5555").toString();
			reslut.setCode(Result.SUCCESS);
			reslut.setMsg(glue);
		} catch (Exception e) {
			reslut.setCode(Result.ERROR);
			reslut.setMsg(ExceptionCrashUtils.getCrashReport(e));
		}
		return reslut;
	}
}
