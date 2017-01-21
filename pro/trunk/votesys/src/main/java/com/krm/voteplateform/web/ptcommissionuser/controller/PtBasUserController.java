package com.krm.voteplateform.web.ptcommissionuser.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.utils.EncryptUtils;
import com.krm.voteplateform.web.plateformuser.model.PtPlateformUser;
import com.krm.voteplateform.web.plateformuserpass.PtPlateformUserPass;
import com.krm.voteplateform.web.ptcommissionuser.model.PtCommissionUser;
import com.krm.voteplateform.web.ptcommissionuser.service.PtBasUserService;
import com.krm.voteplateform.web.ptuserrole.model.PtCommissionUserRole;
import com.krm.voteplateform.web.util.SysUserUtils;
@Controller
@RequestMapping("pt/PtBasUserList")
public class  PtBasUserController extends BaseController{
	
	@Autowired
	private PtBasUserService ptBasUserService;
	
	//展示列表查询语句  委员会用户管理
	@RequestMapping("showList")
	@ResponseBody
	public List<Map<String, Object>> showList() {
		logger.info("开始查询会议表的信息");
		List<Map<String, Object>> list = ptBasUserService.showList();
		logger.info("结束查询会议表的信息");
		return list;
	}
	//跳到页面 委员会用户管理
	@RequestMapping("showpage")
	public String showpage(){
		return "sysfunction/usermanage/user_manage";
	}
	/*//跳到页面  委员会机构管理
	@RequestMapping("showManage")
	public String ShowManage(){
		return "sysfunction/agencymanage/agency_manage";
	}*/
	//跳到增加页面
	/*@RequestMapping("showAddpage")
	public String showAddpage(){
		return "ptsystems/user_manage_add";
	}*/
	//退出委员会操作
	@RequestMapping("deleteById")
	@ResponseBody
	public Result deletedate(HttpServletRequest request, String puid) {
		logger.info("开始退出委员会" +puid);
		boolean delete=ptBasUserService.deleteById(puid);
		return delete?Result.successResult():Result.errorResult();
		
	}
	
	@RequestMapping("ascOrder")
	@ResponseBody
	public Result ascOrder(){
		PtCommissionUser ptCommissionUser=new PtCommissionUser();
		PtCommissionUser ptCommissionUser1=new PtCommissionUser();
		String code=SysUserUtils.getCurrentCommissionCode();
		String puid=getPara("puid");
		int orderCode=Integer.parseInt(getPara("orderCode"));
		
		String prepuid=getPara("prepuid");
		int preorderCode=Integer.parseInt(getPara("preorderCode"));
		
		ptCommissionUser.setCode(code);
		ptCommissionUser.setPuid(puid);;
		ptCommissionUser.setOrderCode(orderCode);
		
		ptCommissionUser1.setCode(code);
		ptCommissionUser1.setPuid(prepuid);;
		ptCommissionUser1.setOrderCode(preorderCode);
		
		boolean flag=ptBasUserService.ascOrder(ptCommissionUser,ptCommissionUser1);
		return flag?Result.successResult():Result.errorResult();
		
	}
	
	@RequestMapping("desOrder")
	@ResponseBody
	public Result desOrder(){
		PtCommissionUser ptCommissionUser=new PtCommissionUser();
		PtCommissionUser ptCommissionUser1=new PtCommissionUser();
		String code=SysUserUtils.getCurrentCommissionCode();
		String puid=getPara("puid");
		int orderCode=Integer.parseInt(getPara("orderCode"));
		
		String nextpuid=getPara("nextpuid");
		int nextorderCode=Integer.parseInt(getPara("nextorderCode"));
		
		ptCommissionUser.setCode(code);
		ptCommissionUser.setPuid(puid);;
		ptCommissionUser.setOrderCode(orderCode);
		
		ptCommissionUser1.setCode(code);
		ptCommissionUser1.setPuid(nextpuid);;
		ptCommissionUser1.setOrderCode(nextorderCode);
		
		boolean flag=ptBasUserService.desOrder(ptCommissionUser,ptCommissionUser1);
		return flag?Result.successResult():Result.errorResult();
	}
	
	@RequestMapping("toAddUser")
	public String toAddUser(Model model){
		List<Map<String, Object>> list=ptBasUserService.listOrgs();
		model.addAttribute("list", list);
		return "sysfunction/usermanage/user_add";
	}
	
