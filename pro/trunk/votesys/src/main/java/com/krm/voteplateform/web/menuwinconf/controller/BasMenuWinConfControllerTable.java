package com.krm.voteplateform.web.menuwinconf.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.menuwinconf.model.BasMenuWinConf;
import com.krm.voteplateform.web.menuwinconf.service.BasMenuWinConfTableService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
@RequestMapping("pt/menuwinconftable")
public class BasMenuWinConfControllerTable extends BaseController {
	@Autowired
	private BasMenuWinConfTableService basMenuWinConfTableService;
	//列表后面的修改功能
			@RequestMapping("selectCommtextsave")
			public String selectCommtextsave(String functionCode , HttpServletRequest request,Model model,String id){
				logger.info("开始根据id数据 selectDicField");
				BasMenuWinConf basMenuWinConf = basMenuWinConfTableService.selectCommtext(functionCode,id);
				model.addAttribute("basMenuWinConf",basMenuWinConf);
				logger.info("结束返回到页面");
				return "plateform/menuwinconftable/WinConf_update";
			}
	//加载到主页面
	@RequestMapping("lodefunction")
			public String sysDic_function(Model model, HttpServletRequest request,String functionCode) {
					model.addAttribute("functionCode", functionCode);
					return "plateform/menuwinconftable/MenuWinConfTable";
	}
	//页面上显示数据库里面的数据
			@RequestMapping(value="showList")
			@ResponseBody
			public List<Map<String, Object>> showlist(HttpServletRequest request,String functionCode) {
					logger.info("开始展示操作功能的表");
					//SysUserUtils.getSessionLoginUser();
					List<Map<String, Object>> listAll = basMenuWinConfTableService.selectAll(functionCode);
					logger.info("結束展示操作功能表");
					return listAll;
		}
	/*
	 * 展示操作功能的新增功能页面
	 * */
			@RequestMapping(value = "showAddfunction")
			public String showAddfunction(Model model,HttpServletRequest request,String functionCode) {
					List<Map<String, Object>> list1 = basMenuWinConfTableService.selectpull(functionCode);
					model.addAttribute("list1",list1);
					model.addAttribute("functionCode", functionCode);
					return "plateform/menuwinconftable/WinConf_add";
	}
	/*
	 * 在操作功能里面进行新增保存
	 * */
	@RequestMapping("basMenuWinConf")
	@ResponseBody
	public Result basMenuWinConf(HttpServletRequest request,String functionCode) {
		logger.info("开始增加BasFunConf操作功能表");
		BasMenuWinConf basMenuWinConf = new BasMenuWinConf();
		//增加的项
				String id = UUIDGenerator.getUUID();
					basMenuWinConf.setId("id");
				String Name = request.getParameter("name");
					basMenuWinConf.setName(Name);
				String bindEventOrUrl = request.getParameter("bindEventOrUrl");
					basMenuWinConf.setBindEventOrUrl(bindEventOrUrl);
					basMenuWinConf.setCreateBy("voteadmin");
				//下拉框
				String groupid = request.getParameter("groupname");
					basMenuWinConf.setGroupid(groupid);
				String useFlag=getPara("state", "1");
					basMenuWinConf.setUseFlag(useFlag);
					basMenuWinConf.setDelFlag("0");
					basMenuWinConf.setType("3");
					basMenuWinConf.setUpdateBy("");
					basMenuWinConf.setFunctionCode(functionCode);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("functionCode", functionCode);
			params.put("groupId", groupid);
			//按钮的序号和分组的序号一样
			String orderCode = basMenuWinConfTableService.getBtnOrder(params).get("orderCode").toString();
			basMenuWinConf.setOrderCode(orderCode);
			boolean issave =basMenuWinConfTableService.savawinconfTable(basMenuWinConf,groupid);
			logger.info("增加结束操作功能表结束");
			if (issave) {
				logger.info("保存完毕");
				return Result.successResult();
			}else {
				logger.info("保存失败");
				return Result.errorResult();
			}
	}
		//将更改页面进项保存
		@RequestMapping("lastsave")
		@ResponseBody
		public Result lastsave (HttpServletRequest request, String id,String name){
			logger.info("开始保存功能操作的页面");
			BasMenuWinConf basMenuWinConf = new BasMenuWinConf();	
			basMenuWinConf.setId(id);
			basMenuWinConf.setName(name);
			if(StringUtils.isAnyEmpty(name)){
				logger.info("修改的数据不能为空");
				return Result.errorResult();
			}else{
			boolean flag = basMenuWinConfTableService.lastsave(basMenuWinConf);
				logger.info("完成页面保存的修改");
				return Result.successResult();
			}
	}
		//数据是否启用的判断
		@RequestMapping("saveUpdate")
		@ResponseBody
		public Result saveUpdate(HttpServletRequest request, String basdict) {
			String basdicts = request.getParameter("basdict");
			Boolean flag = basMenuWinConfTableService.saveUpdate(basdicts);
			if(flag){
				return Result.successResult();
			}else{
				return Result.errorResult();
			}
			}
}
