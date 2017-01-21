package com.krm.voteplateform.web.mettingmem.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.mettingmem.model.BasMettingMem;
import com.krm.voteplateform.web.mettingmem.service.BasMettingMemService;


/**
 * 
 *
 */
@Controller
@RequestMapping("ptsystems/mettingmem")
public class BasMettingMemController extends BaseController {
	
	@Autowired
	private BasMettingMemService basMettingMemService;
	

	/**
	 * 显示详情
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/showBasMettingMem")
	public ModelAndView loadbasMettingMem(String id){
		ModelAndView mv = new ModelAndView("/mettingmem/showBasMettingMem");
		BasMettingMem basMettingMem = basMettingMemService.loadById(id);
		mv.addObject("basMettingMem",basMettingMem);
		return mv;
	}
	
	@RequestMapping(value="/toAddBasMettingMem")
	public String toAddBasMettingMem(){
		return "/mettingmem/addBasMettingMem";
	}
	

	

	@RequestMapping(value="/toEditBasMettingMem")
	public ModelAndView toEditBasMettingMem(String id){
		ModelAndView mv = new ModelAndView("/mettingmem/editBasMettingMem");
		BasMettingMem basMettingMem= basMettingMemService.loadById(id);
		mv.addObject("basMettingMem",basMettingMem);
		return mv;
	}
	
	/**
	 * 保存修改的
	 * @param BasMettingMem
	 * @return
	 */
	@RequestMapping(value = "/updateBasMettingMem",method=RequestMethod.POST)
	public ModelAndView updateBasMettingMem(BasMettingMem basMettingMem){
		ModelAndView mv = new ModelAndView("redirect:/mettingmem/listBasMettingMem");
		basMettingMemService.updateBasMettingMem(basMettingMem);
		return mv;
	}
	/**
	 * 删除
	 * @param BasMettingMem
	 * @return
	 */
	@RequestMapping(value = "/removeBasMettingMem",method=RequestMethod.POST)
	public ModelAndView removeBasMettingMem(HttpServletRequest req,BasMettingMem basMettingMem){
		ModelAndView mv = new ModelAndView("redirect:/mettingmem/listBasMettingMem");
		basMettingMemService.removeBasMettingMem(basMettingMem);
		return mv;
	}


}
