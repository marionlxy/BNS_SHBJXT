package com.krm.voteplateform.web.sysrole.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.stat.TableStat.Mode;
import com.alibaba.fastjson.JSON;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.PtCommissionFunction.model.PtCommissionFunction;
import com.krm.voteplateform.web.authorityrole.model.PtAuthorityRole;
import com.krm.voteplateform.web.commission.model.PtCommissionRole;
import com.krm.voteplateform.web.menwingrouconf.model.BasMenWinGrouConf;
import com.krm.voteplateform.web.sysrole.service.SysRoleService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
@RequestMapping("pt/role")
public class SysRoleController extends BaseController{
	
	@Autowired
	private SysRoleService sysRoleService;
	
	
	/**
	 * 委员角色列表
	 * @author hytms
	 * @return
	 */
	@RequestMapping("listRoles")
	@ResponseBody
	public List<Map<String, Object>> listRoles(){
		List<Map<String, Object>> list=sysRoleService.listRoles();
		return list;
		
	}
	
	@RequestMapping("toAddRole")
	public String toAddRole(Model model){
		return "sysfunction/rolemanage/role_add";
	}
	
	@RequestMapping("listFunctionByRole")
	@ResponseBody
	public List<Map<String, Object>> listFunctionsByRole(String roleCategory){
		List<Map<String, Object>> list=sysRoleService.listFunctionsByRole(roleCategory);
		return list;
	}
	
	@RequestMapping("listAuthorityByRole")
	@ResponseBody
	public List<Map<String, Object>> listAuthorityByRole(){
		List<Map<String, Object>> list=sysRoleService.listAuthorityByRole();
		return list;
	}
	 
	@RequestMapping("saveRole")
	@ResponseBody
	public Result saveRole(@ModelAttribute PtCommissionRole ptCommissionRole,@RequestParam Map<String, Object> params,HttpServletRequest request){
		String roleCategory=params.get("roleCategory").toString();
		
		ptCommissionRole.setCode(SysUserUtils.getCurrentCommissionCode());
		//选中的菜单id
		String resIds = "";
		if(roleCategory.endsWith("01")){
			resIds = params.get("secretaryTree").toString();
		}
		if(roleCategory.endsWith("02")){
			resIds = params.get("committeeTree").toString();
		}
		
		
		List<PtAuthorityRole> list2=new ArrayList<PtAuthorityRole>();
		String[] authNameArrs = request.getParameterValues("authName");
		if (authNameArrs!=null) {
			for(String authName : authNameArrs){
				PtAuthorityRole ptAuthorityRole=new PtAuthorityRole();
				ptAuthorityRole.setCode(SysUserUtils.getCurrentCommissionCode());
				ptAuthorityRole.setAuthId(authName);
				list2.add(ptAuthorityRole);
				
			}
		}
		
		
		Boolean flag=sysRoleService.saveRole(ptCommissionRole,resIds,list2);
//		Boolean flag = false;
		return flag?Result.successResult():Result.errorResult();
		
	}
	
	@RequestMapping("toEditRole")
	public String toEditRole(String id,Model model){
		Map<String, Object> map=sysRoleService.getRoleById(id);
		String roleCategory=(String) map.get("roleCategory");
		//List<Map<String, Object>> list1=listFunctionsByRole(roleCategory);//列出所有的功能
		//List<Map<String, Object>> list2=listAuthorityByRole();//列出权限
		String crid=(String) map.get("crid");
		List<Long> resIds=sysRoleService.getRoleFuntionsByRoleId(crid);
		List<Map<String, Object>> list2=sysRoleService.getRoleAuthoritysByRoleId(crid);
		model.addAttribute("map", map);//委员角色表
		model.addAttribute("resIds", JSON.toJSONString(resIds));
		model.addAttribute("list2", list2);
		return "sysfunction/rolemanage/role_edit";
	}
	
	@RequestMapping("saveRoleUpdate")
	@ResponseBody
	public Result saveRoleUpdate(HttpServletRequest request){
		String crid=getPara("crid");
		String rolename=getPara("rolename");
		
		PtCommissionRole ptCommissionRole=new PtCommissionRole();
		ptCommissionRole.setCrid(crid);
		ptCommissionRole.setRolename(rolename);
		
		List<PtCommissionFunction> list=new ArrayList<PtCommissionFunction>();
		String[] useFlagArrs = request.getParameterValues("useFlag");
		if (useFlagArrs!=null) {
			for(String useFlag : useFlagArrs){
				PtCommissionFunction ptCommissionFunction=new PtCommissionFunction();
				ptCommissionFunction.setCode(SysUserUtils.getCurrentCommissionCode());
				ptCommissionFunction.setResid(useFlag);
				list.add(ptCommissionFunction);
			}
		}
		
		
		List<PtAuthorityRole> list2=new ArrayList<PtAuthorityRole>();
		String[] authNameArrs = request.getParameterValues("authName");
		if (authNameArrs!=null) {
			for(String authName : authNameArrs){
				PtAuthorityRole ptAuthorityRole=new PtAuthorityRole();
				ptAuthorityRole.setCode(SysUserUtils.getCurrentCommissionCode());
				ptAuthorityRole.setAuthId(authName);
				list2.add(ptAuthorityRole);
				
			}
		}
		
		boolean flag=sysRoleService.saveRoleUpdate(ptCommissionRole,list,list2);
		return flag?Result.successResult():Result.errorResult();
	}
	
	@RequestMapping("deleteRoleById")
	@ResponseBody
	public Result deleteRoleById(HttpServletRequest request){
		String crid=getPara("crid");
		boolean flag=sysRoleService.deleteRoleById(crid);
		return flag?Result.successResult():Result.errorResult();
	}
	
}
