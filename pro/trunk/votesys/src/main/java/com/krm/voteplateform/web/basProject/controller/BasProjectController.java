package com.krm.voteplateform.web.basProject.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.vo.MapExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
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
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.glue.GlueCodeFactory;
import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;
import com.krm.voteplateform.web.basProExDetail.model.BasProExDetail;
import com.krm.voteplateform.web.basProExDetail.service.BasProExDetailService;
import com.krm.voteplateform.web.basProject.model.BasMetting;
import com.krm.voteplateform.web.basProject.model.BasProject;
import com.krm.voteplateform.web.basProject.service.BasMettingService;
import com.krm.voteplateform.web.basProject.service.BasProjectService;
import com.krm.voteplateform.web.constants.MessageKeyConstants;
import com.krm.voteplateform.web.menulistconf.service.MenuListConfService;
import com.krm.voteplateform.web.menuwinconf.service.BasMessageService;
import com.krm.voteplateform.web.ptcommissionorg.model.PtCommissionOrg;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
@RequestMapping("ptsystems")
public class BasProjectController extends BaseController{
	
	@Autowired
	private BasProjectService basProjectService;
	
	//菜单项目列表配置表
	@Autowired
	private MenuListConfService menuListConfService;
	
	//消息提示表
	@Resource
	private BasMessageService  basMessageService;
	
	//项目扩展明细表
	@Resource
	private BasProExDetailService basProExDetailService;
	
	//项目扩展明细表
	@Resource
	private BasMettingService basMettingService;
	
	@Resource
	private SQLManager sqlManager;
	
	Result newResult = null;   //定义全局变量，返回提示信息
	
	
	
	/**
	 * 查询所有未审项目列表信息
	 * @author zhangYauHai
	 * @param model
	 * @return
	 */
	@RequestMapping("menu2List")
	@ResponseBody
	public List<Map<String, Object>> menu2List(Model model) {
		logger.info("开始查询议项目表信息");
		List<Map<String, Object>> findBasPorject = basProjectService.findNoAuditoItemList();
		logger.info("结束查询议项目表信息" + JSON.toJSONString(findBasPorject));
		return findBasPorject;
	}
	
