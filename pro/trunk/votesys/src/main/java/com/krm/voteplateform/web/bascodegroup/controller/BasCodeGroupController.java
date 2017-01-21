package com.krm.voteplateform.web.bascodegroup.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;
import com.krm.voteplateform.web.bascodegroup.service.BasCodeGroupService;
import com.krm.voteplateform.web.constants.SessionKeys;


/**
 * 
 *
 */
@Controller
@RequestMapping("pt/bascodegroup")
public class BasCodeGroupController extends BaseController {
	
	@Autowired
	private BasCodeGroupService basCodeGroupService;
	
	/**
	 * 查询
	 * @param params 参数列表
	 * @param page
	 */
	@RequestMapping(value = "listBasCodeGroup")
	public String loadBasCodeGroupList(HttpServletRequest request){
		//HttpSession session = request.getSession();
		//session.getAttribute(SessionKeys.COMMISSION_CODE_KEY);
		//ModelAndView mv = new ModelAndView();
		//Pagination<BasCodeGroup> paging = new Pagination<BasCodeGroup>(10, page);
		//Map<String,Object> params = new HashMap<String, Object>();
		//basCodeGroupService.loadBasCodeGroupList(paging,params);
		//mv.addObject("paging",paging);
		return "plateform/bascode/bascodegroup_list";
	}
	
	/**
	 * 跳转到页面
	 * 
	 * @param model
	 * @return html
	 */
	@RequestMapping(value = "ajaxCode")
	@ResponseBody
	public List<BasCodeGroup> toloadBasCodeGroupList(HttpServletRequest request,Model model) {
		logger.info("开始获取代码字典组信息");
		HttpSession session = request.getSession();
		
		String codeparameter = (String)session.getAttribute(SessionKeys.COMMISSION_CODE_KEY);
		List<BasCodeGroup> selectAlls = basCodeGroupService.selectAlls(codeparameter);
		//model.addAttribute("treeList", jsonString);
		logger.info("结束获取代码字典组信息");
		return selectAlls;
	}
	
	
	/**
	 * 编码名称组不能重复
	 * 
	 * @param model
	 * @return html
	 */
	@RequestMapping(value = "validCodeGroupName")
	@ResponseBody
	public Map<String,String> toValidCodeGroupName(HttpServletRequest request,Model model) {
		logger.info("开始验证获取代码字典组信息");
		String key=request.getParameter("name");
		String value=request.getParameter("param");
		//String basicFlag=request.getParameter("basicFlag");
		Map<String,Object> paramap=new HashMap<String,Object>();
		paramap.put(key, value);
	
		logger.info("开始验证名称{}信息" , paramap);
		
		boolean flag = basCodeGroupService.selectName(paramap);
		  Map<String , String> map = new HashMap<String, String>();     
		  if (flag) {  
		        map.put("info", "系统中已经存在该名称");  
		        map.put("status", "n");  
		    }else {  
		        map.put("info", "该名称可用");  
		        map.put("status", "y");  
		    }  
		  logger.info("结束验证获取代码字典组信息",value);
		 return map; 
	}

	
	/**
	 * 显示详情
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/showBasCodeGroup")
	public ModelAndView loadbasCodeGroup(String id){
		ModelAndView mv = new ModelAndView("/plateform/showBasCodeGroup");
	//	BasCodeGroup basCodeGroup = basCodeGroupService.loadById(id);
		//mv.addObject("basCodeGroup",basCodeGroup);
		return mv;
	}
	
	@RequestMapping(value="/toAddBasCodeGroup")
	public String toAddBasCodeGroup(HttpServletRequest request){
		return "/plateform/bascode/bascodegroup_add";
	}
	
	/**
	 * 保存详情
	 * @param basCodeGroup
	 * @return
	 */
	@RequestMapping(value = "/saveBasCodeGroup",method=RequestMethod.POST)
	@ResponseBody
	public Result saveBasCodeGroup(HttpServletRequest request){
		logger.info("开始保存代码字典组信息");
		HttpSession session = request.getSession();
		String code=(String)session.getAttribute(SessionKeys.COMMISSION_CODE_KEY);
		BasCodeGroup basCodeGroup=new BasCodeGroup();
		basCodeGroup.setCodeGroupName(request.getParameter("codeGroupName"));
		basCodeGroup.setPefPass(request.getParameter("pefPass"));
		int state=StringUtils.isEmpty(request.getParameter("state"))?1:0;//0:是：1:不是
		basCodeGroup.setState(state);
		//ModelAndView mv = new ModelAndView("redirect:/plateform/listBasCodeGroup");
		Boolean flag = basCodeGroupService.saveBasCodeGroup(basCodeGroup,code);
		if(flag){
			return Result.successResult();
		}
		logger.info("结束保存获取代码字典组信息");
		return Result.errorResult();
	}
	

	@RequestMapping(value="/toEditBasCodeGroup")
	public ModelAndView toEditBasCodeGroup(HttpServletRequest request){
		logger.info("开始显示编辑代码字典组信息");
		ModelAndView mv = new ModelAndView("plateform/bascode/bascodegroup_edit");
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		String code=(String)session.getAttribute(SessionKeys.COMMISSION_CODE_KEY);
		BasCodeGroup basCodeGroup= basCodeGroupService.loadById(code,id);
		mv.addObject("basCodeGroup",basCodeGroup);
		logger.info("结束显示编辑代码字典组信息");
		return mv;
	}
	
	/**
	 * 保存修改的
	 * @param BasCodeGroup
	 * @return
	 */
	@RequestMapping(value = "/updateBasCodeGroup",method=RequestMethod.POST)
	@ResponseBody
	public Result updateBasCodeGroup(HttpServletRequest request){
		logger.info("开始保存编辑代码字典信息");
		HttpSession session = request.getSession();
		String code=(String)session.getAttribute(SessionKeys.COMMISSION_CODE_KEY);
		BasCodeGroup basCodeGroup=new BasCodeGroup();
		String id = request.getParameter("id");
		basCodeGroup.setId(id);
		basCodeGroup.setCodeGroupName(request.getParameter("codeGroupName"));
		basCodeGroup.setPefPass(request.getParameter("pefPass"));
		int state=StringUtils.isEmpty(request.getParameter("state"))?1:0;//0:是：1:不是
		basCodeGroup.setState(state);
		//ModelAndView mv = new ModelAndView("redirect:/plateform/listBasCodeGroup");
		Boolean flag = basCodeGroupService.updateBasCodeGroup(basCodeGroup,code);
		if(flag){
			Result success=new Result(Result.SUCCESS, Result.SUCCESS_MSG, id);;
			return success;
		}
		logger.info("结束保存编辑代码字典信息");
		return Result.errorResult();
	}
	/**
	 * 删除
	 * @param BasCodeGroup
	 * @return
	 */
	@RequestMapping(value = "/removeBasCodeGroup")
	@ResponseBody
	public Result removeBasCodeGroup(HttpServletRequest request){
		logger.info("开始删除字典组信息");
		BasCodeGroup basCodeGroup=new BasCodeGroup();
		HttpSession session = request.getSession();
		String code=(String)session.getAttribute(SessionKeys.COMMISSION_CODE_KEY);
		basCodeGroup.setId(request.getParameter("id"));
		Boolean flag=basCodeGroupService.removeBasCodeGroup(basCodeGroup,code);
		if(flag){
			return Result.successResult();
		}
		logger.info("结束删除字典组信息");
		return Result.errorResult();
	}


}
