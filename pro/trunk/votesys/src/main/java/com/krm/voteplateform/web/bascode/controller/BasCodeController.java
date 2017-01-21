package com.krm.voteplateform.web.bascode.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.utils.BeanMapUtils;
import com.krm.voteplateform.web.bascode.model.BasCode;
import com.krm.voteplateform.web.bascode.service.BasCodeService;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;
import com.krm.voteplateform.web.bascodegroup.service.BasCodeGroupService;
import com.krm.voteplateform.web.util.SysUserUtils;


/**
 * 
 *
 */
@Controller
@RequestMapping("pt/bascode")
public class BasCodeController extends BaseController {
	
	@Autowired
	private BasCodeService basCodeService;
	
	@Autowired
	private BasCodeGroupService basCodeGroupService;
	/**
	 * 查询
	 * @param params 参数列表
	 * @param page
	 */
	@RequestMapping(value = "/listBasCode")
	public ModelAndView loadBasCodeList(HttpServletRequest req,@RequestParam(value="page",defaultValue="1",required=false) int page){
		ModelAndView mv = new ModelAndView("/bascode/listBasCode");
		//Pagination<BasCode> paging = new Pagination<BasCode>(10, page);
		Map<String,Object> params = new HashMap<String, Object>();
		//basCodeService.loadBasCodeList(paging,params);
		//mv.addObject("paging",paging);
		return mv;
	}
	
	/**
	 * 查询
	 * @param params 参数列表
	 * @param page
	 */
	@RequestMapping(value = "/detailCode")
	@ResponseBody
	public List<Map<String, Object>>  loadDetailCode(HttpServletRequest request){
		logger.info("开始查询编码字典或者数据反射类字典信息");
		String cgId = request.getParameter("id");
		String currentCommissionCode = SysUserUtils.getCurrentCommissionCode();
		BasCodeGroup baseCode = basCodeGroupService.loadById(currentCommissionCode, cgId);
		String pefPass=baseCode==null?null:baseCode.getPefPass();
		if(StringUtils.isEmpty(pefPass)){
			List<Map<String, Object>> selectCodes = basCodeService.selectByCode(currentCommissionCode,cgId);
			logger.info("结束查询编码字典信息");
			return selectCodes;
		}
		List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
		Map<String, Object> map = BeanMapUtils.transBean2Map(baseCode);
	//	listItems.toJSONString();
		listItems.add(map);
		logger.info("结束查询反射类字典信息");
		
		return listItems;
	}
	
	/**
	 * 显示详情
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/showBasCode")
	public ModelAndView loadbasCode(String id){
		ModelAndView mv = new ModelAndView("/bascode/showBasCode");
		//BasCode basCode = basCodeService.loadById(id);
		//mv.addObject("basCode",basCode);
		return mv;
	}
	
	@RequestMapping(value="/toAddBasCode")
	public String toAddBasCode(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		return "plateform/bascode/bascode_add";
	}
	
	/**
	 * 保存详情
	 * @param basCode
	 * @return
	 */
	@RequestMapping(value = "/saveBasCode",method=RequestMethod.POST)
	@ResponseBody
	public Result saveBasCode(HttpServletRequest request){
		logger.info("开始保存代码code字典信息");
		String code=SysUserUtils.getCurrentCommissionCode();
		BasCode basCode=new BasCode();
		basCode.setCgId(request.getParameter("id"));
		basCode.setEcode(request.getParameter("ecode"));
		String name = request.getParameter("name");
		basCode.setName(name);
		basCode.setMapName(name);
		int sysFlag=StringUtils.isEmpty(request.getParameter("sysFlag"))?1:1;//0:是，系统：1:不是
		basCode.setSysFlag(sysFlag);
		Boolean flag=basCodeService.saveBasCode(basCode,code);
		if(flag){
			return Result.successResult();
		}
		logger.info("结束保存获取代码code字典信息");
		return Result.errorResult();
		
	}
	

	@RequestMapping(value="/toEditBasCode")
	public ModelAndView toEditBasCode(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("plateform/bascode/bascode_edit");
		String code=SysUserUtils.getCurrentCommissionCode();
		String id=request.getParameter("id");
		BasCode basCode= basCodeService.loadById(code,id);
		mv.addObject("basCode",basCode);
		return mv;
	}
	
	/**
	 * 保存修改的
	 * @param BasCode
	 * @return
	 */
	@RequestMapping(value = "/updateBasCode",method=RequestMethod.POST)
	@ResponseBody
	public Result updateBasCode(HttpServletRequest request){
		logger.info("开始保存修改code字典信息");
		String code=SysUserUtils.getCurrentCommissionCode();
		String id=request.getParameter("id");
		BasCode basCode=new BasCode();
		basCode.setId(id);
		basCode.setMapName(request.getParameter("mapName"));
		Boolean flag=basCodeService.updateBasCode(code,basCode);
		if(flag){
			return Result.successResult();
		}
		logger.info("结束保存修改code字典信息");
		return Result.errorResult();
	}
	/**
	 * 删除
	 * @param BasCode
	 * @return
	 */
	@RequestMapping(value = "/removeBasCode",method=RequestMethod.POST)
	@ResponseBody
	public Result removeBasCode(HttpServletRequest request){
		logger.info("开始删除字典信息");
		String code=SysUserUtils.getCurrentCommissionCode();
		String id=request.getParameter("id");
		BasCode basCode=new BasCode();
		basCode.setId(id);
		Boolean flag=	basCodeService.removeBasCode(code,basCode);
		if(flag){
			return Result.successResult();
		}
		logger.info("结束删除字典信息");
		return Result.errorResult();
	
	}


}
