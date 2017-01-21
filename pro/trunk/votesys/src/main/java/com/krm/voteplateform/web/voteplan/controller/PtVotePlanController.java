package com.krm.voteplateform.web.voteplan.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.utils.VoteFormulaUtils;
import com.krm.voteplateform.web.util.SysUserUtils;
import com.krm.voteplateform.web.voteplan.model.PtVotePlan;
import com.krm.voteplateform.web.voteplan.service.PtVotePlanService;

@Controller
@RequestMapping("pt/ptvoteplan")
public class PtVotePlanController extends BaseController {

	@Autowired
	private PtVotePlanService ptVotePlanService;

	/**
	 * @查询所有表决方案列表
	 * @author huangYuanao
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("votePlanList")
	@ResponseBody
	public List<Map<String, Object>> votePlanList(HttpServletRequest request, Model model) {
		logger.info("开始查询vote_plan投票方案表");
		List<Map<String, Object>> listAll = ptVotePlanService.listAll();
		// model.addAttribute("listAll", listAll);
		logger.info("查询vote_plan投票方案表结束");
		return listAll;

	}

	/**
	 * @新增表决方案
	 * @author huangYuanao
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("addVotePlan")
	public String addVotePlan(HttpServletRequest request, Model model) {
		return "plateform/voteplan/voteplan_edit";

	}

	@RequestMapping("planList")
	public String planList() {
		logger.info("开始跳转");
		logger.info("结束跳转");
		return "plateform/voteplan/voteplan_list";
	}

	@RequestMapping("saveVotePlan")
	@ResponseBody
	public Result saveVotePlan(PtVotePlan ptVotePlan, HttpServletRequest request) {
		logger.info("开始保存");
		try {
			Map<String, Integer> paramValueMap = Maps.newHashMap();
			paramValueMap.put("a", 6);
			paramValueMap.put("zong", 10);
			String adoptFormula = ptVotePlan.getAdoptFormula();
			if (StringUtils.isNotEmpty(adoptFormula)) {
				VoteFormulaUtils.calcFormula(adoptFormula, paramValueMap);
			}
			String againFormula = ptVotePlan.getAgainFormula();
			if (StringUtils.isNotEmpty(againFormula)) {
				VoteFormulaUtils.calcFormula(againFormula, paramValueMap);
			}
		} catch (Exception e) {
			logger.warn("保存表决方案时，公式输入错误！", e);
			Result result = Result.errorResult();
			result.setMsg("通过公式或再议公式输入错误");
			return result;
		}
		String createBy = SysUserUtils.getSessionLoginUser().getUserName();
		ptVotePlan.setCreateBy(createBy);
		ptVotePlan.setDelFlag("0");
		ptVotePlan.setUpdateBy("admin");
		ptVotePlan.setUpdateTime(new Date());
		// ptVotePlan.setVotePlanSummary(request.getParameter("votePlanSummary"));
		// ptVotePlan.setVotePlanName(request.getParameter("votePlanName"));
		String state = getPara("state", "1");
		int state2 = Integer.parseInt(state);
		ptVotePlan.setState(state2);
		// String id = UUIDGenerator.getUUID();
		// ptVotePlan.setId(id);
		ptVotePlan.setCreateTime(new Date());
		boolean issave = ptVotePlanService.saveVotePlan(ptVotePlan);
		if (issave) {
			logger.info("保存完毕");
			return Result.successResult();
		} else {
			logger.info("保存失败");
			return Result.errorResult();
		}
	}

	@RequestMapping("deleteById")
	@ResponseBody
	public Result deleteById(HttpServletRequest request, String id) {
		logger.info("开始删除voteplan表决方案信息" + id);
		String[] ids = StringUtils.split(id, ",");
		Result result = new Result();
		// 如果表决方案在委员会中使用，返回result的code为2，并在前台页面提示。
		for (int i = 0; i < ids.length; i++) {
			boolean useFlag = ptVotePlanService.isUseFlag(ids[i]);
			if (useFlag) {
				result.setCode(2);
				return result;
			} else {
				continue;
			}
		}
		ptVotePlanService.delete(id);
		logger.info("结束删除voteplan表决方案信息" + id);
		return Result.successResult();
	}

	@RequestMapping("stopById")
	@ResponseBody
	public Result stopById(HttpServletRequest request, String id) {
		logger.info("开始停用voteplan表决方案信息" + id);
		Result result = new Result();
		// 如果表决方案在委员会中使用，返回result的code为2，并在前台页面提示。
		String[] ids = StringUtils.split(id, ",");
		for (int i = 0; i < ids.length; i++) {
			boolean useFlag = ptVotePlanService.isUseFlag(ids[i]);
			if (useFlag) {
				result.setCode(2);
				return result;
			} else {
				continue;
			}
		}
		ptVotePlanService.stop(id);
		logger.info("结束停用voteplan表决方案信息" + id);
		return Result.successResult();
	}

	@RequestMapping("startById")
	@ResponseBody
	public Result startById(HttpServletRequest request, String id) {
		logger.info("开始启用voteplan表决方案信息" + id);
		boolean flag = ptVotePlanService.startById(id);
		return flag ? Result.successResult() : Result.errorResult();
	}

}
