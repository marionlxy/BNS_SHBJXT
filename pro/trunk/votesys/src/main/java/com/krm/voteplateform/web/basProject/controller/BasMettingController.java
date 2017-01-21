package com.krm.voteplateform.web.basProject.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.excel.ExcelUtils;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.common.utils.IPUtils;
import com.krm.voteplateform.common.utils.VoteFormulaUtils;
import com.krm.voteplateform.web.basProject.model.BasMetting;
import com.krm.voteplateform.web.basProject.model.BasProject;
import com.krm.voteplateform.web.basProject.service.BasMettingService;
import com.krm.voteplateform.web.basProject.service.BasProjectService;
import com.krm.voteplateform.web.commission.service.PtCommissionService;
import com.krm.voteplateform.web.constants.MessageKeyConstants;
import com.krm.voteplateform.web.constants.SysContants;
import com.krm.voteplateform.web.menuwinconf.service.BasMessageService;
import com.krm.voteplateform.web.mettingmem.service.BasMettingMemService;
import com.krm.voteplateform.web.mettingmem.service.BasProComeMemService;
import com.krm.voteplateform.web.ptcommissionorg.service.PtCommissionOrgService;
import com.krm.voteplateform.web.ptcommissionuser.service.PtBasUserService;
import com.krm.voteplateform.web.ptvotematterconf.service.PtVoteMatterConfService;
import com.krm.voteplateform.web.ptvoterestmpl.model.PtVoteResTmpl;
import com.krm.voteplateform.web.ptvoterestmpl.service.PtVoteResTmplService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
@RequestMapping("ptsystems")
public class BasMettingController extends BaseController {

	// 会议表
	@Resource
	private BasMettingService basMettingService;

	// 会议项目
	@Resource
	private BasProjectService basProjectService;

	@Resource
	private PtCommissionService ptCommissionService;

	@Resource
	private PtVoteMatterConfService ptVoteMatterConfService;

	
	//消息提示表
	@Resource
	private BasMessageService  basMessageService;

	//机构信息
	@Autowired
	private PtCommissionOrgService ptCommissionOrgService;

	@Resource
	private PtVoteResTmplService ptVoteResTmplService;
	
	
	//委员会用户信息
	@Resource
	private  PtBasUserService ptBasUserService;
	
	//到会人员信息
	@Resource
	private BasMettingMemService basMettingMemService;
	
	@Resource
	private BasProComeMemService basProComeMemService;
	
	/**
	 * 
	 * 准备会议:准备会议中项目列表数据查询
	 * @author zhangYuHai
	 * @param model
	 * @return
	 */
	@RequestMapping("menu4List")
	@ResponseBody
	public List<Map<String, Object>> findPrepaMetting(Model model, HttpServletRequest request, String mettingId) {
		logger.info("开始查询出预备会议列表信息");
		List<Map<String, Object>> findPrepaMettingList = basProjectService.findProjectByIdList(mettingId);// 预备会议与当前会议只能存在一条根据id不同查询
		logger.info("结束查询出预备会议列表信息" + JSON.toJSONString(findPrepaMettingList));
		return findPrepaMettingList;
	}

	/**
	 * 当前会议:当前会议中项目列表数据查询
	 * @author zhangYuHai
	 * @param model
	 * @return
	 */
	@RequestMapping("menu5List")
	@ResponseBody
	public List<Map<String, Object>> findSecreCurrMetting(Model model, HttpServletRequest request) {
		String mettingId = request.getParameter("mettingId");
		logger.info("开始查当前会议信息{}" + mettingId);
		List<Map<String, Object>> findSecreCurrMettingList = basProjectService.findProjectByIdList(mettingId);
		logger.info("结束查当前会议列表信息" + JSON.toJSONString(findSecreCurrMettingList));
		return findSecreCurrMettingList;
	}

	/**
	 * 
	 * 秘书端 完成会议：查询出完成会议信息
	 * @author zhangYuHai
	 * @param model
	 * @return
	 */
	@RequestMapping("menu6List")
	@ResponseBody
	public List<Map<String, Object>> findcompletMetting(Model model, HttpServletRequest request,
			BasMetting basMetting) {
		logger.info("开始查当完成会议信息");
		List<Map<String, Object>> completMettingList = basMettingService.findCompletMetting(basMetting);
		logger.info("当完成会议信息中,{}" + JSON.toJSONString(completMettingList));
		String mettingId = request.getParameter("mettingId");
		if (StringUtils.isEmpty(mettingId) && CollectionUtils.isNotEmpty(completMettingList)) {
			mettingId = completMettingList.get(0).get("id").toString();
		}
		
		// byLixy
		// 将comProByMetting变成completMettingList的一个子集,形如[{"id":1,"child":[{"conferenceId":1},{"conferenceId":1}]},{"id":2}]
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		if(StringUtils.isNotEmpty(mettingId)){
			List<Map<String, Object>> comProByMetting = basProjectService.findProjectByIdList(mettingId);
			for (Map<String, Object> map : completMettingList) {
				// 判断conferenceId是否等于id，可以判断出comProByMetting是子集completMettingList
				String newId = map.get("id").toString();
				if (comProByMetting.size() > 0 && newId.equals(comProByMetting.get(0).get("conferenceId").toString())) {
					// 增加子集
					map.put("child", comProByMetting);
				}
				resultList.add(map);
			}
		}
		
		// List<Map<String, Object>> aa = Collections3.union(completMettingList, comProByMetting);
		logger.info("查询完成会议结束:{}", JSON.toJSONString(resultList));
		return resultList;

	}

