package com.krm.voteplateform.web.plateformorg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.plateformorg.service.PtPlateformOrgService;

@Controller
@RequestMapping("pt/plateformorg")
public class PtPlateformOrgController extends BaseController{
	
	@Autowired
	private PtPlateformOrgService ptPlateformOrgService;
	
	@RequestMapping("listOrgTree")
	@ResponseBody
	public List<Map<String, Object>> listOrgTree(){
		List<Map<String, Object>> list=ptPlateformOrgService.getOrgAll();
		return list;
	}
	
	@RequestMapping("clickToListUser")
	@ResponseBody
	public List<Map<String, Object>> clickToListUser(String id){
		List<Map<String, Object>> list=ptPlateformOrgService.getUsersByOrgId(id);
		return list;
	}
	
	@RequestMapping("toPtOrgList")
	@ResponseBody
	public List<Map<String, Object>> toPtOrgList(){
		List<Map<String, Object>> list=ptPlateformOrgService.toPtOrgList();
		return list;
	}

}
