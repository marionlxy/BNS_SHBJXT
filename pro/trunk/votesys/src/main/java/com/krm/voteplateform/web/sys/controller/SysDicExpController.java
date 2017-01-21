package com.krm.voteplateform.web.sys.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;
import com.krm.voteplateform.web.constants.SessionKeys;
import com.krm.voteplateform.web.sys.model.BasExDetDic;
import com.krm.voteplateform.web.sys.model.BasExpandGroup;
import com.krm.voteplateform.web.sys.model.BasDict;
import com.krm.voteplateform.web.sys.service.SysDicExpService;


@Controller
@RequestMapping("pt/dicExp")
public class SysDicExpController extends BaseController{
	
	@Resource
	private SysDicExpService sysDicExpService;

	
	/**
	 * 跳转编辑项目扩展明细字典页面sysDicExp_list.html
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("sysDicExp")
	public String sysDicExp(HttpServletRequest request, Model model){
		return "plateform/sysDic/sysDicExp_list";
	}
	
	/**
	 * 获取扩展明细分组表信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getDicExpList")
	@ResponseBody
	public List<Map<String, Object>> getDicExpList(HttpServletRequest request,Model model){
		
		List<Map<String, Object>> tempDicList = Lists.newArrayList();
		Map<String,Object> tempMap = Maps.newHashMap();
		tempMap.put("id", "-1000");
        tempMap.put("pId", "0");
        tempMap.put("groupName", "全部");
        tempDicList.add(tempMap);
		List<Map<String, Object>> dicList = sysDicExpService.findDicExpList();
		 for (Map<String, Object> map : dicList) {
	        map.put("pId", "-1000");
	        tempDicList.add(map);
		 }

		logger.info("结束查询获取扩展明细分组表信息 getDicExpList");
		return dicList;
	}

	/**
	 * 根据点击每树的id查询每个分组详细数据
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getDicExpDetial")
	@ResponseBody
	public List<Map<String, Object>> getDicExpDetial(HttpServletRequest request,Model model,String id){
		logger.info("开始查询编辑项目扩展明细字典 getDicExpDetial");
		List<Map<String, Object>> dicList = sysDicExpService.findDicExpDetial(id);
		logger.info("结束查询编辑项目扩展明细字典 getDicExpDetial");
		return dicList;
		
	}
	
	/**
	 * 跳转增加扩展明细分组页面
	 */
	@RequestMapping("sysExpGroup")
	public String sysExpGroup(HttpServletRequest request, Model model){
		return "plateform/sysDic/sysExpGroup_add";
	}
	
	
	/**
	 * 保存详情
	 * @param basCodeGroup
	 * @return
	 */
	@RequestMapping(value = "/saveExpGroup",method=RequestMethod.POST)
	@ResponseBody
	public Result saveExpGroup(HttpServletRequest request){
		Boolean flag = false;
		String groupName = request.getParameter("groupName");
		Integer isExist = sysDicExpService.isExist(groupName);
		Result newResult = null;
		if(isExist>0){
			 newResult =Result.errorResult();
			newResult.setMsg("该组名已经存在");
			//return newResult;
		}else{
		BasExpandGroup basExpandGroup = new BasExpandGroup();
		basExpandGroup.setGroupName(groupName);
		int state=StringUtils.isEmpty(request.getParameter("state"))?1:0;//0:是启用：1:不启用
		logger.info("取页面状态"+state+"1:代表不启用;0代表启用");
		basExpandGroup.setState(state);
		flag = sysDicExpService.saveExpGroup(basExpandGroup);
		logger.info("进入保存flag{}"+"true:成功; false:失败");
		if(flag){
			logger.info("结束保存获取代码字典信息");
			return Result.successResult();
		}
		
		}
		return newResult;
	}
	
