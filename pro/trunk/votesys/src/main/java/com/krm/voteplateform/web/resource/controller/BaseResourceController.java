package com.krm.voteplateform.web.resource.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.stat.TableStat.Mode;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.utils.BeanMapUtils;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;
import com.krm.voteplateform.web.constants.SessionKeys;
import com.krm.voteplateform.web.resource.model.BasResource;
import com.krm.voteplateform.web.resource.service.BaseResourceService;
import com.krm.voteplateform.web.util.SysUserUtils;
import com.krm.voteplateform.web.voteplan.model.PtVotePlan;

@Controller
@RequestMapping("pt/ptresource")
public class BaseResourceController extends BaseController{
	
	@Autowired
	private BaseResourceService resourceService;
	
	@RequestMapping("resourceList")
	public String resourceList(Model model) {
		logger.info("进入信息提示页面");
		return "plateform/ptresource/resource_list";

	}
	
	@RequestMapping("addVotePlan")
	public String addVotePlan(HttpServletRequest request, Model model,String id) {
		model.addAttribute("id", id);
		Object obj = resourceService.getObject(id);
		BasResource resource =(BasResource) obj;
		String commText = resource.getCommText();
		//String helpCode = resource.getHelpCode();
		String sysText = resource.getSysText();
		model.addAttribute("commText", commText);
		model.addAttribute("helpCode", null);
		model.addAttribute("sysText", sysText);
		return "plateform/ptresource/resource_edit";

	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, Model model) {
		return "plateform/voteplan/voteplan_edit";

	}
	
	@RequestMapping("listResource")
	@ResponseBody
	public List<Map<String, Object>>  listResource(HttpServletRequest request, Model model) {
		logger.info("开始查询resource资源表");
		List<Map<String, Object>> listAll = resourceService.listResource();
		//model.addAttribute("listAll", listAll);
		logger.info("结束查询resource资源表");
		return listAll;

	}
	
	@RequestMapping("saveResource")
	public String saveResource(BasResource resource, HttpServletRequest request) {
		String id = resource.getId();
		boolean  isUpdateSuccess = resourceService.saveObject(resource);
		if (isUpdateSuccess) {
			logger.info("更新完毕");
			return "redirect:/";
		}
		return "";
		
	}
	
	@RequestMapping("toMenuList")
	public String toMenuList(){
		return "plateform/ptresource/menumanage_list";
	}
	
	
	@RequestMapping("toloadMenuTreeList")
	@ResponseBody
	public List<BasResource> toloadMenuTreeList(HttpServletRequest request,Model model) {
		logger.info("开始获取资源表中的数据");
		List<BasResource> selectAlls = resourceService.getTreeList();
		logger.info("结束获取资源表中的数据");
		return selectAlls;
	}
	
	@RequestMapping("clickToList")
	@ResponseBody
	public List<Map<String, Object>>  clickToList(HttpServletRequest request){
		logger.info("开始查询编码字典或者数据反射类字典信息");
		String cgId = request.getParameter("id");
		List<Map<String, Object>> list = resourceService.loadResourceById(cgId);
		logger.info("结束查询反射类字典信息");
		return list;
	}
	
	
	@RequestMapping("toEditMenu")
	public String toEditMenu(Model model,String id){
		List<Map<String, Object>> list = resourceService.loadResourceById(id);
		Map<String, Object> map =list.get(0);
		List<Map<String, Object>> modes =resourceService.findMode();//查询下拉框模板
		model.addAttribute("modes", modes);
		model.addAttribute("map", map);
		return "plateform/ptresource/menumanage_edit";
	}
	
	@RequestMapping("saveUpdateMenu")
	@ResponseBody
	public Result saveUpdateMenu(){
		BasResource baseResource =new BasResource();
		String id =getPara("id");
		String commText =getPara("commText");
		String tempId =getPara("tempId");
		
		baseResource.setId(id);
		baseResource.setCommText(commText);
		baseResource.setTempId(tempId);
		
		boolean update =resourceService.updateMenu(baseResource);
		return update?Result.successResult():Result.errorResult();
		
		
		
	}

}
