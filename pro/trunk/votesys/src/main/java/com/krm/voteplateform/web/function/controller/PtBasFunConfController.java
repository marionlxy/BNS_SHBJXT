package com.krm.voteplateform.web.function.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.common.utils.UUIDGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.function.model.BasFunConf;
import com.krm.voteplateform.web.function.service.PtBasFunConfService;

@Controller
@RequestMapping("pt/FunConfMapper")
public class PtBasFunConfController extends BaseController {
	
	@Autowired
	private PtBasFunConfService ptBasFunConfService;
	//数据是否启用的判断
	@RequestMapping("saveUpdate")
	@ResponseBody
	public Result saveUpdate(HttpServletRequest request, String basdict) {
		String basdicts = request.getParameter("basdict");
		Boolean flag = ptBasFunConfService.saveUpdate(basdicts);
		if(flag){
			return Result.successResult();
		}else{
			return Result.errorResult();
		}
		
  	    }
	//列表后面的修改功能selectCommtext
		@RequestMapping("selectCommtext")
		public String selectCommtext(String tempId, HttpServletRequest request,Model model,String id){
			logger.info("开始展示修改功能的数据 ");
			BasFunConf basFunConf = ptBasFunConfService.selectCommtext(id);
			Timestamp nowTimestamp = DateUtils.getNowTimestamp();
			basFunConf.setUpdateTime(nowTimestamp);
			List<Map<String, Object>> list1 = ptBasFunConfService.selectpull();
			model.addAttribute("list1", list1);
			model.addAttribute("basFunConf",basFunConf);
			model.addAttribute("useFlag",request.getParameter("useFlag"));
			logger.info("展示修改页面完成");
			return "plateform/sysDic/sysDic_function_update";
		}
	/*
	 * 去页面
	 * */
	@RequestMapping("sysDic_function")
		public String sysDic_function(HttpServletRequest request, Model model,String Mlcid) {
			model.addAttribute("Mlcid", Mlcid);
			return "plateform/sysDic/sysDic_function";
		}
	//列表展示
	@RequestMapping(value="showList")
	@ResponseBody
	public List<Map<String, Object>> showlist(HttpServletRequest request,Model model,String Mlcid) {
		logger.info("开始展示ptBasFunConfService操作功能的表");
		List<Map<String, Object>> listAll = ptBasFunConfService.selectAll(Mlcid);
		logger.info("結束展示ptBasFunConfService操作功能表");
		return listAll;
	}
	/*
	 * 展示操作功能的新增功能页面
	 * */
	@RequestMapping(value = "showAddfunction")
	public String showAddfunction(HttpServletRequest request,Model model,String Mlcid) {
		List<Map<String, Object>> list1 = ptBasFunConfService.selectpull();
		model.addAttribute("Mlcid", Mlcid);
		model.addAttribute("list1", list1);
		return "plateform/sysDic/sysDic_add";
	}
	//验证system是否重复
	@RequestMapping("valiBasFunConfSysCode")
	@ResponseBody
	public Map<String, String> valiBasFunConfSysCode(HttpServletRequest request) {
		String str=request.getParameter("param").toUpperCase();
		String functionCode = str.toLowerCase();
		logger.info("开始验证编码{}信息" , functionCode);
		  boolean flag = ptBasFunConfService.selectSysCode(functionCode);
		  Map<String , String> map = new HashMap<String, String>();     
		  if (flag) {      
		        map.put("info", "系统中已经存在该编码");  
		        map.put("status", "n");  
		    }else {  
		        map.put("info", "该编码可用");  
		        map.put("status", "y");  
		    }  
		  logger.info("结束验证编码{}信息", functionCode);
		    return map;  	
	}
	/*
	 * 在操作功能里面进行新增保存
	 * */
	@RequestMapping("saveBasFunConf")
	@ResponseBody
	public Result saveBasFunConf(@ModelAttribute BasFunConf basFunConf,HttpServletRequest request) {
		logger.info("开始增加BasFunConf操作功能表");
		//传过来获取下拉框里面的数值
		String tempName = ptBasFunConfService.gettempNameById(basFunConf.getTempId());
		if(tempName == null){
			tempName = "   ";
		}
			basFunConf.setTempName(tempName);
			basFunConf.setSysText(basFunConf.getCommtext());
			//需要判断页面上的checkbox是否被选中然后再传1 或者 0 
		String useFlag=getPara("useFlag", "0");
			basFunConf.setUseFlag(useFlag);
			basFunConf.setDelFlag("0");
			basFunConf.setType("3");
			basFunConf.setFcid((UUIDGenerator.getUUID()));
			basFunConf.setMlcid(request.getParameter("Mlcid"));
			boolean issave = ptBasFunConfService.savaFunctionTable(basFunConf);
			logger.info("增加结束BasFunConf操作功能表结束");
			if (issave) {
				logger.info("保存完毕");
				return Result.successResult();
			}else {
				logger.info("保存失败");
				return Result.errorResult();
			}
	}
}