	/**
	 * 
	 * 准备会议:将项目退出会议功能
	 * @author zhangYuHai
	 * @param model
	 * @return
	 */
	@RequestMapping("menu4/fun1308")
	@ResponseBody
	public Result quitMetting(Model model, HttpServletRequest request) {
		String projectId = request.getParameter("projectId");
		logger.info("开始执行退出会议 flag{}" + projectId);
		Boolean flag = basProjectService.updateQuitMetting(projectId);
		if (flag) {
			return Result.successResult();
		} else {
			return Result.errorResult();
		}
	}

	/**
	 * 准备会议:将会议指定为当前会议功能 
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param id
	 * @param functionCode
	 * @return
	 */
	@RequestMapping("menu4/fun1305")
	@ResponseBody
	public String toCurrMetting(HttpServletRequest request, Model model) {
		BasMetting basMetting = new BasMetting();
		String mettingId = request.getParameter("mettingId");
		basMetting.setId(mettingId);
		Boolean flag = basMettingService.updateToCurrent(basMetting);
		if (flag) {
			return "ptsystems/toIndex/menu4";// 这里还是用menu4
		}
		return "error/error";
	}

	/**
	 * 跳转委员确认页面
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("menu5/fun1401")
	public String toConfirmList(@RequestParam Map<String, Object> params, Model model) {
		model.addAttribute("functionCode", params.get("functionCode"));
		model.addAttribute("mettingId", params.get("mettingId"));
		return "ptsystems/fun1401";
	}

	/**
	 * 委员确认列表
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("menu5/fun1401/getConfirmList")
	@ResponseBody
	public List<Map<String, Object>> getConfirmList(@RequestParam Map<String, Object> params) {
		List<Map<String, Object>> confirmList = basMettingService.getConfirmList(params);
		return confirmList;
	}

	/**
	 * 获取未到部门列表
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("menu5/fun1401/getAbsentDeptList")
	@ResponseBody
	public List<Map<String, Object>> getAbsentDeptList(@RequestParam Map<String, Object> params) {
		params.clear();
		return basMettingService.getAbsentDeptList(params);
	}

	/**
	 * 赋予委员投票权利
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("menu5/fun1401/empower")
	@ResponseBody
	public Result empower(@RequestParam Map<String, Object> params) {
		boolean flag = basMettingService.empower(params);
		if (flag) {
			return Result.successResult();
		}
		return Result.errorResult();
	}

	/**
	 * 删除与会委员
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("menu5/fun1401/deleteMem")
	@ResponseBody
	public int deleteCommitteeMember(@RequestParam Map<String, Object> params) {
		return basMettingService.deleteCommitteeMember(params);
	}
	
	
	@RequestMapping("menu5/fun1403")
	public String toVoteResult(@RequestParam Map<String, Object> params,Model model) {
		String projectId = params.get("projectId").toString();
		model.addAttribute("projectId", projectId);
		model.addAttribute("functionCode", params.get("functionCode"));
		model.addAttribute("meetingId", params.get("meetingId"));
		model.addAttribute("voteResTmpl", ptVoteResTmplService.findByCode(params));
		model.addAttribute("project", basProjectService.selectProjectById(projectId));
		params.put("enable", "0");
		params.put("code", SysUserUtils.getCurrentCommissionCode());
		model.addAttribute("voteMatterList", ptVoteMatterConfService.selectAll(params));
		//	总人数
		model.addAttribute("total", ptCommissionService.getOneCommissionByCode(params).getTotalNum());
		//	实到人数
		model.addAttribute("attend", basMettingService.getConfirmList(params).size());
		// 有表决权人数
		params.put("memAuthorityFlagId", "10100100");
		model.addAttribute("haveVotes", basMettingService.getConfirmList(params).size());
		List<Map<String, Object>> absentList = basMettingService.getAbsentDeptList(params);
		StringBuffer absentSb = new StringBuffer();
		for (int i = 0; i < absentList.size(); i++) {
			if (i != absentList.size() - 1) {
				absentSb.append(absentList.get(i).get("name").toString().trim() + "、");
			} else {
				absentSb.append(absentList.get(i).get("name").toString().trim());
			}
		}
		model.addAttribute("absentDept", absentSb.toString());
		// 查询已经投票的委员
		List<Map<String, Object>> votedList = basMettingService.selectVoted(params);
		StringBuffer votedSb = new StringBuffer();
		for (int i = 0; i < votedList.size(); i++) {
			if (i != votedList.size() - 1) {
				votedSb.append(votedList.get(i).get("userName") + "、");
			} else {
				votedSb.append(votedList.get(i).get("userName"));
			}
		}
		model.addAttribute("voted", votedSb.toString());
		model.addAttribute("votedDeatails", basMettingService.selectVotedDetails(params));
		return "ptsystems/fun1403";
	}

	/**
	 * 生成文件
	 * @param params
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("menu5/fun1403/generateFile")
	@ResponseBody
	public Map<String,Object> generateFile(@RequestParam Map<String, Object> params, ModelMap modelMap) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//显示项目名称和表决结果
		BasProject basProject = basProjectService.selectProjectById(params.get("projectId").toString());
		resultMap.put("project", basProject);
		//	总人数
		resultMap.put("total", ptCommissionService.getOneCommissionByCode(params).getTotalNum());
		//	实到人数
		resultMap.put("attend", basMettingService.getConfirmList(params).size());
		// 有表决权人数
		params.put("memAuthorityFlagId", "10100100");
		resultMap.put("haveVotes", basMettingService.getConfirmList(params).size());
		List<Map<String, Object>> absentList = basMettingService.getAbsentDeptList(params);
		//未到部门
		StringBuffer absentSb = new StringBuffer();
		for (int i = 0; i < absentList.size(); i++) {
			if (i != absentList.size() - 1) {
				absentSb.append(absentList.get(i).get("name").toString().trim() + "、");
			} else {
				absentSb.append(absentList.get(i).get("name").toString().trim());
			}
		}
		resultMap.put("absentDept", absentSb.toString());
		//投票情况
		params.put("enable", "0");
		params.put("code", SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> voteResultList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> ptVoteMatterList =  ptVoteMatterConfService.selectAll(params);
		List<Map<String, Object>> resultDataList =  basMettingService.countVote(params);
		Map<String, Object> temp;
		for (int i = 0; i < ptVoteMatterList.size(); i++) {
			temp = new HashMap<String, Object>();
			Map<String, Object> map1 = ptVoteMatterList.get(i);
			temp.put("text", map1.get("committeeText").toString()+ "：");
			for (Map<String, Object> map2 : resultDataList) {
				if(map1.get("val").toString().equals(map2.get("voteResult").toString())){
					temp.put("value", Integer.parseInt(map2.get("count").toString()));
				}else{
					temp.put("value",  0);
				}
			}
			voteResultList.add(temp);
		}
		resultMap.put("resultData",voteResultList);
		// 查询已经投票的委员
		List<Map<String, Object>> votedList = basMettingService.selectVoted(params);
		StringBuffer votedSb = new StringBuffer();
		for (int i = 0; i < votedList.size(); i++) {
			if (i != votedList.size() - 1) {
				votedSb.append(votedList.get(i).get("userName") + "、");
			} else {
				votedSb.append(votedList.get(i).get("userName"));
			}
		}
		//投票详情
		resultMap.put("voted", votedSb.toString());
		List<Map<String, Object>> votedDeatails = basMettingService.selectVotedDetails(params);
		resultMap.put("votedDeatails", votedDeatails);
		
		PtVoteResTmpl ptVoteResTmpl =  ptVoteResTmplService.findByCode(params);
		
		String name = basProject.getProjectTitle();
		String regEx="[/*:：?<>|\"]";
        Pattern p = Pattern.compile(regEx);  
        Matcher m = p.matcher(name);  
		name = m.replaceAll("").trim();
		File file = new File(SysContants.WEB_ROOT_PATH + name + "表决结果.xls");
		OutputStream os;
		try {
			os = new FileOutputStream(file);
			TemplateExportParams exportParams = new TemplateExportParams();
			exportParams.setHeadingRows(2);
			exportParams.setHeadingStartRow(2);
			
			exportParams.setTemplateUrl("WEB-INF" + File.separator + "views" + SysContants.TEMPLTE_PATH + "voteResultTemplate.xls");
			Workbook wb = ExcelExportUtil.exportExcel(exportParams, resultMap);
			Sheet sheet = wb.getSheetAt(0);
			//表决基本信息处理
			//合并第一列
			int deptRowCount = ptVoteResTmpl.getDeptShowFlag().equals("0") ? 2 : 0;//未到部门所占的行数
			int basInfoLastRow = 2 + 5 + voteResultList.size() + 3+ deptRowCount; //数字信息代表：【2：表头和项目名称，5：表决结果和到会情况，voteResultList.size()：表决情况，3：投票情况，deptRowCount：未到部门】
			int deptEndRow = 2 + 5 + voteResultList.size() + 3+ 2; //数字信息代表：【2：表头和项目名称，5：表决结果和到会情况，voteResultList.size()：表决情况，3：投票情况，2：未到部门】
			sheet.addMergedRegion(new CellRangeAddress(2, basInfoLastRow, 0, 0));//在sheet里增加合并单元格  
			if(!ptVoteResTmpl.getDeptShowFlag().equals("0")){
				//删除这两行，再创建两行(高度为0)，并且取消合并单元格
				sheet.removeRow(sheet.getRow(deptEndRow -1));		sheet.createRow(deptEndRow -1).setHeight((short) 0);
				ExcelUtils.removeMergedRegion(sheet,deptEndRow -1,1);
				sheet.removeRow(sheet.getRow(deptEndRow));			sheet.createRow(deptEndRow).setHeight((short) 0); 	
				ExcelUtils.removeMergedRegion(sheet,deptEndRow,1);
				ExcelUtils.removeMergedRegion(sheet,deptEndRow + 1,1);
				//将原部门信息下的一个空行与刚建立的两行合并
				sheet.addMergedRegion(new CellRangeAddress(deptEndRow -1, deptEndRow + 1, 0, 0));//在sheet里增加合并单元格  
				sheet.addMergedRegion(new CellRangeAddress(deptEndRow -1, deptEndRow + 1, 1, 4));//在sheet里增加合并单元格  
			}
			//汇总意见处理
			int suggeStartRow = sheet.getLastRowNum() - votedDeatails.size()*2;
			int suggeEndRow = sheet.getLastRowNum() - votedDeatails.size()-1;
			if(ptVoteResTmpl.getSuggShowFlag().equals("0")){
				//合并第一列
				sheet.addMergedRegion(new CellRangeAddress(suggeStartRow, suggeEndRow, 0, 0));//在sheet里增加合并单元格  
				for (int i = suggeStartRow; i <= suggeEndRow; i++) {
					//合并意见的单元格
					sheet.addMergedRegion(new CellRangeAddress(i, i, 2, 4));//在sheet里增加合并单元格  
				}
			}else{
				for (int i = suggeStartRow; i <= suggeEndRow; i++) {
					//不显示则删除这几行
					sheet.removeRow(sheet.getRow(i));
				}
				//移动下面的单元格前先处理合并单元格（汇总意见不显示时，需要提前处理合并单元格）
				sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum() - votedDeatails.size(), sheet.getLastRowNum(), 0, 0));//在sheet里增加合并单元格 
				//处理签名是否显示
				if(!ptVoteResTmpl.getSignShowFlag().equals("0")){
					for (int i = sheet.getLastRowNum() - votedDeatails.size(); i <= sheet.getLastRowNum(); i++) {
						Row row = sheet.getRow(i);
						row.getCell(4).setCellValue("");
						sheet.addMergedRegion(new CellRangeAddress(i, i, 3, 4));//在sheet里增加合并单元格 
					}
				}
				//删除后下面的数据需要向上移
				sheet.shiftRows(sheet.getLastRowNum() - votedDeatails.size(), sheet.getLastRowNum(), -votedDeatails.size(), true, true);
			}
			//表决详细信息处理（汇总意见显示时，正常按顺序处理合并单元格）
			if(ptVoteResTmpl.getSuggShowFlag().equals("0")){
				sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum() - votedDeatails.size(), sheet.getLastRowNum(), 0, 0));//在sheet里增加合并单元格 
				//处理签名是否显示
				if(!ptVoteResTmpl.getSignShowFlag().equals("0")){
					for (int i = sheet.getLastRowNum() - votedDeatails.size(); i <= sheet.getLastRowNum(); i++) {
						Row row = sheet.getRow(i);
						row.getCell(4).setCellValue("");
						sheet.addMergedRegion(new CellRangeAddress(i, i, 3, 4));//在sheet里增加合并单元格 
					}
				}
			}
			wb.write(os);
			os.close();
			Map<String,Object> fileName = new HashMap<String,Object>();
			fileName.put("fileName", basProject.getProjectTitle() + "表决结果.xls");
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 下载文件
	 * @param params
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "menu5/fun1403/downloadFile",method = RequestMethod.POST)
	public void downloadFile(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response){
		File file;
		try
		{
			String fielName = params.get("fileName").toString();
			fielName = new String((fielName).getBytes("ISO-8859-1"),"UTF-8");
			fielName = java.net.URLDecoder.decode(fielName, "UTF-8");
			file = new File(SysContants.WEB_ROOT_PATH+fielName);
			FileInputStream in=new FileInputStream(file);
			String regEx="[/*:：?<>|\"]";
	        Pattern p = Pattern.compile(regEx);  
	        Matcher m = p.matcher(fielName);  
			fielName = m.replaceAll("").trim();
			FileUtils.downloadFile(response,in,fielName);
			FileUtils.deleteFile(file.getPath());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 秘书端 当前会议:指定为预备会议
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param mettingId
	 * @return
	 */
	@RequestMapping("menu5/fun1404")
	@ResponseBody
	public String toPrepaMetting(HttpServletRequest request, Model model, String mettingId) {
		BasMetting basMetting = new BasMetting();
		basMetting.setId(request.getParameter("mettingId"));
		Boolean flag = basMettingService.updateToPreparatory(basMetting);
		if (flag) {
			// model.addAttribute("id",request.getParameter("mettingId"));
			// model.addAttribute("menuCode",menuCode);
			return "ptsystems/toIndex/menu4";
		}
		return "error/error";
	}

