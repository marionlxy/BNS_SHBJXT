package com.krm.voteplateform.web.ptcommissionorg.controller;

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
import com.krm.voteplateform.web.plateformorg.model.PtPlateformOrg;
import com.krm.voteplateform.web.ptcommissionorg.model.PtCommissionOrg;
import com.krm.voteplateform.web.ptcommissionorg.service.PtCommissionOrgService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
@RequestMapping("pt/PtCommissionOrg")
public class PtCommissionOrgController extends BaseController{
	
	@Autowired
	private PtCommissionOrgService ptCommissionOrgService;
	
	@RequestMapping("toOrgList")
	@ResponseBody
	public List<Map<String, Object>> toOrgList(){
		List<Map<String, Object>> list=ptCommissionOrgService.toOrgList();
		return list;
	}
	
	@RequestMapping("toAddOrg")
	public String toAddOrg(){
		return "sysfunction/orgmanage/organ_add";
	}
	
	@RequestMapping("addOrg")
	@ResponseBody
	public Result addOrg(HttpServletRequest request){
		PtCommissionOrg ptCommissionOrg=new PtCommissionOrg();
		String id=getPara("id");
		String code=SysUserUtils.getCurrentCommissionCode();
		int orgorder=0;
		int count=ptCommissionOrgService.toOrgList().size();
		if (count>0) {//如果表里存在数据
			orgorder=ptCommissionOrgService.getMaxOrgOrder()+1;
		}else {
			orgorder=1;
		}
		PtPlateformOrg ptPlateformOrg=ptCommissionOrgService.getPtPlateformOrgById(id);
		
		ptCommissionOrg.setPorgid(id);
		ptCommissionOrg.setCode(code);
		ptCommissionOrg.setOrgname(ptPlateformOrg.getOrgname());
		ptCommissionOrg.setOrgorder(orgorder);
		ptCommissionOrg.setOrgtype(ptPlateformOrg.getOrgtype());
		ptCommissionOrg.setState("01");//默认为“01”，代表通用。
		
		boolean insert=ptCommissionOrgService.saveOrg(ptCommissionOrg);
		return insert?Result.successResult():Result.errorResult();
		
		
	}
	
	/**
	 * 批量从平台机构里添加机构到委员机构表里
	 * @param request
	 * @return
	 */
	@RequestMapping("addOrgs")
	@ResponseBody
	public Result addOrgs(HttpServletRequest request){
		String ids=getPara("ids");
		String[] porgids=ids.split(",");
		int orgorder=0;
		int count=ptCommissionOrgService.toOrgList().size();
		if (count>0) {//如果表里存在数据
			orgorder=ptCommissionOrgService.getMaxOrgOrder()+1;
		}else {
			orgorder=1;
		}
		String code=SysUserUtils.getCurrentCommissionCode();
		List<PtCommissionOrg> list=new ArrayList<PtCommissionOrg>();
		for(int i=0;i<porgids.length;i++){
			PtCommissionOrg ptCommissionOrg=new PtCommissionOrg();
			PtPlateformOrg ptPlateformOrg=ptCommissionOrgService.getPtPlateformOrgById(porgids[i]);
			ptCommissionOrg.setPorgid(porgids[i]);
			ptCommissionOrg.setCode(code);
			ptCommissionOrg.setOrgname(ptPlateformOrg.getOrgname());
			ptCommissionOrg.setOrgorder(orgorder+i);
			ptCommissionOrg.setOrgtype(ptPlateformOrg.getOrgtype());
			ptCommissionOrg.setState("01");//默认为“01”，代表通用。
			list.add(ptCommissionOrg);
		}	
		boolean insert=ptCommissionOrgService.saveOrgs(list);
		return insert?Result.successResult():Result.errorResult();
		
		
	}
	
