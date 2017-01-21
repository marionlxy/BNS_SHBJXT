package com.krm.voteplateform.web.pageTemplet.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.pageTemplet.model.PtVotePageTemplet;
import com.krm.voteplateform.web.pageTemplet.service.PtVotePageTempletService;

@Controller
@RequestMapping("pt/votePageTemplet")
public class PtVotePageTempletController extends BaseController{

	@Autowired
	private PtVotePageTempletService ptVotePageTempletService;
	
	@RequestMapping("toPageTemplet")
	public String toPageTemplet(){
		logger.info("跳入页面模板界面");
		return "plateform/pagetemplet/pagetemplet_list";
	}
	
	@RequestMapping("listPageTemplet")
	@ResponseBody
	public List<Map<String, Object>> listPageTemplet(){
		logger.info("开始查询ptVotePageTemplet表数据");
		List<Map<String, Object>> list = ptVotePageTempletService.listPT();
		logger.info("查询完毕");
		return list;
	}
	
	@RequestMapping("toAddPageTemplet")
	public String toAddPageTemplet(String id,Model model){
		logger.info("跳入新增页面模板界面");
		
		//修改操作
		if (id!=null) {
			Map<String, Object> map =ptVotePageTempletService.getPageTempletById(id);
			model.addAttribute("id", id);
			model.addAttribute("map", map);
		}
		return "plateform/pagetemplet/pagetemplet_add";
	}
	
	@RequestMapping(value="savePageTemplet", method=RequestMethod.POST)
	@ResponseBody
	public Result savePageTemplet(@ModelAttribute PtVotePageTemplet ptVotePageTemplet){
		boolean insert= ptVotePageTempletService.savePage(ptVotePageTemplet);
		return insert?Result.successResult():Result.errorResult();
	}
	
	@RequestMapping("updatePageTemplet")
	@ResponseBody
	public Result updatePageTemplet(){
		PtVotePageTemplet ptVotePageTemplet = new PtVotePageTemplet();
		String id = getPara("id");
		String name = getPara("name");
		String fileName = getPara("fileName");
		String explain = getPara("explain");
		String type = getPara("type");
		
		ptVotePageTemplet.setId(id);
		ptVotePageTemplet.setName(name);
		ptVotePageTemplet.setFileName(fileName);
		ptVotePageTemplet.setExplain(explain);
		ptVotePageTemplet.setType(type);
		
		boolean update = ptVotePageTempletService.updatePageTemplet(ptVotePageTemplet);
		return update?Result.successResult():Result.errorResult();
		
	}
	
	@RequestMapping("valiMenuField")
	@ResponseBody
	public Map<String, String> valiMenuField(HttpServletRequest request) {
		String name=request.getParameter("name");
		try {
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			name = java.net.URLDecoder.decode(name, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String id=getPara("id");
		logger.info("开始验证" + name);
			boolean flag = ptVotePageTempletService.isRepeat(name,id);
		  Map<String , String> map = new HashMap<String, String>();     
		  if (flag) {  
		        map.put("info", "模板名称已经存在");
		        map.put("status", "n");  
		    }else {  
		        map.put("info", "该模板名称可用"); 
		        map.put("status", "y");  
		    }  
		  logger.info("验证完毕"+ name);
		    return map;  	
	}
		
	
}
