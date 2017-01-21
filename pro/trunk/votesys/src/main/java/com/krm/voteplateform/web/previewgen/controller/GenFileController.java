package com.krm.voteplateform.web.previewgen.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.beetl.utils.BeetlUtils;
import com.krm.voteplateform.common.model.BootstrapTable;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.web.basProject.service.BasMettingService;
import com.krm.voteplateform.web.constants.SessionKeys;
import com.krm.voteplateform.web.constants.SysContants;
import com.krm.voteplateform.web.function.service.PtBasFunConfService;
import com.krm.voteplateform.web.menuwinconf.service.BasMenuWinConfService;
import com.krm.voteplateform.web.previewgen.service.GenBtnService;
import com.krm.voteplateform.web.previewgen.service.GenFileServcie;
import com.krm.voteplateform.web.previewgen.util.GenConstant;
import com.krm.voteplateform.web.previewgen.util.TempUtil;
import com.krm.voteplateform.web.ptsystemsmenu.service.PtSystemsMenuService;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * ClassName:GenPreviewController <br/>
 * Function: 控制类. <br/>
 * Reason: 生成文件入口. <br/>
 * Date: 2016年11月21日 下午5:37:42 <br/>
 * 
 * @author lixy
 * @version
 * @since JDK 1.7
 * @see
 */
@Controller
@RequestMapping("pt/gen")
public class GenFileController extends BaseController {

	@Autowired
	private GenFileServcie genPreviewServcie;

	@Autowired
	private GenBtnService genBtnService;

	@Autowired
	private BasMenuWinConfService basMenuWinConfService;
	
	@Resource
	private BeetlGroupUtilConfiguration beetlConfig;
	
	@Autowired
	private PtBasFunConfService ptBasFunConfService;
	