	/**
	 * 未审项目:删除列表信息
	 * @param request
	 * @return
	 */
	@RequestMapping("menu2Delete")
	@ResponseBody
	public Result removeNoAuditoItem(HttpServletRequest request){
		BasProject basProject=new BasProject();
		String projectId = request.getParameter("projectId");
		//String functionCode = request.getParameter("functionCode");
		basProject.setProjectId(projectId);
		logger.info("开始删除未审项目会议信息{}"+ projectId);
		Boolean flag=	basProjectService.removeBasProject(basProject);
		logger.info("flag{}"+flag+ "true:返回Result.successResult(),false:返回Result.errorResult()");
		if(flag){
			return Result.successResult();
		}
		logger.info("结束删除未审项目列表信息");
		return Result.errorResult();
	
	}
	
	
	/**
	 * 已审项目:列表数据查询,搜索功能
	 * @author zhangYuHai
	 * @param model
	 * @param baseProject
	 * @return
	 */
	@RequestMapping("menu3List")
	@ResponseBody
	public Pagination<Map<String, Object>> findAuditoItem(Pagination<Map<String, Object>> page,BasProject basProject,Integer pageNumber, Integer pageSize) {
		logger.info("开始分页查询已审项目列表信息");
		basProject.tableName = SysUserUtils.getCurrentCommissionCode();
		List<Map<String, Object>> findAudProList = basProjectService.findAuditoItemList(page,basProject);
		logger.info("结束分页查询已审项目列表信息"+JSON.toJSONString(findAudProList));
		page.setRows(findAudProList);
		return page;
	}
	
	
	/**
	 * 已审项目:导出功能
	 * @author zhangYuHai
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("menu3ExportExcel")
	public String exportMenue3Table(ModelMap modelMap, HttpServletRequest request,BasProject basProject) {
		
		
		logger.info("开始查询已审项目列表出页面配置的数据");
		// 属性列表
		String resid="3"; //menu3菜单id='3'
		List<Map<String,Object>>  basMenuListConf = menuListConfService.findMenuListConf(resid);
		logger.info("查询出数据结果" +JSON.toJSONString(basMenuListConf));
		List<ExcelExportEntity> entityList = Lists.newArrayList();
		//添加下载的表头
		ExcelExportEntity entity = null;
		Map<String,Object> basDict = null;
		String enName = null;
		Set<String> set = Sets.newHashSet();
		for (int i = 0; i < basMenuListConf.size(); i++) {
			basDict = basMenuListConf.get(i);
			String dataType= basDict.get("dataType").toString();
			logger.info("dataType类型为03时，enName后面拼上Name");
			if("03".equals(dataType)){
				String enNameResult = basDict.get("enName").toString();
				enName = enNameResult+"Name";
			}else{
				enName = basDict.get("enName").toString();
			}
			entity = new ExcelExportEntity(basDict.get("mapCnName").toString(),enName);
			entityList.add(entity);
			set.add(enName);//放置需要显示的中文字段的表头
			
		}
		
		//添加导出的数据源
		List<Map<String, Object>> dataResultList = basProjectService.exportData(basProject);//源数据
		logger.info("查询出导出的数据源结果" +JSON.toJSONString(dataResultList));
		List<Map<String, Object>> dataResult = Lists.newArrayList();
		Map<String,Object> m = null;
		for (int i = 0; i < dataResultList.size(); i++) {
			m = Maps.newHashMap();
			Map<String, Object> map = dataResultList.get(i);//获取每个配置表头字段
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				if(set.contains(string)){//判断表头Set<String>中是否包含 数据源dataResult中的字段
					m.put(string,map.get(string));//存在放入map中
				}
			}
			dataResult.add(m);
		}
		
		modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);// 属性列表
		modelMap.put(MapExcelConstants.MAP_LIST, dataResult);// 数据源
		modelMap.put(MapExcelConstants.FILE_NAME, "普通导出测试");// 文件名称
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("导出数据", "已审项目信信息", ExcelType.XSSF));// 导出参数
		return MapExcelConstants.JEECG_MAP_EXCEL_VIEW;
	}
	
	
	/**
	 * 查询委员端历史会议信息
	 * @author zhangYuHai
	 * @param model
	 * @param baseProject
	 * @return
	 */
	@RequestMapping("menu9List")
	@ResponseBody
	public Pagination<Map<String, Object>> findVoteHistoryList(Pagination<Map<String, Object>> page,BasProject basProject,Integer pageNumber, Integer pageSize) {
		logger.info("开始分页查询查询委员端历史会议信息");
		basProject.tableName = SysUserUtils.getCurrentCommissionCode();
		List<Map<String, Object>> findVoteHistoryList = basProjectService.findAuditoItemList(page,basProject);//与已审核项目列表信息查询同一个查询方法
		logger.info("结束分页查询查询委员端历史会议信息"+JSON.toJSONString(findVoteHistoryList));
		page.setRows(findVoteHistoryList);
		return page;
	}
	
	
	/**
	 * 查询委员端当前会议列表信息
	 * @author zhangYuHai
	 * @param model
	 * @return
	 */
	@RequestMapping("menu8List")
	@ResponseBody
	public List<Map<String, Object>> findVoteCurrentMetting(Model model,HttpServletRequest request) {
		String mettingId = request.getParameter("mettingId");
		logger.info("开始查委员端当前会议列表信息{}" + mettingId);
		List<Map<String, Object>> findProjectMetting = basProjectService.findProjectByIdList(mettingId);
		logger.info("结束查委员端当前会议列表信息" + JSON.toJSONString(findProjectMetting));
		return findProjectMetting;
	}
	
	
	/**
	 * 添加议题 {预备会议与当前会议中}
	 * @param request
	 * @param id
	 * @param mettingId
	 * @return
	 */
	@RequestMapping("menu4/fun1306")
	@ResponseBody
	public Result addProjectById(HttpServletRequest request,String projectId,String mettingId){
		Boolean flag = false;
		Integer  result = basProjectService.updateByProjectId(projectId,mettingId);
		flag = result>0? true:false;
		if(flag){
			return Result.successResult();
		}
		return Result.errorResult();
		
	}
	
	/**
	 *跳转到添加到议题页面
	 * @param request
	 * @param id
	 * @param mettingId
	 * @return
	 */
	@RequestMapping("menu4ormenu5/{functionCode}/addproject")
	public String toAddProject(HttpServletRequest request,Model model,String projectId,String mettingId){
		model.addAttribute("mettingId", mettingId);
		return "ptsystems/add_project";
		
	}
	
