package com.krm.voteplateform.web.basProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.IPUtils;
import com.krm.voteplateform.web.basProject.service.BasProjectService;
import com.krm.voteplateform.web.commission.service.PtCommissionService;
import com.krm.voteplateform.web.constants.SessionKeys;
import com.krm.voteplateform.web.menuwinconf.service.BasProAttchService;
import com.krm.voteplateform.web.mettingmem.model.BasProComeMem;
import com.krm.voteplateform.web.mettingmem.service.BasMettingMemService;
import com.krm.voteplateform.web.mettingmem.service.BasProComeMemService;
import com.krm.voteplateform.web.ptvotematterconf.service.PtVoteMatterConfService;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * ClassName:CouncilMeetingController <br/>
 * Function: 委员端控制. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年12月12日 下午8:27:35 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Controller
@RequestMapping("ptsystems")
public class CouncilMeetingController extends BaseController{
	
	@Autowired
	private BasProjectService basProjectService;
	
	@Autowired
	private PtVoteMatterConfService ptVoteMatterConfService;
	
	@Autowired
	private BasMettingMemService basMettingMemService;
	
	@Autowired
	private BasProComeMemService basProComeMemService;
	
	 @Autowired
	private PtCommissionService ptCommissionService;
	 
	 
	@Resource
	private BasProAttchService fileUploadService;
	
	
	/**
	 * 
	 * showCommissionMeetingInfo:(委员端查看会议信息). <br/>
	 * @author lixy
	 * @param model
	 * @param request
	 * @param functionCode
	 * @return
	 * @since JDK 1.6
	 */
	@RequestMapping("councilmeeting/fun2101")
	public String showCommissionMeetingInfo(Model model,HttpServletRequest request,String functionCode) {
		logger.info("委员开始执行投票表决功能{}" );
		String mettingId = request.getParameter("mettingId");
		String projectId = request.getParameter("projectId");
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		Map<String,String> map=Maps.newHashMap();
		map.put("code", tableNamePrefix);
		map.put("conferenceId", mettingId);
		map.put("projectId", projectId);
		 //查询委员会审查意见 
		Map<String,Object> objectmap=Maps.newHashMap();
		objectmap.put("code", tableNamePrefix);
		objectmap.put("projectId", projectId);
		objectmap.put(MyBatisConstans.DYTABLE_KEY, tableNamePrefix);
		 //项目信息查询
		 Map<String, Object> projectMap=basProjectService.findProjectOne(map);
		 model.addAttribute("voteInfo", objectmap);
		 model.addAttribute("mettingId", mettingId);
		 //查询是否有已经投票
		 model.addAttribute("projectMap", projectMap);
		logger.info("委员结束执行投票表决功能");
		return "ptsystems/meeting_info";
	}
	/**
	 * 
	 * commissionVoteSuggest:委员会投票弹出窗. <br/>
	 * @author lixy
	 * @param model
	 * @param request
	 * @return
	 * @since JDK 1.6
	 */
	@RequestMapping("councilmeeting/fun2102")
	public String showCommissionVoteSuggest(Model model,HttpServletRequest request,String functionCode) {
		logger.info("委员开始执行投票表决功能{}" );
		String mettingId = request.getParameter("mettingId");
		String projectId = request.getParameter("projectId");
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		Map<String,String> map=Maps.newHashMap();
		map.put("code", tableNamePrefix);
		map.put("conferenceId", mettingId);
		map.put("projectId", projectId);
		//得到投票按钮信息
		 List<Map<String, Object>> voteMatterConfList= ptVoteMatterConfService.findBtnInfo(map);
		 //查询初始表决权 //该委员查询有无表决权限
		 Boolean flag= basMettingMemService.findCommisionVoteAuths(map);
		 //项目信息查询
		 Map<String, Object> projectMap=basProjectService.findProjectOne(map);
		 //查询委员会审查意见 
		Map<String,Object> objectmap=Maps.newHashMap();
		objectmap.put("code", tableNamePrefix);
		objectmap.put("projectId", projectId);
		objectmap.put(MyBatisConstans.DYTABLE_KEY, tableNamePrefix);
		 List<Map<String, Object>> voteSuggestion = ptCommissionService.getCommisssionByCode(objectmap);
		 //附件信息查询
		 HashMap<String, Object> hapmap=Maps.newHashMap(); 
		 hapmap.put("projectId", projectId);
		 List<Map<String, Object>> proAttchList = fileUploadService.getProAttchList(hapmap);
		 //查询是否有已经投票
		 Boolean againVote=basProComeMemService.loadByMapId(objectmap);
		 model.addAttribute("voteMatterConfList", voteMatterConfList);
		 objectmap.put("voteFlag", flag);
		 objectmap.put("againVote", againVote);
		 logger.info("标记{}",objectmap);
		 model.addAttribute("voteInfo", objectmap);
		 model.addAttribute("voteFlag", flag);
		 model.addAttribute("projectMap", projectMap);
		 model.addAttribute("voteSuggestion", voteSuggestion.get(0));
		 model.addAttribute("proAttchList", proAttchList);
		 logger.info("项目附件：{},size:{}",JSON.toJSONString(proAttchList),proAttchList.size());
		 logger.info("是否需要委员输入审查意见{}", voteSuggestion.get(0).get("suggestFlag"));
		logger.info("委员结束执行投票表决功能");
		return "ptsystems/commission_vote_suggest";
	}
	
	@RequestMapping("councilmeeting/aggress")
	@ResponseBody
	public Result commissionVoteSuggest(HttpServletRequest request,String functionCode) {
		String userid = SysUserUtils.getSessionLoginUser().getId();
		String conferenceId = request.getParameter("conferenceId");
		String projectId = request.getParameter("projectId");
		String passVal = request.getParameter("passVal");
		String passType = request.getParameter("passType");
		String suggestionContent = request.getParameter("suggestionContent");
		logger.info("委员{}开始执行投票表决功能{}----{}" ,userid,passType,passVal);
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		Map<String,String> map=Maps.newHashMap();
		map.put("code", tableNamePrefix);
		map.put("conferenceId", conferenceId);
		map.put("projectId", projectId);
		map.put("passVal", passVal);
		map.put("passType", passType);
		map.put("suggestionContent", suggestionContent);
		String clientAddress = IPUtils.getClientAddress(request);
		map.put("clientAddress", clientAddress);
		Boolean flag = basProComeMemService.saveBasProComeMem(map);
		
		if(flag){
			logger.info("委员{}结束执行投票表决功能{}----{}" ,userid,passType,passVal);
			return Result.successResult();
		}else{
			return Result.errorResult();
   	   }
		
	}
	
}

