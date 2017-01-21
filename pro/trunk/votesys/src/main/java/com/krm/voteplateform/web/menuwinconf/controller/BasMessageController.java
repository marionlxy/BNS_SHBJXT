package com.krm.voteplateform.web.menuwinconf.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.menuwinconf.model.BasMessage;
import com.krm.voteplateform.web.menuwinconf.service.BasMessageService;
import com.krm.voteplateform.web.sys.model.BasDict;
import com.krm.voteplateform.web.sys.model.BasExpandGroup;

@Controller
@RequestMapping("pt/basMessage")
public class BasMessageController extends BaseController{
	
	Logger logger = LoggerFactory.getLogger(BasMessage.class);
	
	
	@Autowired
	private BasMessageService basMessageService;
	
	/**
	 * 跳转basMessage_list提示页面basMessage_list
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toBasMessage")
	public String toBasMessage(HttpServletRequest request, Model model,String massCode){
		String massCodes = request.getParameter("massCode");
		request.setAttribute("massCodes", massCodes);
		return "plateform/menuwinconf/basMessage_list";
	}
	
	
	/**
	 * 查询所有提示信息集
	 * @author zhangYuHai
	 * @param model
	 * @return
	 */
	@RequestMapping("getBasMessageList")
	@ResponseBody
	public List<Map<String,Object>> getBasMessageList(Model model,String massCodes){
		List<Map<String, Object>> basMessageList =basMessageService.finbasMessage(massCodes);
		return basMessageList;
	}
	
	/**
	 * 根据id编辑提示信息的每条委员会文本内容
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("upBasMessage")
	public String upBasMessage(HttpServletRequest request,Model model,String id){
		logger.info("开始根据id查询每条字典数据 upBasMessage");
		logger.info("从session中获得codes");
		BasMessage basMessage = basMessageService.upBasMessageText(id);
		model.addAttribute("basMessage",basMessage);
		logger.info("结束查询每条字典数据结束返回到sysDic_edit.html页面");
		return "plateform/menuwinconf/basMessage_edit";
	}
	
	
	/**
	 * 保存修改提示信息的每条委员会文本内容
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param mapCnName
	 * @param mapPrecision
	 * @param id
	 * @return
	 * 
	 */
	@RequestMapping("savUpBasMessage")
	@ResponseBody
	public Result savUpBasMessage (HttpServletRequest request){
		Boolean	flag = false;
		BasMessage basMessage = new BasMessage();
		logger.info("保存修改提示信息的每条委员会文本内容");
		basMessage.setId(request.getParameter("id"));
		basMessage.setCommText(request.getParameter("commText"));
		flag = basMessageService.savUpBasMessage(basMessage);
		if(flag){
			return Result.successResult();
		}else{
			return Result.errorResult();
		}
		
	}
}