	/**
	 * 未审项目:新增项目功能
	 * @author zhangYuHai
	 * @param request
	 * @param basProject  对象
	 * @param projectId   项目id
	 * @param basicFlag  基本项标志
	 * @return
	 */
	@RequestMapping(value = "menu2/fun1101",method=RequestMethod.POST)
	@ResponseBody
	public Result saveMenu2Items(HttpServletRequest request,Model model,@ModelAttribute BasProject basProject){
		String basicFlag = request.getParameter("basicFlag");
		String projectId =request.getParameter("projectId");
		String extendGroupId = request.getParameter("extendGroupId");
		logger.info("开始保存未审项目数据basicFlag{}，1：表示为启用，0启用" );
		if("1".equals(basicFlag)){   //basicFlag 1:不启用 0：启用
			if(projectId == null || projectId.equals("")){
				newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg01205);
				return newResult;
			}
		}
		if(CollectionUtils.isNotEmpty(basProject.getDetailForm())){ //添加时存在明细数据
			logger.info("添加未审项目中是否存在明细数据 size >0 存在数据" + basProject.getDetailForm().size());
			List<BasProExDetail> tempList = basProject.getDetailForm();
			basProExDetailService.deleteBasProExDetail(projectId,extendGroupId);//先删除原有明细再修改
			for (BasProExDetail basProExDetail : tempList) {
				basProExDetail.setProjectId(projectId); //获取projectId
			}
			logger.info("保存明细flag{}");
			Boolean flag = basProjectService.saveForDetailList(tempList);
			if(flag){
				newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg01200);
				logger.info("获取成功后的提示信息" + newResult);
				newResult.setObj(basProject);
				return  newResult;
			}
		}else{
			try{
				String id = SysUserUtils.getCurPtCommission().getPdsid();
				String proCodeType = SysUserUtils.getCurPtCommission().getProCodeType();
				if("1".equals(proCodeType)){ //proCodeType="1"或者"2"
					String specProCode = GlueCodeFactory.glue(id, basProject,null,1);
					if(StringUtils.isNotEmpty(specProCode)){
						//String specProCode = GlueCodeFactory.glue(id, basProject,null,1);
						basProject.setSpecProCode(specProCode);
					}
				}
			}catch(Exception e){
				logger.error("动态执行Java编码发生错误。",e);
				return new Result(Result.ERROR,"动态执行Java编码发生错误。");
			}
			Boolean flag = basProjectService.saveProjectByFunctionCode(basProject);//添加时未审基本数据
			logger.info("保存基本flag{}");
			if(flag){
				newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg01200);
				logger.info("获取成功后的提示信息" + newResult);
				newResult.setObj(basProject);//将对象返回前台，目的取projecId
				return newResult;
			}
		}
		logger.info("结束添加项目信息！！");
		newResult =basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg01201);
		return newResult;
	}
	
	/**
	 * 未审项目:修改项目功能
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param basProject
	 * @return
	 */
	@RequestMapping(value = "menu2/fun1102",method=RequestMethod.POST)
	@ResponseBody
	public Result updateMenu2Items(HttpServletRequest request,Model model,BasProject basProject){
			Boolean flag = null;
	 flag = basProjectService.updateProjectByFunctionCode(basProject);
	if(flag){
		newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg01300);
		return newResult;
		}
	newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg01301);
	return newResult;
	}
	
	
	/**
	 * 准备会议:修改预备会议题
	 * @param request
	 * @param model
	 * @param basProject
	 * @return
	 */
	@RequestMapping(value = "menu4/fun1307",method=RequestMethod.POST)
	@ResponseBody
	public Result updateMenu4ProperList(HttpServletRequest request,Model model,BasProject basProject){
		Boolean flag = null;
		 flag = basProjectService.updateProjectByFunctionCode(basProject);
		if(flag){
			newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg03400);
			return newResult;
			}
		newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg03401);
		return newResult;
		}
	
	
	
	/**
	 * 当前会议:修改项目功能
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @param basProject
	 * @return
	 */
	@RequestMapping(value = "menu5/fun1402",method=RequestMethod.POST)
	@ResponseBody
	public Result updateMenu5CurrentList(HttpServletRequest request,Model model,BasProject basProject){
		Boolean flag = null;
		 flag = basProjectService.updateProjectByFunctionCode(basProject);
		if(flag){
			newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg04200);
			return newResult;
			}
		newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg04201);
		return newResult;
	}
	
	/**
	 * 指定再议功能
	 * @param request
	 * @param model
	 * @param basProject
	 * @return
	 */
	@RequestMapping(value = "menu5/fun1411",method=RequestMethod.POST)
	@ResponseBody
	public Result curMenu5toreDis(HttpServletRequest request,Model model){
		Boolean flag = null;
		BasProject basProject = new BasProject();
		String projectId = request.getParameter("projectId");
		basProject.setProjectId(projectId);
		 flag = basProjectService.curMenu5toreDis(basProject);
		if(flag){
			newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg04500);
			return newResult;
			}
		newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg04501);
		return newResult;
	}
	
	
	/**
	 * 当前会议:提定不审议功能
	 * @author zhangYuHai
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "menu5/fun1412",method=RequestMethod.POST)
	@ResponseBody
	public Result curMenu5NoreDis(HttpServletRequest request,Model model){
		Boolean flag = null;
		BasProject basProject = new BasProject();
		String projectId = request.getParameter("projectId");
		basProject.setProjectId(projectId);
		 flag = basProjectService.updateQuitMetting(projectId);
		if(flag){
			newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg04600);
			return newResult;
			}
		newResult = basMessageService.findMessageByMegfuncode(MessageKeyConstants.Message_Key_msg04601);
		return newResult;
	}
	

	/**
	 * 准备会议,当前会议:项目升序/降序
	 * @return
	 */
	@RequestMapping(value = "menu4or5/ascordesc/{functionCode}",method=RequestMethod.POST)
	@ResponseBody
	public Result ascProjectOrder(HttpServletRequest request,@RequestParam Map<String, Object> params){
		boolean flag=basProjectService.ascProjectOrder(params);
		return flag?Result.successResult():Result.errorResult();
		
	}
	
	

	
	/**
	 * 当前会议:导出功能
	 * @author zhangYuHai
	 * @param modelMap
	 * @param request
	 * @param basProject
	 * @return
	 */
	@RequestMapping("menu5ExportExcel")
	public String menu5ExportExcel(ModelMap modelMap, HttpServletRequest request,BasProject basProject) {
		logger.info("开始查询已审项目列表出页面配置的数据");
		// 属性列表
		String resid="5"; //menu3菜单id='5'
		List<Map<String,Object>>  basMenuListConf = menuListConfService.findMenuListConf(resid);
		logger.info("查询出数据结果" +JSON.toJSONString(basMenuListConf));
		List<ExcelExportEntity> entityList = Lists.newArrayList();
		//添加下载的表头
		ExcelExportEntity entity = null;
		Map<String,Object> basDict = null;
		String enName = null;
		Set<String> set = Sets.newHashSet();
		for (int i = 0; i < basMenuListConf.size(); i++) {
			basDict = basMenuListConf.get(i);
			String dataType= basDict.get("dataType").toString();
			logger.info("dataType类型为03时，enName后面拼上Name");
			if("03".equals(dataType)){
				String enNameResult = basDict.get("enName").toString();
				enName = enNameResult+"Name";
			}else{
				enName = basDict.get("enName").toString();
			}
			entity = new ExcelExportEntity(basDict.get("mapCnName").toString(),enName);
			entityList.add(entity);
			set.add(enName);//放置需要显示的中文字段的表头
		}
		//添加导出的数据源
		String mettingId = request.getParameter("mettingId");
		List<Map<String, Object>>  dataResultList = basProjectService.findProjectByIdList(mettingId);//源数据
		//List<Map<String, Object>> dataResultList = basProjectService.exportData(basProject);//源数据
		logger.info("查询出导出的数据源结果" +JSON.toJSONString(dataResultList));
		List<Map<String,Object>> dataResult = Lists.newArrayList();
		Map<String,Object> m = null;
		for (int i = 0; i < dataResultList.size(); i++) {
			m = Maps.newHashMap();
			Map<String, Object> map = dataResultList.get(i);//获取每个配置表头字段
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				if(set.contains(string)){//判断表头Set<String>中是否包含 数据源dataResult中的字段
					m.put(string,map.get(string));//存在放入map中
				}
			}
			dataResult.add(m);
		}
		
		modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);// 属性列表
		modelMap.put(MapExcelConstants.MAP_LIST, dataResult);// 数据源
		modelMap.put(MapExcelConstants.FILE_NAME, "普通导出测试");// 文件名称
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("导出数据", "已审项目信信息", ExcelType.XSSF));// 导出参数
		return MapExcelConstants.JEECG_MAP_EXCEL_VIEW;
	}
	
}