	/**
	 * 主方法生成
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/genCommissionConf", method = RequestMethod.POST)
	@ResponseBody
	public Result genCommissionConf(HttpServletRequest request, Model model) {
		logger.info("开始生成页面");
		// 查询时表名大写
		String tableName = request.getParameter("code");
		String id = request.getParameter("id");//暂时没用到
		GroupTemplate gt = BeetlUtils.getBeetlGroupTemplate();
		// 资源表
		Set<Map<String, Object>> menuSet = genPreviewServcie.getBasResource(tableName);

		boolean flag = false;
		
		for (Map<String, Object> mu : menuSet) {
			// 字典表
			String rsid = (String) mu.get("id");
			String menu = (String) mu.get("resourceCode");
			String commText = (String) mu.get("commText");
			// 是否自动生成0:自动生成 1:不自动生成
			String autoGenFlag = (String) mu.get("autoGenFlag");
			if ("1".equals(autoGenFlag)) {
							continue;
			}
			String tempId = (String) mu.get("tempId");
			String srcTemplateFileName = (String) mu.get("tempName");
			if (StringUtils.isEmpty(srcTemplateFileName)) {
					srcTemplateFileName = "bootTableTemplate.html";// 默认模板
					}
			// 得到字典信息
			BootstrapTable bootstrapTable = genPreviewServcie.getTemplateTableInfo(tableName, rsid);
			// 得到搜索框信息
			List<Map<String, Object>> smmap = genPreviewServcie.getSearchTableInfo(tableName, rsid);
			// 得到按钮信息
			List<Map<String, Object>> btninfo = genBtnService.getButtonTableInfo(tableName, rsid);
			// 得到按钮配置弹出窗体信息
			List<Map<String, Object>> btnwind = genBtnService.getBtnTableWind(tableName, rsid);
				if ("menu2".equals(menu)||"menu4".equals(menu)||"menu5".equals(menu)) {
					
				} else if ("menu3".equals(menu)||"menu6".equals(menu)) {
					bootstrapTable.setPagination(true);
				}
				// 得到字典信息bootstrapTable.setPagination(true);
				Template template = gt.getTemplate(SysContants.TEMPLTE_PATH + srcTemplateFileName);
				Map<String, String> resultmp = Maps.newHashMap();
				resultmp.put(MyBatisConstans.DYTABLE_KEY, tableName.toLowerCase());
				resultmp.put("menu", menu);
				resultmp.put("commText", commText);
				TempUtil.TemplateCommon(resultmp);//常量
				template.binding("bt", bootstrapTable);
				template.binding("sm", smmap);
				template.binding("btninfo", btninfo);
				template.binding("btnwind", btnwind);
				template.binding("pageinfo", resultmp);
				String render = template.render();
				String srcToFileName = menu + ".html";// 文件名
				String toPath =  SysContants.TEMPLTE_TO_PATH + tableName.toLowerCase() + File.separator + srcToFileName;;
				// 删除文件
				FileUtils.deleteFile(toPath);
				FileUtils.writeFile(render, toPath);
				flag = true;
				logger.info("table页面参数:{}",JSON.toJSONString(bootstrapTable));
				logger.info("搜索框信息页面参数:{}" , JSON.toJSONString(smmap));
				logger.info("按钮信息页面参数:{}" , JSON.toJSONString(btninfo));
				logger.info("弹出窗体信息页面参数{}:" , JSON.toJSONString(btnwind));
				logger.info("main页面参数{}:" ,JSON.toJSONString(resultmp));
				logger.info("结束生成{}页面.......", commText);
		}
		//开始生成窗口页面
		Map<String,String> params = new HashMap<String,String>();
		params.put(MyBatisConstans.DYTABLE_KEY, tableName);
		List<Map<String, Object>> list = ptBasFunConfService.findBtnBasFunConfWind(params);
		for (Map<String, Object> map : list) {
			map.put(MyBatisConstans.DYTABLE_KEY, tableName);
			this.genWinConf(map);
		}
		Result result = null;
		if (flag) {
			result = Result.successResult();
			result.setMsg("生成成功！");
		} else {
			result = Result.errorResult();
			result.setMsg("生成失败！");
		}
		logger.info("结束生成页面");
		return result;
	}

	public void genWinConf(Map<String, Object> params){
		String functionCode = params.get("functionCode").toString();
		logger.info("开始生成预览界面:"+functionCode+".html");
		String tableName = params.get("tableName").toString();
		Map<String, Object> result = basMenuWinConfService.getTempaletName(params);
		//如果没有绑定模板就不生成
		if(result != null){
			List<Map<String, Object>> list =basMenuWinConfService.getWinConfList(params);
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String,Object> group = new HashMap<String,Object>();
			String grpidPreSuffix = "";
			String itemPreSuffix = "";
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> map = list.get(i);
				String grpidSuffix = map.get("groupid").toString().substring(3,6);
				if(grpidSuffix.equals("000")){
					grpidPreSuffix = map.get("groupid").toString().substring(0,3);	//遍历到组时保存组前缀
					group  = map;
					List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();//临时存放form表单的相关信息
					map.put("itemList", itemList);
					resultList.add(map);
				}
				if(!grpidSuffix.equals("000")){
					itemPreSuffix = map.get("groupid").toString().substring(0,3);	//临时保存采集框的前缀id
				}
				if(!grpidSuffix.equals("000") && itemPreSuffix.equals(grpidPreSuffix)){	//采集框的前缀ID和组前缀ID相同时才放进去
					String type = map.get("type").toString();
					if(type.equals("1")){
						String collectType = map.get("collectType").toString();
						//下拉框需要获取数据
						if(collectType.equals("1") || collectType.equals("5")){
							Map<String, Object> selgroups = basMenuWinConfService.selectGroupCode(tableName, map.get("datasource").toString());
							map.putAll(selgroups);
						}
					}
					//明细列表
					if(type.equals("2")){
						params.put("gatherId", map.get("id").toString());
						List<Map<String, Object>> detailList = basMenuWinConfService.getExtDetList(params);
						for (Map<String, Object> detail : detailList) {
							if(detail.get("datasource") != null){
								Map<String, Object> selgroups = basMenuWinConfService.selectGroupCode(tableName, detail.get("datasource").toString());
								detail.putAll(selgroups);
							}
						}
						map.put("detailList", detailList);
					}
					List<Map<String, Object>> itemList = (List<Map<String, Object>>) group.get("itemList");
					itemList.add(map);
				}
			}
			logger.info("返回结果:"+resultList);
			
			GroupTemplate gt = beetlConfig.getGroupTemplate();
			String temptateFileName = result == null?null:result.get("tempName").toString();
			Template template = gt.getTemplate(SysContants.TEMPLTE_PATH+temptateFileName);
			template.binding("rs", resultList);
			template.binding("functionCode", functionCode);
			String render = template.render();
			String toPath = SysContants.TEMPLTE_TO_PATH+tableName.toLowerCase()+File.separator+functionCode+".html";
			//删除文件
			FileUtils.deleteFile(toPath);
			FileUtils.writeFile(render, toPath);
			logger.info("开始生成预览界面:"+functionCode+".html");
		}
	}
	
	
	/**
	 * 主方法生成(预览形式)
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/genCommissionConfPreview")
	public String genCommissionConfPreview(HttpServletRequest request, Model model) {
		logger.info("开始生成预览页面");
		// 查询时表名大写
			String tableName = request.getParameter("code");
			String menuCode = request.getParameter("menuCode");
			String id = request.getParameter("id");
			//作预览用临时用，到时要去掉
			SysUserUtils.setCurrentCommissionCode(tableName);
			
			GroupTemplate gt = BeetlUtils.getBeetlGroupTemplate();
			Set<Map<String, Object>> menuSet = genPreviewServcie.getBasResource(tableName);
			boolean flag = false;
			String htl = null;
		
			for (Map<String, Object> mu : menuSet) {
			// 字典表
			String rsid = (String) mu.get("id");
			String menu = (String) mu.get("resourceCode");
			String commText = (String) mu.get("commText");
			// 是否自动生成0:自动生成 1:不自动生成
			String autoGenFlag = (String) mu.get("autoGenFlag");
			if ("1".equals(autoGenFlag)) {
				continue;
			}
			String tempId = (String) mu.get("tempId");
			String srcTemplateFileName = (String) mu.get("tempName");
			if (StringUtils.isEmpty(srcTemplateFileName)) {
					srcTemplateFileName = "bootTableTemplate2.html";// 默认模板
			}
				// 得到字典信息
				BootstrapTable bootstrapTable = genPreviewServcie.getTemplateTableInfo(tableName, rsid);
				// 得到搜索框信息
				List<Map<String, Object>> smmap = genPreviewServcie.getSearchTableInfo(tableName, rsid);
				// 得到按钮信息
				List<Map<String, Object>> btninfo = genBtnService.getButtonTableInfo(tableName, rsid);
				// 得到按钮配置弹出窗体信息
				List<Map<String, Object>> btnwind = genBtnService.getBtnTableWind(tableName, rsid);
					//未审,会议准备,当前,完成会议
					if ("menu2".equals(menu)||"menu4".equals(menu)||"menu5".equals(menu)) {
					//,已审,，完成
					} else if ("menu3".equals(menu)||"menu6".equals(menu)) {
						// 得到字典信息bootstrapTable.setPagination(true);
						bootstrapTable.setPagination(true);
					
					} 
					Template template = gt.getTemplate(SysContants.TEMPLTE_PATH + srcTemplateFileName);
					//bootstrapTable.setUrl("'${ctxPath!}/ptsystems/" + menu + "List?r='" + "+Math.random()");
					Map<String, String> resultmp = Maps.newHashMap();
					resultmp.put(MyBatisConstans.DYTABLE_KEY, tableName.toLowerCase());
					resultmp.put("menu", menu);
					resultmp.put("commText", commText);
					
				
					TempUtil.TemplateCommon(resultmp);//常量
					
					//this.toIndex(resultmp, menuCode);//这个可以注释
					resultmp.put("previewFlag", "1");//设置为预览标记
					
					template.binding("bt", bootstrapTable);
					template.binding("sm", smmap);
					template.binding("btninfo", btninfo);
					template.binding("btnwind", btnwind);
					template.binding("pageinfo", resultmp);
					String render = template.render();
					String srcToFileName = menu + ".html";// 文件名
					String toPath =  SysContants.TEMPLTE_TO_PATH + tableName.toLowerCase() + File.separator + srcToFileName;
					// 删除文件
					FileUtils.deleteFile(toPath);
					FileUtils.writeFile(render, toPath);
					flag = true;
					htl = srcToFileName.substring(0, srcToFileName.indexOf("."));
					
					
					
					if (menu.equals(menuCode)) {
						logger.info("table页面参数:{}",JSON.toJSONString(bootstrapTable));
						logger.info("搜索框信息页面参数:{}" , JSON.toJSONString(smmap));
						logger.info("按钮信息页面参数:{}" , JSON.toJSONString(btninfo));
						logger.info("弹出窗体信息页面参数{}:" , JSON.toJSONString(btnwind));
						logger.info("main页面参数{}:" ,JSON.toJSONString(resultmp));
						logger.info("结束预览{}页面", commText);
						
						//删掉
						//SysUserUtils.getSession().removeAttribute(SessionKeys.COMMISSION_CODE_KEY);;
						return "ptsystems/" + tableName.toLowerCase() + "/" + htl;
					}
		}
		return null;
	}

	
	@Resource
	private PtSystemsMenuService ptSystemsMenuService;

	@Resource
	private BasMettingService basMettingService;


	/**
	 * 
	 * 
	 * @param request
	 * @param model
	 * @param menuCode 菜单编码
	 * @return
	 */
	public Map<String, String>  toIndex(Map<String, String> resultmp,String menuCode) {
		//String toUrl = "ptsystems/" + SysUserUtils.getCurrentCommissionCode().toLowerCase() + "/";
		if (StringUtils.equals(GenConstant.SPECIAL_MENU_CODE, menuCode)) {// 若当前菜单Code传递过来的是特殊Code
			Map<String, Object> findCurrentMeeting = basMettingService.findCurrentMeeting();// 取得当前会议
			if (findCurrentMeeting != null && !findCurrentMeeting.isEmpty()) {// 若存在当前会议
				//menuCode = GenConstant.SPECIAL_MENU_CODE_CHANGE;// 重新指定跳转页面
				/*model.addAttribute(GenConstant.METTING_NAME, findCurrentMeeting.get("title"));// 设定会议名称
				model.addAttribute("mettingId", findCurrentMeeting.get("id"));*/
				resultmp.put(GenConstant.METTING_NAME, findCurrentMeeting.get("specMettingTitle").toString());
				resultmp.put("mettingId", findCurrentMeeting.get("id").toString());
			}
			Map<String, Object> findPrepaMeeting = basMettingService.findPrepatOrMeeting();
			if (findPrepaMeeting != null) {// 若存在准备会议
				//menuCode = GenConstant.SPECIAL_MENU_CODE_CHANGE;// 重新指定跳转页面
				/*model.addAttribute(GenConstant.METTING_NAME, findPrepaMeeting.get("title"));// 设定会议名称
				model.addAttribute("mettingId", findPrepaMeeting.get("id"));*/
				resultmp.put(GenConstant.METTING_NAME, findCurrentMeeting.get("specMettingTitle").toString());
				resultmp.put("mettingId", findCurrentMeeting.get("id").toString());
			}
			
		}
		if (StringUtils.equals(GenConstant.SPECIAL_MENU_CODE_VOTE, menuCode)) {
			Map<String, Object> findCurrentMeeting = basMettingService.findCurrentMeeting();// 取得当前会议
			if (findCurrentMeeting != null && !findCurrentMeeting.isEmpty()) {// 若存在当前会议
			/*	model.addAttribute(GenConstant.METTING_NAME, findCurrentMeeting.get("title"));// 设定会议名称
				model.addAttribute("mettingId", findCurrentMeeting.get("id"));
				model.addAttribute(GenConstant.METTING_CODE, findCurrentMeeting.get("code"));
				template.binding(GenConstant.METTING_NAME, findCurrentMeeting.get("title"));
				template.binding("mettingId", findCurrentMeeting);
				template.binding(GenConstant.METTING_CODE, findCurrentMeeting.get("code"));*/
				
				resultmp.put(GenConstant.METTING_NAME, findCurrentMeeting.get("specMettingTitle").toString());
				resultmp.put("mettingId", findCurrentMeeting.get("id").toString());
				resultmp.put(GenConstant.METTING_CODE, findCurrentMeeting.get("specMettingCode").toString());
			}
		}
		//toUrl += menuCode;
		return resultmp;
	}

}