	/**
	 * 删除项目拓展明细分组
	 * @param BasCodeGroup
	 * @return
	 */
	@RequestMapping(value = "/deleteExpGroup")
	@ResponseBody
	public Result deleteExpGroup(HttpServletRequest request){
		BasExpandGroup basExpandGroup=new BasExpandGroup();
		logger.info("开始删除项目拓展明细分组");
		String groupId = request.getParameter("id");
		basExpandGroup.setId(groupId);
		//根据组groupId删除明细字典的数据
		Boolean pixFlag = sysDicExpService.deleteByGroupId(groupId);
		if(pixFlag){
			boolean flag=sysDicExpService.deleteExpGroup(basExpandGroup);
			logger.info("进入保存flag{}"+"true:成功; false:失败");
			if(flag){
				logger.info("结束删除字典组信息");
				return Result.successResult();
			}
		}
		return Result.errorResult();
	}
	
	/**
	 * 修改分组名称
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateExpGroup")
	public String updateExpGroup(HttpServletRequest request,Model model){
		logger.info("开始修改分组名称");
		String id = request.getParameter("id");
		BasExpandGroup basExpandGroup= sysDicExpService.updateExpGroup(id);
		model.addAttribute("bp",basExpandGroup);
		
		logger.info("结束修改分组名称跳转到sysExpGroup_edit.html页面");
		return "plateform/sysDic/sysExpGroup_edit";
	}
	
	
	/**
	 * 保存修改项目拓展明细分组名称
	 * @param BasExpandGroup
	 * @return
	 */
	@RequestMapping(value = "/saveUpExpGroup",method=RequestMethod.POST)
	@ResponseBody
	public Result saveUpExpGroup(HttpServletRequest request){
		BasExpandGroup basExpandGroup=new BasExpandGroup();
		String id = request.getParameter("id");
		basExpandGroup.setId(id);
		String groupName=request.getParameter("groupName");
		basExpandGroup.setGroupName(groupName);
		int state=getParaToInteger("state",0);//0:是：1:不是
		logger.info("获取页面state状态{}--",state);
		basExpandGroup.setState(state);
		logger.info("开始修改项目拓展明细分组名称信息{}"+id+groupName );
		Boolean flag = sysDicExpService.saveUpExpGroup(basExpandGroup);
		if(flag){
			logger.info("结束保存修改项目拓展明细分组名称");
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	
	/**
	 * 查询每个拓展明细字典表页面的每一条映射名称值
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @return 
	 */
	@RequestMapping("findDicGroupFild")
	public String findDicGroupFild(HttpServletRequest request,Model model,String gropId,String expDicId ){
		logger.info("开始根据id查询每条字典数据 findDicGroupFild");
		logger.info("从session中获得codes");
		BasExDetDic basExDetDic = sysDicExpService.findDicGroupFild(gropId,expDicId);
		model.addAttribute("beg",basExDetDic);
		logger.info("结束查询每条字典数据结束返回到basExDetDicField_edit.html页面");
		return "plateform/sysDic/basExDetDicField_edit";
	}
	
	
	/**
	 * 编辑保存每个字典类型页面的每一条映射名称
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param mapCnName
	 * @param mapPrecision
	 * @param id
	 * @return
	 * 
	 */
	@RequestMapping("editDicGroupFild")
	@ResponseBody
	public Result editDicGroupFild (HttpServletRequest request,Model model,String groupId,String id,String mapCnName){
		logger.info("进入逻辑业务flag{}",groupId,mapCnName,id);
		Boolean	flag = false;
		logger.info("映射名称不能为空");
		if(StringUtils.isEmpty(mapCnName)){
			return Result.errorResult();
		}else{
			logger.info("进入逻辑业务",groupId,mapCnName,id);
			flag = sysDicExpService.editDicGroupFild(groupId,mapCnName,id);
			if(flag){
				return Result.successResult();
			}
		return Result.errorResult();
	}
}
	
	
	/**
	 * 更新保存数据是否启用
	 * @author zhangYuHai
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("saveUpStateExp")
	@ResponseBody
	public Result saveUpStateExp(HttpServletRequest request, String basdict) {
		String basdicts = request.getParameter("basdict");
		Boolean flag = sysDicExpService.saveUpStateExps(basdicts);
		logger.info("保存saveUpState" + flag);
		if(flag){
			return Result.successResult();
		}else{
			return Result.errorResult();
		}
		
		}
	
	
	
	
	
	
	
}
