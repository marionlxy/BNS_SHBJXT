package com.krm.voteplateform.web.menulistconf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.menulistconf.model.BasMenuListConf;
import com.krm.voteplateform.web.menulistconf.service.MenuListConfService;
import com.krm.voteplateform.web.resource.model.BasResource;

@Controller
@RequestMapping("pt/menulist")
public class MenuListConfController extends BaseController {

	@Autowired
	private MenuListConfService menuListConfService;

	@RequestMapping("listMenuConf")
	@ResponseBody
	public List<Map<String, Object>> listMenuConf(String resid) {
		logger.info("开始查询menu_list_conf表");
		List<Map<String, Object>> listAll = menuListConfService.listMenuConf(resid);
		logger.info("查询menu_list_conf表完毕");
		return listAll;

	}
	
	@RequestMapping("listMuenAll")
	public String listMuenAll(Model model,String resid) {
		model.addAttribute("resid", resid);
		return "plateform/menuListConf/menuConf_list";
	}
	
	@RequestMapping("beginEdit")
	public String beginEdit(Model model,String resid) {
		List<Map<String, Object>> fields = menuListConfService.getAllField();
		model.addAttribute("fields", fields);
		model.addAttribute("resid", resid);
		return "plateform/menuListConf/menuConf_edit";
	}

	@RequestMapping("listField")
	@ResponseBody
	public List<Map<String, Object>> listField() {
		List<Map<String, Object>> fields = menuListConfService.getAllField();
		return fields;

	}

	@RequestMapping("listType")
	@ResponseBody
	public List<String> listType() {
		List<String> types = menuListConfService.getListType();
		return types;

	}

	@RequestMapping("listDataSource")
	@ResponseBody
	public List<Map<String, Object>> listDataSource() {
		List<Map<String, Object>> types = menuListConfService.getDataSource();
		return types;

	}

	@RequestMapping("saveMenu")
	@ResponseBody
	public Result saveMenu(HttpServletRequest request,String resid) {
		String repid = request.getParameter("fields");
		String offineFlag = getPara("offineFlag", "1");
		String type = request.getParameter("type");
		String datasource = request.getParameter("dataSource");
		Result result=new Result();
		if (menuListConfService.isExistDicid(repid,resid)) {
			BasMenuListConf basMenuListConf = new BasMenuListConf();
			basMenuListConf.setDicid(repid);
			basMenuListConf.setSearchFlag(offineFlag);
			basMenuListConf.setSearchType(type);
			basMenuListConf.setDatasource(datasource);
			basMenuListConf.setDelFlag("0");
			basMenuListConf.setResid(resid);
			basMenuListConf.setUseFlag("1");
			boolean insert = menuListConfService.saveMenuConf(basMenuListConf);
			if (insert) {
				result = Result.successResult();
			}else {
				result=Result.errorResult();
			}

		} else {
			logger.info("字段已经存在");
			request.setAttribute("a", "字段已经存在");
			result=Result.errorResult();
		}
		logger.info("增加bas_menu_list_conf表操作完毕");
		return result;

	}

	@RequestMapping("toEdit")
	public String toEdit(String id, Model model, HttpServletResponse response) {
		logger.info("开始跳入编辑页面");
		// response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = menuListConfService.getMenuForm(id);
		model.addAttribute("map", map);
		logger.info("进入编辑页面");
		return "plateform/menuListConf/menuConf_update";

	}

	@RequestMapping("saveMenuUpdate")
	@ResponseBody
	public Result saveMenuUpdate(HttpServletRequest request) {
		BasMenuListConf basMenuListConf = new BasMenuListConf();
		String id = request.getParameter("id");
		basMenuListConf.setId(id);

		String searchFlag = getPara("offineFlag", "1");
		basMenuListConf.setSearchFlag(searchFlag);

		String searchType = request.getParameter("type");
		basMenuListConf.setSearchType(searchType);

		String datasource = request.getParameter("dataSource");
		basMenuListConf.setDatasource(datasource);
		Result result = new Result();
		boolean isUpdateSuccess = menuListConfService.saveMeUpdate(basMenuListConf);
		
		/*
		 * if (isUpdateSuccess) { logger.info("更新完毕"); return "redirect:/"; }
		 */
		if (isUpdateSuccess) {
			logger.info("更新完毕");
			result = Result.successResult();
		}else {
			logger.info("更新失败");
			result=Result.errorResult();
		}
		return result;

	}

	@RequestMapping("deleMenuById")
	@ResponseBody
	public Result deleMenuById(String ids) {
		logger.info("开始删除bas_menu_list_conf表" + ids);
		menuListConfService.deleteById(ids);
		logger.info("结束删除bas_menu_list_conf表" + ids);
		return Result.successResult();
	}
	
	@RequestMapping("valiMenuField")
	@ResponseBody
	public Map<String, String> valiCommissionCode(HttpServletRequest request) {
		String repid=request.getParameter("param");
		String resid = getPara("resid");
		logger.info("开始验证bas_menu_list_conf表" + repid);
			boolean flag = menuListConfService.isExistDicid(repid,resid);
		  Map<String , String> map = new HashMap<String, String>();     
		  if (!flag) {  
		        map.put("info", "表中该字段已经存在");
		        map.put("status", "n");  
		    }else {  
		        map.put("info", "该字段可用"); 
		        map.put("status", "y");  
		    }  
		  logger.info("结束验证bas_menu_list_conf表信息" + repid);
		    return map;  	
	}

}
