package com.krm.voteplateform.web.ptvoterestmpl.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.ptvoterestmpl.model.PtVoteResTmpl;
import com.krm.voteplateform.web.ptvoterestmpl.service.PtVoteResTmplService;


/**
 * 
 *
 */
@Controller
@RequestMapping("/pt/voterestmpl")
public class PtVoteResTmplController extends BaseController {
	

	private PtVoteResTmplService ptVoteResTmplService;
	
	/**
	 * 查询
	 * @param params 参数列表
	 * @param page
	 */
	@RequestMapping(value = "/listPtVoteResTmpl")
	public ModelAndView loadPtVoteResTmplList(HttpServletRequest req,@RequestParam(value="page",defaultValue="1",required=false) int page){
		ModelAndView mv = new ModelAndView("/plateform/listPtVoteResTmpl");
		
		return mv;
	}
	
	/**
	 * 显示详情
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/showPtVoteResTmpl")
	public ModelAndView loadptVoteResTmpl(String id){
		ModelAndView mv = new ModelAndView("/plateform/showPtVoteResTmpl");
		
		return mv;
	}
	
	@RequestMapping(value="/toAddPtVoteResTmpl")
	public String toAddPtVoteResTmpl(){
		return "/plateform/addPtVoteResTmpl";
	}
	
	/**
	 * 保存详情
	 * @param ptVoteResTmpl
	 * @return
	 */
	@RequestMapping(value = "/savePtVoteResTmpl",method=RequestMethod.POST)
	public ModelAndView savePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl){
		ModelAndView mv = new ModelAndView("redirect:/plateform/listPtVoteResTmpl");
		//ptVoteResTmplService.savePtVoteResTmpl(ptVoteResTmpl);
		return mv;
	}
	

	@RequestMapping(value="/toEditPtVoteResTmpl")
	public ModelAndView toEditPtVoteResTmpl(String id){
		ModelAndView mv = new ModelAndView("/plateform/editPtVoteResTmpl");
		//PtVoteResTmpl ptVoteResTmpl= ptVoteResTmplService.loadById(id);
		//mv.addObject("ptVoteResTmpl",ptVoteResTmpl);
		return mv;
	}
	
	/**
	 * 保存修改的
	 * @param PtVoteResTmpl
	 * @return
	 */
	@RequestMapping(value = "/updatePtVoteResTmpl",method=RequestMethod.POST)
	public ModelAndView updatePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl){
		ModelAndView mv = new ModelAndView("redirect:/plateform/listPtVoteResTmpl");
		///ptVoteResTmplService.updatePtVoteResTmpl(ptVoteResTmpl);
		return mv;
	}
	/**
	 * 删除
	 * @param PtVoteResTmpl
	 * @return
	 */
	@RequestMapping(value = "/removePtVoteResTmpl",method=RequestMethod.POST)
	public ModelAndView removePtVoteResTmpl(HttpServletRequest req,PtVoteResTmpl ptVoteResTmpl){
		ModelAndView mv = new ModelAndView("redirect:/plateform/listPtVoteResTmpl");
		//ptVoteResTmplService.removePtVoteResTmpl(ptVoteResTmpl);
		return mv;
	}


}
