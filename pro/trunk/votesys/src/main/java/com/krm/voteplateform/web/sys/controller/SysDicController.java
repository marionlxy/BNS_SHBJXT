package com.krm.voteplateform.web.sys.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.sys.model.BasDict;
import com.krm.voteplateform.web.sys.service.SysDicService;
import com.krm.voteplateform.web.util.LogoUtils;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
@RequestMapping("pt/ptSysDic")
public class SysDicController extends BaseController {

	@Autowired
	private SysDicService sysDicService;

	/**
	 * 根据编辑code 跳转到字典sysindex.html首页，
	 * 
	 * @author ZhangYuHai
	 * @param request
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping("toPtSysDic")
	public String toPtSysDic(HttpServletRequest request, Model model, String code) {
		String codes = request.getParameter("code");
		SysUserUtils.setCurrentCommissionCode(codes);
		model.addAttribute("logoBase64", LogoUtils.getLogo());
		return "plateform/sysindex";
	}

	/**
	 * 根据字典表CATEGORY 类型来展示页面
	 * 
	 * @author zhangYuHai
	 * @param request 类型为
	 * @param model
	 * @return
	 */
	@RequestMapping("toDicType")
	public String toDicType(HttpServletRequest request, Model model) {
		logger.info("开始编辑项目申请分类明细字典页面跳转:sysDic_list");
		request.setAttribute("category", request.getParameter("category"));
		SysUserUtils.getCurrentCommissionCode();
		return "plateform/sysDic/sysDic_list";
	}

	/**
	 * 根据SysUserUtils.getCurrentCommissionCode()存在的Code值 和category两个参数，查询编辑项目字典数据
	 * 
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param codes
	 * @return
	 */
	@RequestMapping("getSysDicList")
	@ResponseBody
	public List<Map<String, Object>> getSysDicList(HttpServletRequest request, Model model, String codes,
			String category) {
		logger.info("开始查询编辑项目字典数据 getSysDicList根据 code");
		List<Map<String, Object>> dicList = sysDicService.findSysList(category);
		logger.info("结束查询编辑项目字典数据 getSysDicList");
		return dicList;

	}

	/**
	 * 查询每个字典类型页面的每一条映射名称与精度值
	 * 
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("selectDicField")
	public String selectDicField(HttpServletRequest request, Model model, String id) {
		logger.info("开始根据id查询每条字典数据 selectDicField");
		logger.info("从session中获得codes");
		BasDict sysDicFields = sysDicService.selectDicFields(id);
		model.addAttribute("sysDicFields", sysDicFields);
		logger.info("结束查询每条字典数据结束返回到sysDic_edit.html页面");
		return "plateform/sysDic/sysDic_edit";
	}

	/**
	 * 编辑保存每个字典类型页面的每一条映射名称与精度值
	 * 
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param mapCnName
	 * @param mapPrecision
	 * @param id
	 * @return
	 * 
	 */
	// , Integer mapPrecision 映射精度
	@RequestMapping("editDidcField")
	@ResponseBody
	public Result editDidcField(HttpServletRequest request, Model model, String id, String mapCnName) {
		logger.info("开始根据id编辑每条的映射名称与精度值");
		boolean flag = false;
		if (StringUtils.isAnyEmpty(mapCnName)) {
			logger.info("映射名称不能为空");
			// result = Result.errorResult();
			return Result.errorResult();
		} else {
			flag = sysDicService.editDicFields(id, mapCnName);
			logger.info("进入逻辑业务，根据返回boolean falg 值，返回 结束Result.successResul结果1：,返回成功，0： 失败");
			return Result.successResult();
		}
	}

	/**
	 * 更新保存数据是否启用
	 * 
	 * @author zhangYuHai
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("saveUpState")
	@ResponseBody
	public Result saveUpState(HttpServletRequest request, String basdict) {
		String basdicts = request.getParameter("basdict");
		Boolean flag = sysDicService.saveUpState(basdicts);
		// logger.info("saveUpState" + id);
		if (flag) {
			return Result.successResult();
		} else {
			return Result.errorResult();
		}

	}

}