	/**
	 * 秘书端  当前会议：指定为完成会议
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param mettingId
	 * @return
	 */
	@RequestMapping("menu5/fun1405")
	public String toCompleMetting(HttpServletRequest request, Model model, String mettingId) {
		BasMetting basMetting = new BasMetting();
		basMetting.setId(request.getParameter("mettingId"));
		Boolean flag = basMettingService.updateToComplete(basMetting);
		if (flag) {
			// model.addAttribute("id",request.getParameter("mettingId"));
			// model.addAttribute("menuCode",menuCode);
			return "ptsystems/toIndex/menu6";
		}
		return "error/error";
	}

	/**
	 * 跳转到发起表决页面
	 * 
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("menu5/fun1410")
	public String toVote(@RequestParam Map<String, Object> params, Model model) {
		String projectId = params.get("projectId").toString();
		model.addAttribute("sysTitle", ptCommissionService.getOneCommissionByCode(params).getSysTitle());
		model.addAttribute("projectId", projectId);
		model.addAttribute("projectName", basProjectService.selectProjectById(projectId).getProjectTitle());
		params.put("enable", "0");
		params.put("code", SysUserUtils.getCurrentCommissionCode());
		model.addAttribute("voteMatterList", ptVoteMatterConfService.selectAll(params));
		model.addAttribute("meetingId", params.get("meetingId").toString());
		// 开始发起表决,更改状态为表决
		params.put("projectState", "10200302");
		basProjectService.updateProjectState(params);
		return "ptsystems/fun1410";
	}

	@RequestMapping("menu5/fun1410/countVote")
	@ResponseBody
	public List<Map<String, Object>> countVote(@RequestParam Map<String, Object> params) {
		return basMettingService.countVote(params);
	}

	@RequestMapping("menu5/fun1410/selectVote")
	@ResponseBody
	public Map<String, Object> selectVote(@RequestParam Map<String, Object> params, Model model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查询已经投票的委员
		List<Map<String, Object>> votedList = basMettingService.selectVoted(params);
		List<String> votedCodeList = new ArrayList<String>();// 存储已投票委员的code,用来在未投票的列表里排除出去
		params.put("memAuthorityFlagId", "10100100");
		// 查询没有投票的委员
		List<Map<String, Object>> notVoteList = basMettingService.getConfirmList(params);
		StringBuffer votedSb = new StringBuffer();
		StringBuffer notVoteSb = new StringBuffer();
		for (int i = 0; i < votedList.size(); i++) {
			if (i != votedList.size() - 1) {
				votedSb.append(votedList.get(i).get("userName") + "、");
				votedCodeList.add(votedList.get(i).get("userCode").toString());
			} else {
				votedSb.append(votedList.get(i).get("userName"));
				votedCodeList.add(votedList.get(i).get("userCode").toString());
			}
		}

		Iterator<Map<String, Object>> iterator = notVoteList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> map = iterator.next();
			if (votedCodeList.contains(map.get("memUserCode"))) {
				iterator.remove();
			}
		}

		for (int i = 0; i < notVoteList.size(); i++) {
			if (i != notVoteList.size() - 1) {
				notVoteSb.append(notVoteList.get(i).get("memUserName") + "、");
			} else {
				notVoteSb.append(notVoteList.get(i).get("memUserName"));
			}
		}
		resultMap.put("voted", votedSb.toString());
		resultMap.put("notVote", notVoteSb.toString());
		// 统计投票情况
		List<Map<String, Object>> voteCount = basMettingService.countVote(params);
		resultMap.put("voteCount", voteCount);
		// 未投票的人数为空时,出表决结果
		if (notVoteList.size() == 0 && votedList.size() > 0) {
			Map<String, Object> fomulas = basMettingService.selectFomula(params);
			// 查询该委员会是否有一票否决权
			Integer oneMindFlag = Integer.parseInt(fomulas.get("oneMindFlag").toString());
			if (oneMindFlag == 0) {
				// 查询具有一票否决权的委员是否投了否决票
				String memberIds = fomulas.containsKey("memberIds") ? fomulas.get("memberIds").toString() : null;
				params.put("memberIds", memberIds);
				if (memberIds != null && !memberIds.equals("")) {
					params.put("resultType", "03");
					int count = Integer.parseInt(basMettingService.countOneMind(params).get("count").toString());
					if (count > 0) {
						params.put("enable", "0");
						params.put("code", SysUserUtils.getCurrentCommissionCode());
						params.put("val", "03");
						List<Map<String, Object>> resultList = ptVoteMatterConfService.selecResultList(params);
						String resultText = resultList.size() == 0 ? "未通过"
								: resultList.get(0).get("committeeText").toString();
						resultMap.put("result", resultText);
						// 一票否决出结果,更改项目状态
						params.put("projectStateName", "已审");
						params.put("reviewResultName", resultText);
						resultMap.put("resultType", "03");
						basProjectService.updateProjectState(params);
						return resultMap;
					}
				}
			}
			//没有一票否决权和有否决权但未投否决的时候会走下面的方法
			Integer agree = 0;
			Integer rediscuss = 0;
			Integer disagree = 0;
			Integer total = 0;
			for (int i = 0; i < voteCount.size(); i++) {
				if (voteCount.get(i).get("voteResult").toString().equals("01")) {
					agree = Integer.parseInt(voteCount.get(i).get("count").toString());
				}
				if (voteCount.get(i).get("voteResult").toString().equals("02")) {
					rediscuss = Integer.parseInt(voteCount.get(i).get("count").toString());
				}
				if (voteCount.get(i).get("voteResult").toString().equals("03")) {
					disagree = Integer.parseInt(voteCount.get(i).get("count").toString());
				}
				total += Integer.parseInt(voteCount.get(i).get("count").toString());
			}
			String resultText = "";
			if (fomulas.containsKey("adoptFormula")) {
				String againFormula = fomulas.get("adoptFormula").toString();
				String[] array = againFormula
						.substring(againFormula.indexOf("(") + 1, againFormula.indexOf(")")).split(",");
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(array[0], agree);
				map.put(array[1], total);
				boolean result = VoteFormulaUtils.calcFormula(againFormula, map);
				if (result) {
					params.put("enable", "0");
					params.put("code", SysUserUtils.getCurrentCommissionCode());
					params.put("val", "01");
					resultText = ptVoteMatterConfService.selecResultList(params).get(0).get("committeeText")
							.toString();
					resultMap.put("result", resultText);
					resultMap.put("resultType", "01");
					// 如果通过公式不满足,则查询再议公式
				} else if (fomulas.containsKey("againFormula")) {
					String adoptFormula = fomulas.get("againFormula").toString();
					String[] arr = adoptFormula
							.substring(adoptFormula.indexOf("(") + 1, adoptFormula.indexOf(")")).split(",");
					Map<String, Integer> map2 = new HashMap<String, Integer>();
					map2.put(arr[0], rediscuss);
					map2.put(arr[1], total);
					boolean b = VoteFormulaUtils.calcFormula(adoptFormula, map2);
					if (b) {
						params.put("enable", "0");
						params.put("code", SysUserUtils.getCurrentCommissionCode());
						params.put("val", "02");
						resultText = ptVoteMatterConfService.selecResultList(params).get(0)
								.get("committeeText").toString();
						resultMap.put("result", resultText);
						resultMap.put("resultType", "02");
						// 如果通过公式和再议公式都不满足,则直接输出不通过
					} else {
						params.put("enable", "0");
						params.put("code", SysUserUtils.getCurrentCommissionCode());
						params.put("val", "03");
						List<Map<String, Object>> resultList = ptVoteMatterConfService
								.selecResultList(params);
						resultText = resultList.size() == 0 ? "未通过"
								: resultList.get(0).get("committeeText").toString();
						resultMap.put("result", resultText);
						resultMap.put("resultType", "03");
					}
				}
				// 投票结束,更改项目状态
				params.put("projectStateName", "已审");
				params.put("reviewResultName", resultText);
				basProjectService.updateProjectState(params);
			}
		} else {// 否则查询未投票的姓名
			resultMap.put("result", "");

		}
		
		return resultMap;
	}

	/**
	 * 查询会议信息
	 */
	@RequestMapping("metting/selectRecentlyMettingList")
	@ResponseBody
	public List<Map<String, Object>> selectRecentlyMettingList() {
		logger.info("开始查询最近六条会议记录");
		List<Map<String, Object>> list = basMettingService.selectRecentlyMettingList();
		logger.info("结束查询最近六条会议记录" + JSON.toJSONString(list));
		return list;
	}

	
	/**
	 * 准备会议:新建会议功能
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param basProject
	 * @return
	 */
	@RequestMapping(value = "menu4/fun1301",method=RequestMethod.POST)
	@ResponseBody
	public Result saveMenu4Metting(HttpServletRequest request,Model model,@ModelAttribute BasMetting basMetting){
		Result newResult = null;
		logger.info("开始保存预备会议！！");
		Boolean flag = basMettingService.saveMenu4MettingList(basMetting);
		if(flag){
			newResult =basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg03200);
			newResult.setObj(basMetting);//将对象返回前台，目的取mettingId
			return newResult;
		}
		newResult =basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg03201);
		return newResult;
	}
	
	
	@RequestMapping(value = "menu5/fun1407/showList",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>>  showOfflineUserList(@RequestParam Map<String, String> params, Model model){
		List<Map<String, Object>> mettingCommsionList = ptBasUserService.findCommisionUserList(params);
		if(logger.isDebugEnabled()){
			logger.info("委员信息{}",JSON.toJSONString(mettingCommsionList));
		}
		return mettingCommsionList;
	}
	/**
	 * 开启离线会议表决功能
	 * @param request
	 * @param model
	 * @param basProject
	 * @return
	 */
	@RequestMapping(value = "menu5/fun1407",method=RequestMethod.POST)
	public String showOfflineVoteList(@RequestParam Map<String, Object> params, Model model){
		logger.info("开启离线展示委员功能");
		String meetingId = params.get("mettingId").toString();
		params.put("meetingId", meetingId);
		//查询此次到会的委员

		//得到机构信息
	    List<Map<String, Object>> orgList=ptCommissionOrgService.toOrgList();
		//model.addAttribute("mettingCommsionList",mettingCommsionList);
		model.addAttribute("orgList",orgList);//用作下拉框
		model.addAttribute("params",params);//页面上一些参数
		if(logger.isDebugEnabled()){
			//logger.info("委员信息：{}",JSON.toJSONString(mettingCommsionList));
			logger.info("委员机构信息：{}",JSON.toJSONString(orgList));
			logger.info("其它参数信息：{}",JSON.toJSONString(params));
		}
		logger.info("结束离线展示委员功能");
		//boootable列表
		return "ptsystems/fun1407_offline_vote";
	}
	
	
	/**
	 * 开启离线会议表决功能
	 * @param request
	 * @param model
	 * @param basProject
	 * @return
	 */
	@RequestMapping(value = "menu5/fun1407/suggestion",method=RequestMethod.POST)
	public String confirmOfflineSuggestionList(HttpServletRequest request,@RequestParam Map<String, Object> params, Model model){
		logger.info("开启离线表决功能");
		//得到投票按钮下拉框信息
		String mettingId = (String) params.get("meetingId");
		String projectId = (String) params.get("projectId");
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		Map<String,String> map=Maps.newHashMap();
		map.put("code", tableNamePrefix);
		map.put("conferenceId", mettingId);
		map.put("projectId", projectId);
		//插入与会人员信息
		String clientAddress = IPUtils.getClientAddress(request);
		params.put("IP", clientAddress);
		Boolean flag=basMettingMemService.saveMemMettingList(params);
		//得到投票按钮信息
		 List<Map<String, Object>> voteMatterConfList= ptVoteMatterConfService.findBtnInfo(map);
		 model.addAttribute("voteMatterConfList",voteMatterConfList);//投票选择
		 model.addAttribute("params",params);//页面上一些参数
		if(logger.isDebugEnabled()){
			logger.info("委员机构信息：{}",JSON.toJSONString(voteMatterConfList));
			logger.info("其它参数信息：{}",JSON.toJSONString(params));
		}
		logger.info("结束离线表决功能");
		//boootable列表
		return "ptsystems/offline_suggestion";
	}
	
	
	@RequestMapping(value = "menu5/fun1407/offlineVote",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> offLineVoteResult(HttpServletRequest request,@RequestParam Map<String, Object> params,Model model){
		Result newResult = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Integer total = Integer.parseInt((String) params.get("total"));
		// 人数不能为空
		if (total > 0) {
			Map<String, Object> fomulas = basMettingService.selectFomula(params);
			// 查询该委员会是否有一票否决权
			Integer oneMindFlag = Integer.parseInt(fomulas.get("oneMindFlag").toString());
			if (oneMindFlag == 0) {
				// 查询具有一票否决权的委员是否投了否决票
				String memberIds = fomulas.containsKey("memberIds") ? fomulas.get("memberIds").toString() : null;
				params.put("memberIds", memberIds);
				if (memberIds != null && !memberIds.equals("")) {
					params.put("resultType", "03");
					int count = Integer.parseInt(basMettingService.countOneMind(params).get("count").toString());
					if (count > 0) {
						params.put("enable", "0");
						params.put("code", SysUserUtils.getCurrentCommissionCode());
						params.put("val", "03");
						List<Map<String, Object>> resultList = ptVoteMatterConfService.selecResultList(params);
						String resultText = resultList.size() == 0 ? "未通过"
								: resultList.get(0).get("committeeText").toString();
						resultMap.put("result", resultText);
						// 一票否决出结果,更改项目状态
						params.put("projectStateName", "已审");
						params.put("reviewResultName", resultText);
						resultMap.put("resultType", "03");
//						basProjectService.updateProjectState(params);
						return resultMap;
					}
				}
			}
			//没有一票否决权和有否决权但未投否决的时候会走下面的方法
			Integer agree = Integer.parseInt((String) params.get("agree"));
			Integer rediscuss = Integer.parseInt((String) params.get("rediscuss"));
			Integer disagree = Integer.parseInt((String) params.get("disagree"));
//			for (int i = 0; i < voteCount.size(); i++) {
//				if (voteCount.get(i).get("voteResult").toString().equals("01")) {
//					agree = Integer.parseInt(voteCount.get(i).get("count").toString());
//				}
//				if (voteCount.get(i).get("voteResult").toString().equals("02")) {
//					rediscuss = Integer.parseInt(voteCount.get(i).get("count").toString());
//				}
//				if (voteCount.get(i).get("voteResult").toString().equals("03")) {
//					disagree = Integer.parseInt(voteCount.get(i).get("count").toString());
//				}
//				total += Integer.parseInt(voteCount.get(i).get("count").toString());
//			}
			String resultText = "";
			if (fomulas.containsKey("adoptFormula")) {
				String againFormula = fomulas.get("adoptFormula").toString();
				String[] array = againFormula
						.substring(againFormula.indexOf("(") + 1, againFormula.indexOf(")")).split(",");
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(array[0], agree);
				map.put(array[1], total);
				boolean result = VoteFormulaUtils.calcFormula(againFormula, map);
				if (result) {
					params.put("enable", "0");
					params.put("code", SysUserUtils.getCurrentCommissionCode());
					params.put("val", "01");
					resultText = ptVoteMatterConfService.selecResultList(params).get(0).get("committeeText")
							.toString();
					resultMap.put("result", resultText);
					resultMap.put("resultType", "01");
					// 如果通过公式不满足,则查询再议公式
				} else if (fomulas.containsKey("againFormula")) {
					String adoptFormula = fomulas.get("againFormula").toString();
					String[] arr = adoptFormula
							.substring(adoptFormula.indexOf("(") + 1, adoptFormula.indexOf(")")).split(",");
					Map<String, Integer> map2 = new HashMap<String, Integer>();
					map2.put(arr[0], rediscuss);
					map2.put(arr[1], total);
					boolean b = VoteFormulaUtils.calcFormula(adoptFormula, map2);
					if (b) {
						params.put("enable", "0");
						params.put("code", SysUserUtils.getCurrentCommissionCode());
						params.put("val", "02");
						resultText = ptVoteMatterConfService.selecResultList(params).get(0)
								.get("committeeText").toString();
						resultMap.put("result", resultText);
						resultMap.put("resultType", "02");
						// 如果通过公式和再议公式都不满足,则直接输出不通过
					} else {
						params.put("enable", "0");
						params.put("code", SysUserUtils.getCurrentCommissionCode());
						params.put("val", "03");
						List<Map<String, Object>> resultList = ptVoteMatterConfService
								.selecResultList(params);
						resultText = resultList.size() == 0 ? "未通过"
								: resultList.get(0).get("committeeText").toString();
						resultMap.put("result", resultText);
						resultMap.put("resultType", "03");
					}
				}
				// 投票结束,更改项目状态
				params.put("projectStateName", "已审");
				params.put("reviewResultName", resultText);
//				basProjectService.updateProjectState(params);
			}
		} else {// 否则查询未投票的姓名
			resultMap.put("result", "");

		}
		return resultMap;
	}
	
	@RequestMapping(value = "menu5/fun1407/saveVoteSuggestion",method=RequestMethod.POST)
	@ResponseBody
	public Result saveOffLineVoteResult(HttpServletRequest request,@RequestParam Map<String, Object> params,Model model){
		String clientAddress = IPUtils.getClientAddress(request);
		params.put("clientAddress", clientAddress);
		Boolean flag = basProComeMemService.saveBatchBasProComeMemList(params);
		if(flag){
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	
}
