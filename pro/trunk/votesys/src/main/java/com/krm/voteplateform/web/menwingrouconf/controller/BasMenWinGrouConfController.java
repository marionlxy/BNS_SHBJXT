package com.krm.voteplateform.web.menwingrouconf.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.menwingrouconf.model.BasMenWinGrouConf;
import com.krm.voteplateform.web.menwingrouconf.service.BasMenWinGrouConfService;


/**
 * 菜单窗体拓展明细分组配置表
 *
 */
@Controller
@RequestMapping("pt/menwingrouconf")
public class BasMenWinGrouConfController extends BaseController {
	
	@Autowired
	private BasMenWinGrouConfService basMenWinGrouConfService;
	
	/**
	 * 查询菜单窗体拓展明细分组配置表
	 * @param params 参数列表
	 * @param page
	 */
	@RequestMapping(value = "/listBasMenWinGrouConf")
	public ModelAndView loadBasMenWinGrouConfList(HttpServletRequest req,@RequestParam(value="page",defaultValue="1",required=false) int page){
		ModelAndView mv = new ModelAndView("/menwingrouconf/listBasMenWinGrouConf");
		//Pagination<BasMenWinGrouConf> paging = new Pagination<BasMenWinGrouConf>(10, page);
		Map<String,Object> params = new HashMap<String, Object>();
		//basMenWinGrouConfService.loadBasMenWinGrouConfList(paging,params);
		//mv.addObject("paging",paging);
		return mv;
	}
	
	/**
	 * 显示菜单窗体拓展明细分组配置表详情
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/showBasMenWinGrouConf")
	public ModelAndView loadbasMenWinGrouConf(String id){
		ModelAndView mv = new ModelAndView("/menwingrouconf/showBasMenWinGrouConf");
		BasMenWinGrouConf basMenWinGrouConf = basMenWinGrouConfService.loadById(id);
		mv.addObject("basMenWinGrouConf",basMenWinGrouConf);
		return mv;
	}
	
	@RequestMapping(value="/toAddBasMenWinGrouConf")
	public String toAddBasMenWinGrouConf(){
		return "/menwingrouconf/addBasMenWinGrouConf";
	}
	
	/**
	 * 保存菜单窗体拓展明细分组配置表详情
	 * @param basMenWinGrouConf
	 * @return
	 */
	@RequestMapping(value = "/saveBasMenWinGrouConf",method=RequestMethod.POST)
	public ModelAndView saveBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf){
		ModelAndView mv = new ModelAndView("redirect:/menwingrouconf/listBasMenWinGrouConf");
		basMenWinGrouConfService.saveBasMenWinGrouConf(basMenWinGrouConf);
		return mv;
	}
	

	@RequestMapping(value="/toEditBasMenWinGrouConf")
	public ModelAndView toEditBasMenWinGrouConf(String id){
		ModelAndView mv = new ModelAndView("/menwingrouconf/editBasMenWinGrouConf");
		BasMenWinGrouConf basMenWinGrouConf= basMenWinGrouConfService.loadById(id);
		mv.addObject("basMenWinGrouConf",basMenWinGrouConf);
		return mv;
	}
	
	/**
	 * 保存修改的菜单窗体拓展明细分组配置表
	 * @param BasMenWinGrouConf
	 * @return
	 */
	@RequestMapping(value = "/updateBasMenWinGrouConf",method=RequestMethod.POST)
	public ModelAndView updateBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf){
		ModelAndView mv = new ModelAndView("redirect:/menwingrouconf/listBasMenWinGrouConf");
		basMenWinGrouConfService.updateBasMenWinGrouConf(basMenWinGrouConf);
		return mv;
	}
	/**
	 * 删除菜单窗体拓展明细分组配置表
	 * @param BasMenWinGrouConf
	 * @return
	 */
	@RequestMapping(value = "/removeBasMenWinGrouConf",method=RequestMethod.POST)
	public ModelAndView removeBasMenWinGrouConf(HttpServletRequest req,BasMenWinGrouConf basMenWinGrouConf){
		ModelAndView mv = new ModelAndView("redirect:/menwingrouconf/listBasMenWinGrouConf");
		basMenWinGrouConfService.removeBasMenWinGrouConf(basMenWinGrouConf);
		return mv;
	}


}