	@RequestMapping("toEditUser")
	public String toEditUser(HttpServletRequest request,Model model){
		String puid=getPara("puid");
		String code=SysUserUtils.getCurrentCommissionCode();
		Map<String, Object> map=ptBasUserService.getUserById(puid,code);
		List<Map<String, Object>> list=ptBasUserService.getRoleList();
		model.addAttribute("map", map);
		model.addAttribute("list", list);
		return "sysfunction/usermanage/user_edit";
	}
	
	@RequestMapping("saveUserUpdate")
	@ResponseBody
	public Result saveUserUpdate(){
		PtCommissionUser ptCommissionUser=new PtCommissionUser();
		PtCommissionUserRole ptCommissionUserRole=new PtCommissionUserRole();
		PtPlateformUser ptPlateformUser=new PtPlateformUser();
		PtPlateformUserPass ptPlateformUserPass=new PtPlateformUserPass();
		
		String puid=getPara("puid");
		String code=SysUserUtils.getCurrentCommissionCode();
		String userName=getPara("userName");
		ptCommissionUser.setPuid(puid);
		ptCommissionUser.setCode(code);
		ptCommissionUser.setUserName(userName);
		
		ptPlateformUserPass.setId(puid);
		
		String loginPassword=getPara("loginPassword");
		String password=null;
		if (loginPassword==null||"".equals(loginPassword)) {
			//没有更改密码
		}else {
//			password=PasswordEncoder.encrypt(userName,loginPassword);
			password=EncryptUtils.MD5_HEX(loginPassword);
		}
		ptPlateformUser.setLoginPassword(password);
		ptPlateformUser.setUserName(userName);
		ptPlateformUser.setId(puid);
		
		ptPlateformUserPass.setLoginPassword(password);
		
		String crid=getPara("crid");
		if("".equalsIgnoreCase(crid)){
			crid=null;
		}
		String urid=getPara("urid");
		ptCommissionUserRole.setCrid(crid);
		ptCommissionUserRole.setId(urid);
		ptCommissionUserRole.setCode(code);
		//ptCommissionUserRole.setPuid(puid);
		
		boolean update=ptBasUserService.saveUserUpdate(ptCommissionUser,ptPlateformUser,ptCommissionUserRole,ptPlateformUserPass);
		return update?Result.successResult():Result.errorResult();
	}
	
	@RequestMapping("addUsers")
	@ResponseBody
	public Result addUsers(){
		//String puid=getPara("id");
		//String userName=getPara("userName");
		String code=SysUserUtils.getCurrentCommissionCode();
		String state="0";
		int orderCode=ptBasUserService.getMaxOrder()+1;
		String[] puids=getPara("ids").split(",");
		String[] userNames=getPara("userNames").split(",");
		List<PtCommissionUser> list=new ArrayList<PtCommissionUser>();
		List<PtCommissionUserRole> listUr=new ArrayList<PtCommissionUserRole>();
		boolean update=false;
		for(int i=0;i<puids.length;i++){
			Map<String, Object> map=ptBasUserService.getUserById(puids[i], code);
			Map<String, Object> mapUR=ptBasUserService.getUserRoleById(puids[i], code);
			PtCommissionUser ptCommissionUser=new PtCommissionUser();
			PtCommissionUserRole ptCommissionUserRole=new PtCommissionUserRole();
			if (map==null) {
				ptCommissionUser.setCode(code);
				ptCommissionUser.setPuid(puids[i]);
				ptCommissionUser.setOrderCode(orderCode+i);
				ptCommissionUser.setViewFlag("0");
				ptCommissionUser.setState(state);
				ptCommissionUser.setUserName(userNames[i]);
				
				list.add(ptCommissionUser);
			}else {
				ptCommissionUser.setCode(code);
				ptCommissionUser.setPuid(puids[i]);
				ptCommissionUser.setState("0");
				ptCommissionUser.setOrderCode(orderCode+i);
				update=ptBasUserService.updateState(ptCommissionUser);
			}
			if (mapUR==null) {
				ptCommissionUserRole.setCode(code);
				ptCommissionUserRole.setPuid(puids[i]);
				listUr.add(ptCommissionUserRole);
			}else {
				continue;
			}
			
		}
		boolean insert=ptBasUserService.addUsers(list,listUr);
		if ((insert==true)||(update==true)) {
			return Result.successResult();
		}else {
			return Result.errorResult();
		}
	}
	
} 