package com.krm.voteplateform.web.mettingmem.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.mettingmem.model.BasProComeMem;
import com.krm.voteplateform.web.mettingmem.service.BasProComeMemService;


/**
 * 
 *
 */
@Controller
@RequestMapping("ptsystems/procomemem")
public class BasProComeMemController extends BaseController {
	
	@Autowired
	private BasProComeMemService basProComeMemService;
	
	
	/**
	 * 显示详情
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/showBasProComeMem")
	public ModelAndView loadbasProComeMem(String id){
		ModelAndView mv = new ModelAndView("/mettingmem/showBasProComeMem");
		BasProComeMem basProComeMem = basProComeMemService.loadById(id);
		mv.addObject("basProComeMem",basProComeMem);
		return mv;
	}
	
	@RequestMapping(value="/toAddBasProComeMem")
	public String toAddBasProComeMem(){
		return "/mettingmem/addBasProComeMem";
	}
	
	/**
	 * 保存详情
	 * @param basProComeMem
	 * @return
	 */
	@RequestMapping(value = "/saveBasProComeMem",method=RequestMethod.POST)
	public ModelAndView saveBasProComeMem(BasProComeMem basProComeMem){
		ModelAndView mv = new ModelAndView("redirect:/mettingmem/listBasProComeMem");
		//basProComeMemService.saveBasProComeMem(basProComeMem);
		return mv;
	}
	

	@RequestMapping(value="/toEditBasProComeMem")
	public ModelAndView toEditBasProComeMem(String id){
		ModelAndView mv = new ModelAndView("/mettingmem/editBasProComeMem");
		BasProComeMem basProComeMem= basProComeMemService.loadById(id);
		mv.addObject("basProComeMem",basProComeMem);
		return mv;
	}
	
	/**
	 * 保存修改的
	 * @param BasProComeMem
	 * @return
	 */
	@RequestMapping(value = "/updateBasProComeMem",method=RequestMethod.POST)
	public ModelAndView updateBasProComeMem(BasProComeMem basProComeMem){
		ModelAndView mv = new ModelAndView("redirect:/mettingmem/listBasProComeMem");
		basProComeMemService.updateBasProComeMem(basProComeMem);
		return mv;
	}
	/**
	 * 删除
	 * @param BasProComeMem
	 * @return
	 */
	@RequestMapping(value = "/removeBasProComeMem",method=RequestMethod.POST)
	public ModelAndView removeBasProComeMem(HttpServletRequest req,BasProComeMem basProComeMem){
		ModelAndView mv = new ModelAndView("redirect:/mettingmem/listBasProComeMem");
		basProComeMemService.removeBasProComeMem(basProComeMem);
		return mv;
	}


}