	@RequestMapping("toEditOrg")
	public String toEditOrg(Model model){
		String code=SysUserUtils.getCurrentCommissionCode();
		String porgid=getPara("porgid");
		Map<String, Object> map=ptCommissionOrgService.getOrg(code,porgid);
		model.addAttribute("map", map);
		return "sysfunction/orgmanage/organ_edit";
	}
	
	@RequestMapping("saveOrgUpdate")
	@ResponseBody
	public Result saveOrgUpdate(){
		PtCommissionOrg ptCommissionOrg=new PtCommissionOrg();
		String code=SysUserUtils.getCurrentCommissionCode();
		String porgid=getPara("porgid");
		String orgname=getPara("orgname");
		String state=getPara("state");
		if ("0".equalsIgnoreCase(state)) {
			state=null;
		}
		String remarks=getPara("remarks");
		
		ptCommissionOrg.setCode(code);
		ptCommissionOrg.setPorgid(porgid);
		ptCommissionOrg.setOrgname(orgname);
		ptCommissionOrg.setState(state);
		ptCommissionOrg.setOrgremark(remarks);
		
		boolean update=ptCommissionOrgService.saveOrgUpdate(ptCommissionOrg);
		return update?Result.successResult():Result.errorResult();
	}
	
	@RequestMapping("deleteOrg")
	@ResponseBody
	public Result deleteOrg(){
		PtCommissionOrg ptCommissionOrg=new PtCommissionOrg();
		String code=SysUserUtils.getCurrentCommissionCode();
		String porgid=getPara("porgid");
		
		ptCommissionOrg.setCode(code);
		ptCommissionOrg.setPorgid(porgid);
		
		boolean delete=ptCommissionOrgService.deleteOrg(ptCommissionOrg);
		return delete?Result.successResult():Result.errorResult();
		
	}
	
	@RequestMapping("ascOrder")
	@ResponseBody
	public Result ascOrder(){
		PtCommissionOrg ptCommissionOrg=new PtCommissionOrg();
		PtCommissionOrg ptCommissionOrg1=new PtCommissionOrg();
		String code=SysUserUtils.getCurrentCommissionCode();
		String porgid=getPara("porgid");
		int orgorder=Integer.parseInt(getPara("orgorder"));
		
		String preporgid=getPara("preporgid");
		int preorgorder=Integer.parseInt(getPara("preorgorder"));
		
		ptCommissionOrg.setCode(code);
		ptCommissionOrg.setPorgid(porgid);
		ptCommissionOrg.setOrgorder(orgorder);
		
		ptCommissionOrg1.setCode(code);
		ptCommissionOrg1.setPorgid(preporgid);
		ptCommissionOrg1.setOrgorder(preorgorder);
		
		boolean flag=ptCommissionOrgService.ascOrder(ptCommissionOrg,ptCommissionOrg1);
		return flag?Result.successResult():Result.errorResult();
		
	}
	
	@RequestMapping("desOrder")
	@ResponseBody
	public Result desOrder(){
		PtCommissionOrg ptCommissionOrg=new PtCommissionOrg();
		PtCommissionOrg ptCommissionOrg1=new PtCommissionOrg();
		String code=SysUserUtils.getCurrentCommissionCode();
		String porgid=getPara("porgid");
		int orgorder=Integer.parseInt(getPara("orgorder"));
		
		String nextporgid=getPara("nextporgid");
		int nextorgorder=Integer.parseInt(getPara("nextorgorder"));
		
		ptCommissionOrg.setCode(code);
		ptCommissionOrg.setPorgid(porgid);
		ptCommissionOrg.setOrgorder(orgorder);
		
		ptCommissionOrg1.setCode(code);
		ptCommissionOrg1.setPorgid(nextporgid);
		ptCommissionOrg1.setOrgorder(nextorgorder);
		
		boolean flag=ptCommissionOrgService.desOrder(ptCommissionOrg,ptCommissionOrg1);
		return flag?Result.successResult():Result.errorResult();
	}

}
