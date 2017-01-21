package com.krm.voteplateform.web.ptuserrole.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.plateformuser.model.PtPlateformUser;
import com.krm.voteplateform.web.ptuserrole.model.PtCommissionUserRole;
import com.krm.voteplateform.web.ptuserrole.service.PtCommissionUserRoleService;

@Controller
@RequestMapping("pt/userRole")
public class PtCommissionUserRoleController extends BaseController{
	
	@Autowired
	PtCommissionUserRoleService ptCommissionUserRoleService;
	
	@RequestMapping("toAdminList")
	public String toAdminList(){
		List<Map<String, Object>> list=ptCommissionUserRoleService.getCurrentCommissionUsers();
		if (list.size()>0) {
			return "plateform/userrolemanage/usermanage_list";
		}else {
			return "plateform/userrolemanage/usermanage_list2";
		}
		
	}
	
	@RequestMapping("listUserManager")
	@ResponseBody
	public List<Map<String, Object>> listUserManager(){
		List<Map<String, Object>> list=ptCommissionUserRoleService.getCurrentCommissionUsers();
		return list;
	}
	
	/**
	 * 删除当前委员会的平台管理员
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteById")
	public String deleteById(String id){
		boolean delete=ptCommissionUserRoleService.deleteById(id);
		if (delete){
			return "plateform/userrolemanage/administrator_set";
		}else{
			return "plateform/userrolemanage/usermanage_list";
		}
		//return delete?Result.successResult():Result.errorResult();
	}
	
	@RequestMapping("toSetAdministrator")
	public String toSetAdministrator(){
		return "plateform/userrolemanage/administrator_set";
	}
	
	@RequestMapping("plateformUser")
	public String plateformUser(){
		return "plateform/usermanage/plateuser_list";
	}
	
	/*
	 * 从用户表中添加委员会平台管理员
	 */
	@RequestMapping("addPtAdmin")
	public String addPtAdmin(String id,HttpServletRequest request,HttpServletResponse response){
		PtCommissionUserRole ptCommissionUserRole=new PtCommissionUserRole();
		ptCommissionUserRole.setPuid(id);
		boolean inert=ptCommissionUserRoleService.addPtAdmin(ptCommissionUserRole);
		//return "redirect:/toAdminList";
		List<Map<String, Object>> list=ptCommissionUserRoleService.getCurrentCommissionUsers();
		if (list.size()>0) {
			return "plateform/userrolemanage/usermanage_list";
		}else {
			return "plateform/userrolemanage/usermanage_list2";
		}
	}

}
