package com.krm.voteplateform.web.commission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.commission.model.PtCommission;
import com.krm.voteplateform.web.commission.service.PtCommissionService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
@RequestMapping("pt/voteplancontroller")
public class VotePlanController extends BaseController {
	@Autowired
	private PtCommissionService ptCommissionService;

	//跳到页面 委员会方案管理
		@RequestMapping("jumppange")
		public String showpage(){
			return "sysfunction/planmanage/agency_manage";
		}
	//查询委员会方案的数据
		@RequestMapping("showSelect")
		@ResponseBody
		public List<Map<String, Object>> showSelect(@RequestParam Map<String, Object> map) {
			logger.info("开始查询会议表的信息"); 
			Map<String, Object> result = ptCommissionService.selectVotePlanByCode(map);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list.add(result);
			logger.info("结束查询会议表的信息");
			return list;
		}
		//跳到页面 委员会方案管理的修改页面
		@RequestMapping("UpdateVotePlan")
		public String UpdateVotePlan(HttpServletRequest request,Model model){
			List<Map<String, Object>> list = ptCommissionService.selecePull();
			String id = request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", SysUserUtils.getCurrentCommissionCode());
			Map<String, Object> VotePlan =  ptCommissionService.selectVotePlanByCode(map);
			model.addAttribute("VotePlan", VotePlan);
			model.addAttribute("id", id);
			model.addAttribute("list", list);
			return "sysfunction/planmanage/agency_edit";
		}	
		//查询委员会方案的数据
			@RequestMapping("selectComissionMembers")
			@ResponseBody
			public List<Map<String, Object>> selectComissionMembers(HttpServletRequest request) {
				logger.info("开始查询会议表的信息");
				String code = SysUserUtils.getCurrentCommissionCode();
				Map<String, Object> memberIds = ptCommissionService.selectMemberIds(code);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("code", code);
					map.put("memberIds", memberIds.get("memberIds"));
					List<Map<String, Object>> listshowTable = ptCommissionService.selectComissionMembers(map);
					logger.info("结束查询会议表的信息");
					return listshowTable;
		}
		//查询操作里面按钮状态的方法
		@RequestMapping("SelectState")
		@ResponseBody
		public List<Map<String, Object>> SelectState(String id) {
			logger.info("开始查询会议表的信息");
			List<Map<String, Object>> Statelist = ptCommissionService.SelectState(id);
			logger.info("结束查询会议表的信息");
			return Statelist;
	}
		//保存页面
		@RequestMapping("SavevotePlanName")
		@ResponseBody
		public Result SavevotePlanName (HttpServletRequest request, String id,String votePlanName,String votePlanId){
			PtCommission ptCommission = new PtCommission();
			ptCommission.setId(id);
			ptCommission.setVotePlanName(votePlanName);
			ptCommission.setVotePlanId(votePlanId);
			boolean flag  = ptCommissionService.SavevotePlanName(ptCommission);
			logger.info("完成页面保存的修改");
				return Result.successResult();
				}
			//去测试页面
			@RequestMapping("TestPlan")
			public String TestPlan(String code ,HttpServletRequest request,Model model){
					return "sysfunction/planmanage/TestPlan";
					}
			//删除方法
			@RequestMapping("deletereid")
			@ResponseBody
		public void deletereid(Model model,HttpServletRequest request){
				String code = SysUserUtils.getCurrentCommissionCode();
				Map<String, Object> memberIds = ptCommissionService.selectMemberIds(code);
				String puid = request.getParameter("puid");
				String memberIds1=(String) memberIds.get("memberIds");
				//String i =memberIds1.substring(0,1);
				//String string = memberIds1.substring(0, memberIds1.indexOf(","));
				//String rString =  memberIds1.substring(0,1);
				//去掉一个字符以及前面的‘,’操作 
				String aString = ",";
				String iString = null;
				if (!memberIds1.contains(aString)){
					iString = memberIds1.replace(puid,"");
				}
				else{
					if (memberIds1.substring(0, memberIds1.indexOf(",")).equals(puid)) {
						iString = memberIds1.replace(puid+',',"");
					}
					else{
						iString = memberIds1.replace(','+puid,"");
					}
				}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			map.put("iString", iString);
			ptCommissionService.RemoveMemberIds(map);
		}		
		//添加方法
		@RequestMapping("addreid")
		@ResponseBody
			public void addreid(HttpServletRequest request,Model model){
				PtCommission  ptCommission = new PtCommission();
				String code = SysUserUtils.getCurrentCommissionCode();
				Map<String, Object> memberIds = ptCommissionService.selectMemberIds(code);
				String memberIds1=(String) memberIds.get("memberIds");
				String newmemberIds=null;
				String puid = request.getParameter("puid");
				//判断是否为空  以及添加操作
				if(memberIds1==null ||  memberIds1=="") {
					newmemberIds=puid;
				}
				else{
					newmemberIds=memberIds1+","+puid;
				}
				ptCommission.setMemberIds(newmemberIds);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", code);
				map.put("newmemberIds", newmemberIds);
				ptCommissionService.UpdatememberIds(map);
			}
		
}
