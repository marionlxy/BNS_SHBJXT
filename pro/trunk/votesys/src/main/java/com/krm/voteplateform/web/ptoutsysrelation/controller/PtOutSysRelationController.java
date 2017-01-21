package com.krm.voteplateform.web.ptoutsysrelation.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.ptoutsysrelation.model.PtOutSysRelation;
import com.krm.voteplateform.web.ptoutsysrelation.service.PtOutSysRelationService;

@Controller
@RequestMapping("pt/relate")
public class PtOutSysRelationController extends BaseController {

	@Autowired
	private PtOutSysRelationService ptOutSysRelationService;

	@RequestMapping("toSysRelateIndex")
	public String toSysRelateIndex() {
		return "plateform/outsysrelation/sysrelation_list";
	}

	@RequestMapping("relationList")
	@ResponseBody
	public List<Map<String, Object>> relationList() {
		List<Map<String, Object>> list = ptOutSysRelationService.getRelationList();
		return list;
	}

	@RequestMapping("toAddReletion")
	public String toAddReletion(Model model) {
		List<Map<String, Object>> list = ptOutSysRelationService.getSysCommissionList();
		model.addAttribute("list", list);
		return "plateform/outsysrelation/sysrelation_add";
	}

	@RequestMapping("saveOutCommission")
	@ResponseBody
	public Result saveOutCommission(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始新增关联系统编码...");
		String relationCode = request.getParameter("relationCode");
		String relationName = request.getParameter("relationName");
		String code = request.getParameter("code").toUpperCase();
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("relationCode", relationCode);
		paramMap.put("code", code);
		boolean flag = ptOutSysRelationService.selectVotesCode(paramMap);
		if (!flag) {
			Result errorResult = Result.errorResult();
			errorResult.setMsg("操作失败[编码已存在于该委员会中，请重新分配编码！]");
			return errorResult;
		}
		PtOutSysRelation ptOutSysRelation = new PtOutSysRelation();
		String id = UUIDGenerator.getUUID();
		String notifyAddr = request.getParameter("notifyAddr");
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();

		ptOutSysRelation.setId(id);
		ptOutSysRelation.setCode(code);
		ptOutSysRelation.setNotifyAddr(notifyAddr);
		ptOutSysRelation.setRelationCode(relationCode);
		ptOutSysRelation.setRelationName(relationName);
		ptOutSysRelation.setUpdateTime(nowTimestamp);

		boolean insert = ptOutSysRelationService.saveOutCommission(ptOutSysRelation);
		logger.info("新增关联系统编码结束,信息{}，结果：{}", JSON.toJSONString(ptOutSysRelation), insert);
		return insert ? Result.successResult() : Result.errorResult();
	}

	@RequestMapping("toEditCommission")
	public String toEditCommission(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		Map<String, Object> map = ptOutSysRelationService.getRelationMap(id);
		List<Map<String, Object>> list = ptOutSysRelationService.getSysCommissionList();
		model.addAttribute("list", list);
		model.addAttribute("map", map);
		return "plateform/outsysrelation/sysrelation_edit";
	}

	@RequestMapping("saveUpdateOutCommission")
	@ResponseBody
	public Result saveUpdateOutCommission(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始编辑关联系统编码...");
		PtOutSysRelation ptOutSysRelation = new PtOutSysRelation();
		String notifyAddr = request.getParameter("notifyAddr");
		String id = request.getParameter("id");
		String relationCode = getPara("relationCode");
		String code = getPara("hidCode");
		String relationName = getPara("relationName");
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();

		ptOutSysRelation.setCode(code);
		ptOutSysRelation.setRelationCode(relationCode);
		ptOutSysRelation.setId(id);
		ptOutSysRelation.setRelationName(relationName);
		ptOutSysRelation.setNotifyAddr(notifyAddr);
		ptOutSysRelation.setUpdateTime(nowTimestamp);

		boolean update = ptOutSysRelationService.saveUpdateOutCommission(ptOutSysRelation);
		logger.info("编辑关联系统编码结束,信息{}，结果：{}", JSON.toJSONString(ptOutSysRelation), update);
		return update ? Result.successResult() : Result.errorResult();

	}
}
