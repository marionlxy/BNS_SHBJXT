package com.krm.voteplateform.web.menuwinconf.controller;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.web.basProject.service.BasMettingService;
import com.krm.voteplateform.web.bascodegroup.service.BasCodeGroupService;
import com.krm.voteplateform.web.constants.SysContants;
import com.krm.voteplateform.web.menuwinconf.model.BasMenuWinConf;
import com.krm.voteplateform.web.menuwinconf.service.BasMenuWinConfService;
import com.krm.voteplateform.web.menuwinconf.service.BasProAttchService;
import com.krm.voteplateform.web.menwingrouconf.model.BasMenWinGrouConf;
import com.krm.voteplateform.web.sys.service.SysDicExpService;
import com.krm.voteplateform.web.sys.service.SysDicService;
import com.krm.voteplateform.web.util.SysUserUtils;


/**
 * 窗体配置类
 *
 */
@Controller
@RequestMapping("pt/menuwinconf")
public class BasMenuWinConfController extends BaseController {
	
	@Autowired
	private BasMenuWinConfService basMenuWinConfService;
	
	@Autowired
	private BasCodeGroupService basCodeGroupService;
	
	
	@Autowired
	private SysDicService sysDicService;
	
	@Autowired
	private SysDicExpService sysDicExpService;
	
	@Resource
	private BeetlGroupUtilConfiguration beetlConfig;
	
	@Resource
	private BasMettingService basMettingService;
	
	@Resource
	private BasProAttchService fileUploadService;
	/**
	 * 查询
	 * @param params 参数列表
	 * @param page
	 */
	@RequestMapping(value = "/listBasMenuWinConf")
	public ModelAndView loadBasMenuWinConfList(HttpServletRequest req,@RequestParam(value="page",defaultValue="1",required=false) int page){
		ModelAndView mv = new ModelAndView("/menuwinconf/listBasMenuWinConf");
		Pagination<BasMenuWinConf> paging = new Pagination<BasMenuWinConf>(10, page);
		Map<String,Object> params = new HashMap<String, Object>();
		//basMenuWinConfService.loadBasMenuWinConfList(paging,params);
		mv.addObject("paging",paging);
		return mv;
	}
	
	/**
	 * 显示详情
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/showBasMenuWinConf")
	public ModelAndView loadbasMenuWinConf(String id){
		ModelAndView mv = new ModelAndView("/menuwinconf/showBasMenuWinConf");
		BasMenuWinConf basMenuWinConf = basMenuWinConfService.loadById(id);
		mv.addObject("basMenuWinConf",basMenuWinConf);
		return mv;
	}
	
	@RequestMapping(value="/toAddBasMenuWinConf")
	public String toAddBasMenuWinConf(HttpServletRequest request,Model model){
		String functionCode=request.getParameter("functionCode");
		model.addAttribute("functionCode", functionCode);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("functionCode", functionCode);
		model.addAttribute("groupOrder", basMenuWinConfService.getGroupOrder(params).get("grouporder").toString());
		return "plateform/menuwinconf/windgroup_add";
	}
	/**
	 * 
	 * toAddBasMenuWinDetail:(跳转到详细增加页面). <br/>
	 * @author lixy
	 * @param request
	 * @param model
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="/toAddWinDetail")
	public String toAddBasMenuWinDetail(HttpServletRequest request,Model model){
		logger.info("开始查询添加明细项参数");
		String functionCode=request.getParameter("functionCode");
		model.addAttribute("functionCode", functionCode);
		List<Map<String, Object>> findgrouplist=basMenuWinConfService.findSelectGroupList(functionCode);
		List<Map<String, Object>> findSelectDicExpList = sysDicExpService.findSelectDicExpList();
		List<Map<String, Object>> findcodegrouplist=basCodeGroupService.findBasCodeGroupList();
		model.addAttribute("findgrouplist", findgrouplist);
		model.addAttribute("findSelectDicExpList", findSelectDicExpList);
		model.addAttribute("findcodegrouplist", findcodegrouplist);
		String groupPre = "";
		String orderCode = "";
		if(findgrouplist != null && findgrouplist.size() > 0){
			groupPre = findgrouplist.get(0).get("groupid").toString().substring(0,3);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("functionCode", functionCode);
			params.put("groupId", groupPre);
			List<Map<String, Object>> orders = basMenuWinConfService.getOrder(params);
			if(orders.size() == 0){
				orderCode = "01";
				model.addAttribute("order", orderCode);
			}if(orders.size() == 1){
				if(orders.get(0) == null){
					orderCode = "01";
					model.addAttribute("order", orderCode);
				}else{
					orderCode = orders.get(0).get("ordercode").toString();
					model.addAttribute("order", orderCode.substring(2,4));
				}
			}
			if(orders.size() == 2){
				orderCode = orders.get(0).get("ordercode").toString();
				if(orderCode.length() == 3){
					orderCode = orderCode + "0";
				}if(orderCode.length() == 2){
					orderCode = orderCode + "00";
				}
				model.addAttribute("order", orderCode.substring(2,4));
			}
			model.addAttribute("realCode", orderCode);
		}
		logger.info("结束查询添加明细项参数");
		return "plateform/menuwinconf/windetail_add";
	}
	
	@RequestMapping(value="/toAddWindGather")
	public String toAddWindGather(HttpServletRequest request,Model model){
		String functionCode=request.getParameter("functionCode");
		String resid=request.getParameter("resid");
		model.addAttribute("functionCode", functionCode);
		model.addAttribute("resid", resid);
		List<Map<String, Object>> findSysList = sysDicService.findSelectSysList(resid,functionCode);
		List<Map<String, Object>> findgrouplist=basMenuWinConfService.findSelectGroupList(functionCode);
		List<Map<String, Object>> findcodegrouplist=basCodeGroupService.findBasCodeGroupList();
		model.addAttribute("findSysList", findSysList);
		model.addAttribute("findgrouplist", findgrouplist);
		model.addAttribute("findcodegrouplist", findcodegrouplist);
		String groupPre = "";
		String orderCode = "";
		if(findgrouplist != null && findgrouplist.size() > 0){
			groupPre = findgrouplist.get(0).get("groupid").toString().substring(0,3);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("functionCode", functionCode);
			params.put("groupId", groupPre);
			List<Map<String, Object>> orders = basMenuWinConfService.getOrder(params);
			if(orders.size() == 0){
				orderCode = "01";
				model.addAttribute("order", orderCode);
			}if(orders.size() == 1){
				if(orders.get(0) == null){
					orderCode = "01";
					model.addAttribute("order", orderCode);
				}else{
					orderCode = orders.get(0).get("ordercode").toString();
					model.addAttribute("order", orderCode.substring(2,4));
				}
			}
			if(orders.size() == 2){
				orderCode = orders.get(0).get("ordercode").toString();
				if(orderCode.length() == 3){
					orderCode = orderCode + "0";
				}if(orderCode.length() == 2){
					orderCode = orderCode + "00";
				}
				model.addAttribute("order", orderCode.substring(2,4));
			}
			model.addAttribute("realCode", orderCode);
		}
		return "plateform/menuwinconf/windgather_add";
	}
	
	@RequestMapping(value="/getOrder",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getOrder(@RequestParam Map<String, Object> params,Model model){
		List<Map<String, Object>> orders = basMenuWinConfService.getOrder(params);
		if(orders.size() == 1){
			Map<String, Object> map = orders.get(0);
			map.put("realcode", map.get("ordercode").toString());//真实的值
			map.put("ordercode", map.get("ordercode").toString().substring(2,4));//显示的值
			return map;
		}
		if(orders.size() == 2){
			Map<String, Object> map = orders.get(0);
			String orderCode = orders.get(0).get("ordercode").toString();
			if(orderCode.length() == 3){
				orderCode = orderCode + "0";
			}if(orderCode.length() == 2){
				orderCode = orderCode + "00";
			}
			map.put("realcode", orderCode);//真实的值
			map.put("ordercode", orderCode.substring(2,4));//显示的值
			return map;
		}
		return null;
	}
	/**
	 * 查询扩展明细
	 * @param basMenuWinConf
	 * @return
	 */
	@RequestMapping(value = "/findExpGroupList",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> findExpGroupList(HttpServletRequest request){
		logger.info("开始查询扩展明细下拉");
		String id=request.getParameter("id");
		List<Map<String, Object>> lstObj = sysDicExpService.findExpGroupList(id);
		List<Map<String, Object>> findcodegrouplist=basCodeGroupService.findBasCodeGroupList();
				List<Map<String, Object>> lstMap= new ArrayList<Map<String,Object>>();
		//（遍历）循环遍历List<HashMap<String, Object>>第二种方式(重新复制)
		for(Map<String,Object> map:lstObj){
			 Iterator<Entry<String, Object>> itor = map.entrySet().iterator();  
				Map<String, Object> mp=Maps.newHashMap();
		        while (itor.hasNext()) { 
		               Entry<String, Object> e = itor.next(); 
		               mp.put(e.getKey(), e.getValue());
		        } 
		        mp.put("children", findcodegrouplist); 
		        lstMap.add(mp);
		}
		logger.info("结束查询扩展明细下拉");
		return lstMap;
	}
	
	/**
	 * 保存详情
	 * @param basMenuWinConf
	 * @return
	 */
	@RequestMapping(value = "/saveBasMenuWinConf",method=RequestMethod.POST)
	@ResponseBody
	public Result saveBasMenuWinConf(HttpServletRequest request){
		logger.info("开始保存分组窗体");
		BasMenuWinConf basMenuWinConf=new BasMenuWinConf();
		String functionCode = request.getParameter("functionCode");
		basMenuWinConf.setFunctionCode(functionCode);
		String type = request.getParameter("type");
		basMenuWinConf.setType(type);
		
		//basMenuWinConf.setGroupid(type);特殊处理
		String basicFlag=StringUtils.isEmpty(request.getParameter("trueBasicFlag"))?"1":request.getParameter("trueBasicFlag");
		basMenuWinConf.setBasicFlag(basicFlag);
		basMenuWinConf.setName(request.getParameter("name"));
		String useFlag=StringUtils.isEmpty(request.getParameter("useFlag"))?"1":"0";
		basMenuWinConf.setUseFlag(useFlag);
		String order = request.getParameter("orderCode");
		order += ".00";
		basMenuWinConf.setOrderCode(order);
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("functionCode", functionCode);
		map.put("type", type);
		map.put("basicFlag", basicFlag);
		Boolean flag = basMenuWinConfService.saveBasMenuWinConf(basMenuWinConf,map);
		if(flag){
			return Result.successResult();
		}
		logger.info("结束保存分组窗体");
		return Result.errorResult();
	}
	
	/**
	 * 保存详情
	 * @param basMenuWinConf
	 * @return
	 */
	@RequestMapping(value = "/saveWindGather",method=RequestMethod.POST)
	@ResponseBody
	public Result saveWindGather(HttpServletRequest request,@RequestParam Map<String, Object> params){
		logger.info("开始保存采集框窗体");
		BasMenuWinConf basMenuWinConf=new BasMenuWinConf();
		basMenuWinConf.setFunctionCode(request.getParameter("functionCode"));
		String type = request.getParameter("type");
		basMenuWinConf.setType(type);
		basMenuWinConf.setAlterFlag(getPara("alterFlag","0"));
		basMenuWinConf.setName(request.getParameter("name"));
		basMenuWinConf.setBindFiled(request.getParameter("bindFiled"));
		String groupid = request.getParameter("groupid");
		basMenuWinConf.setGroupid(groupid);//需特殊处理
		String nullFlag = StringUtils.isEmpty(request.getParameter("nullFlag"))?"1":"0";
		basMenuWinConf.setNullFlag(nullFlag);
		String collectType = request.getParameter("collectType");
		
		basMenuWinConf.setCollectType(collectType);
		if("1".equalsIgnoreCase(collectType)){
			basMenuWinConf.setDatasource(request.getParameter("dataSource"));
		}else{
			basMenuWinConf.setDatasource(null);
		}
		String useFlag=StringUtils.isEmpty(request.getParameter("useFlag"))?"1":"0";
		basMenuWinConf.setOrderCode(request.getParameter("orderCode"));
		basMenuWinConf.setUseFlag(useFlag);
		Boolean flag = basMenuWinConfService.saveBasMenuGatherConf(basMenuWinConf);
		if(flag){
			return Result.successResult();
		}
		logger.info("结束保存采集框窗体");
		return Result.errorResult();
	}
	
	/**
	 * 保存详情
	 * @param basMenuWinConf
	 * @return
	 */
	@RequestMapping(value = "/saveWindDetail",method=RequestMethod.POST)
	@ResponseBody
	public Result saveWindDetail(HttpServletRequest request){
		logger.info("开始保存明细框窗体");
		BasMenuWinConf basMenuWinConf=new BasMenuWinConf();
		//String uuid1 = UUIDGenerator.getUUID();
		//basMenuWinConf.setId(uuid1);

		basMenuWinConf.setFunctionCode(getPara("functionCode"));
		String type = getPara("type");
		basMenuWinConf.setType(type);
		basMenuWinConf.setName(getPara("name"));
		basMenuWinConf.setBindFiled(getPara("bindFiled"));//为空
		String groupid = getPara("groupid");
		basMenuWinConf.setGroupid(groupid);
		String useFlag=StringUtils.isEmpty(getPara("useFlag"))?"1":"0";
		basMenuWinConf.setUseFlag(useFlag);
//		String nullFlag = StringUtils.isEmpty(getPara("nullFlag"))?"1":"0";
		basMenuWinConf.setNullFlag(null);
		//明细扩展对象
		String expGroupObjId = getPara("expGroupObj");
		//项目扩展明细表主键(有多个需特殊处理)
		 List<BasMenWinGrouConf> lst=new ArrayList<BasMenWinGrouConf>();
		String[] chkArrs = request.getParameterValues("chk");
		for (String chkid : chkArrs) {
			//菜单窗体拓展明细分组配置表
			BasMenWinGrouConf basMenWinGrouConf = new BasMenWinGrouConf(); 
			//String uuid2 = UUIDGenerator.getUUID();
			//basMenWinGrouConf.setId(uuid2);
			basMenWinGrouConf.setBxgId(expGroupObjId);//明细扩展对象的值拓展明细分组主键
			basMenWinGrouConf.setBredId(chkid);
			String isMust = getPara("isMust"+chkid);
			basMenWinGrouConf.setNullFlag(isMust);//selectData
			String alterFlag = getPara("isAlert"+chkid);
			basMenWinGrouConf.setAlterFlag(alterFlag);
			String selectDataType = getPara("selectData"+chkid);
			if("1".equalsIgnoreCase(selectDataType)){//下拉框类型
				basMenWinGrouConf.setCollectType(selectDataType);
				String selectDataSorce = getPara("selectDataSorce"+chkid);
				basMenWinGrouConf.setDatasource(selectDataSorce);
			}else{
				basMenWinGrouConf.setCollectType(selectDataType);
				basMenuWinConf.setDatasource(null);
			}
			String useFlag2 = StringUtils.isEmpty(getPara("useFlag"+chkid))?"1":"0";
			basMenWinGrouConf.setUseFlag(useFlag2);
	
			lst.add(basMenWinGrouConf);
		}
		basMenuWinConf.setOrderCode(request.getParameter("orderCode"));
		Boolean flag = basMenuWinConfService.saveBasMenuDetailConf(basMenuWinConf,lst,chkArrs);
		if(flag){
			return Result.successResult();
		}
		logger.info("结束保存明细框窗体");
		return Result.errorResult();
	}
	
	
	/**
	 * @验证名称是否重复
	 * @author zhangYuhai
	 * @param request
	 * @param documentStrIds
	 * @return
	 */
	@RequestMapping("valiGroupName")
	@ResponseBody
	public Map<String, String> valiGroupName(HttpServletRequest request) {
		
		String key=request.getParameter("name");
		String value=request.getParameter("param").trim();
		String selectText=request.getParameter("selectText");
		//用在有下拉框的地方
		if(StringUtils.isEmpty(selectText)){
			selectText=value;
		}else{
			try {
				selectText=java.net.URLDecoder.decode(selectText,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String functionCode=request.getParameter("functionCode");
		String type=request.getParameter("type");
		//String basicFlag=request.getParameter("basicFlag");
		Map<String,Object> paramap=new HashMap<String,Object>();
		paramap.put(key, value);
		paramap.put("functionCode", functionCode);
		paramap.put("type", type);
	
		logger.info("开始验证名称{}信息" , paramap);
		
		boolean flag = basMenuWinConfService.selectName(paramap);
		  Map<String , String> map = new HashMap<String, String>();     
		  if (flag) {  
		        map.put("info", "字段【"+selectText+"】已经被绑定");  
		        map.put("status", "n");  
		    }else {  
		        map.put("info", "该名称可用");  
		        map.put("status", "y");  
		    }  
		  logger.info("结束验证名称{}信息",value);
		    return map;  	
	}
	
	@RequestMapping("valiGroupBasicFlag")
	@ResponseBody
	public Result valiGroupBasicFlag(HttpServletRequest request) {
		 logger.info("开始基本项验证{}");
		String basicFlag="0";//基本项
		String functionCode=request.getParameter("functionCode");
		String type=request.getParameter("type");
		//String basicFlag=request.getParameter("basicFlag");
		Map<String,Object> paramap=new HashMap<String,Object>();
		paramap.put("basicFlag", basicFlag);
		paramap.put("functionCode", functionCode);
		paramap.put("type", type);
		logger.info("开始基本项验证{}信息" , paramap);
		boolean flag = basMenuWinConfService.selectName(paramap);
		Result result = Result.successResult();
		  if (flag){  
			  	result=Result.successResult();
			  	result.setMsg("已经存在基本项！是否覆盖先前分组基本项");
		  }else{
			  result=Result.errorResult();
			  result.setMsg("还未存在基本项！是否指定当前分组为基本项");
		  }
		  logger.info("结束基本项验证{}",basicFlag);
		    return result;  	
	}
	
	@RequestMapping(value="/toEditBasMenuWinConf")
	public ModelAndView toEditBasMenuWinConf(String id){
		ModelAndView mv = new ModelAndView("/menuwinconf/editBasMenuWinConf");
		BasMenuWinConf basMenuWinConf= basMenuWinConfService.loadById(id);
		mv.addObject("basMenuWinConf",basMenuWinConf);
		return mv;
	}
	
	/**
	 * 保存修改的
	 * @param BasMenuWinConf
	 * @return
	 */
	@RequestMapping(value = "/updateBasMenuWinConf",method=RequestMethod.POST)
	public ModelAndView updateBasMenuWinConf(BasMenuWinConf basMenuWinConf){
		ModelAndView mv = new ModelAndView("redirect:/menuwinconf/listBasMenuWinConf");
		basMenuWinConfService.updateBasMenuWinConf(basMenuWinConf);
		return mv;
	}
	/**
	 * 删除
	 * @param BasMenuWinConf
	 * @return
	 */
	@RequestMapping(value = "/removeBasMenuWinConf",method=RequestMethod.POST)
	public ModelAndView removeBasMenuWinConf(HttpServletRequest req,BasMenuWinConf basMenuWinConf){
		ModelAndView mv = new ModelAndView("redirect:/menuwinconf/listBasMenuWinConf");
		basMenuWinConfService.removeBasMenuWinConf(basMenuWinConf);
		return mv;
	}
	
	@RequestMapping("tomenuWin")
	public String tomenuWin(String resid,String functionCode,Model model){
		logger.info("进入待审项目新建窗体页面");
		model.addAttribute("functionCode", functionCode);
		model.addAttribute("resid", resid);
		return "plateform/menuwinconf/menuWinConf_list";
	}
	
	@RequestMapping("listMenuWinConf")
	@ResponseBody
	public List<Map<String, Object>> listMenuWinConf(String functionCode){
		logger.info("开始查询basmenuwinconf表");
		List<Map<String, Object>> list =basMenuWinConfService.getMenuWinAll(functionCode);
		logger.info("查询basmenuwinconf表完毕");
		return list;
		
	}
	
	@RequestMapping("deleMenuWinById")
	@ResponseBody
	public Result deleMenuWinById(String id) {
		logger.info("开始删除bas_menu_list_conf表" + id);
		basMenuWinConfService.deleteMById(id);
		logger.info("结束删除bas_menu_list_conf表" + id);
		return Result.successResult();
	}
	
	@RequestMapping("toSetWinSize")
	public String toSetWinSize(HttpServletRequest request,Model model){
		String functionCode=request.getParameter("functionCode");
		model.addAttribute("functionCode", functionCode);
		Map<String, Object> params = Maps.newHashMap();
		params.put("functionCode", functionCode);
		params.put("type", 1);
		Map<String, Object> winsize = basMenuWinConfService.selectWinSize(params);
		if(winsize != null){
			String[] weiheight = winsize.get("val").toString().split(";");
			model.addAttribute("height", weiheight[0]);
			model.addAttribute("width", weiheight[1]);
		}
		return "plateform/menuwinconf/winsize_set";
	}
	
	/**
	 * toEditGroup:(跳转编辑分组页面). <br/>
	 * @author huangyuantao
	 * @param request
	 * @param model
	 */
	@RequestMapping(value="/toEditGroup")
	public String toEditGroup(HttpServletRequest request,Model model){
		String id = getPara("id");
		Map<String,Object> list = basMenuWinConfService.findGroupByID(id);
		String orderCode = list.get("orderCode").toString();
		model.addAttribute("realCode", orderCode.substring(0,1));
		if(!orderCode.equals("")){
			list.put("orderCode", orderCode.substring(0,1));
		}
		model.addAttribute("list", list);
		return "plateform/menuwinconf/windgroup_edit";
	}
	
	
	/**
	 * toEditGroup:(跳转编辑明细项页面). <br/>
	 * @author huangyuantao
	 * @param request
	 * @param model
	 */
	@RequestMapping(value="/toEditWinDetail")
	public String toEditWinDetail(HttpServletRequest request,Model model){
		String id = getPara("id");
		String functionCode=getPara("functionCode");
		model.addAttribute("functionCode", functionCode);
		List<Map<String, Object>> findgrouplist=basMenuWinConfService.findSelectGroupList(functionCode);//分组下拉框
		List<Map<String, Object>> findSelectDicExpList = sysDicExpService.findSelectDicExpList();//明细对象下拉框
		model.addAttribute("findgrouplist", findgrouplist);
		model.addAttribute("findSelectDicExpList", findSelectDicExpList);
		
		//获得basMenuWinConfService对象
		Map<String,Object> map = basMenuWinConfService.getWinDetailById(id);
		
		//指定groupid的分组id
		String groupid = (String) map.get("groupid");
		String group = groupid.substring(0,3)+"000";
		map.put("groupid", group);
		
		String bmwcId = (String) map.get("id");
		
		//获得basMenWinGrouConf对象集合
		List<Map<String,Object>> list2 = basMenuWinConfService.getBasMenWinGroupConf(bmwcId);
		String bxgId = (String) list2.get(0).get("bxgId");
		model.addAttribute("bxgId", bxgId);
		model.addAttribute("list2", list2);
		
		//获得basExpandGroup对象
		Map<String,Object> basExpandGroup =sysDicExpService.getBasExpandGroupById(bxgId);
		String groupName =(String) basExpandGroup.get("groupName");
		model.addAttribute("groupName", groupName);
		//String bredId = (String) map2.get("bredId");
		
		//获得组id下所有的字段
		List<Map<String, Object>> lstObj = sysDicExpService.findExpGroupList(bxgId);
		List<Map<String, Object>> findcodegrouplist=basCodeGroupService.findBasCodeGroupList();
		List<Map<String, Object>> lstMap= new ArrayList<Map<String,Object>>();
		//（遍历）循环遍历List<HashMap<String, Object>>第二种方式(重新复制)
		for(Map<String,Object> map3:lstObj){
			 Iterator<Entry<String, Object>> itor = map3.entrySet().iterator();  
				Map<String, Object> mp=Maps.newHashMap();
		        while (itor.hasNext()) { 
		               Entry<String, Object> e = itor.next(); 
		               mp.put(e.getKey(), e.getValue());
		        } 
		        mp.put("children", findcodegrouplist); 
		        lstMap.add(mp);
		}
		String orderCode = map.get("orderCode").toString();
		map.put("orderCode", orderCode.subSequence(2, 4));
		map.put("realCode", orderCode);
		model.addAttribute("map", map);
		model.addAttribute("lstObj", lstObj);
		
		return "plateform/menuwinconf/windetail_edit";
	}
	
	/**
	 * toEditGroup:(跳转编辑采集框页面). <br/>
	 * @author huangYuantao
	 * @param request
	 * @param model
	 */
	@RequestMapping(value="/toEditWindGather")
	public String toEditWindGather(HttpServletRequest request,Model model){
		String id =getPara("id");
		String functionCode = getPara("functionCode");
		model.addAttribute("functionCode", functionCode);
		String resid = getPara("resid");
		List<Map<String, Object>> findSysList = sysDicService.findSelectSysList(resid,functionCode);
		List<Map<String, Object>> findgrouplist=basMenuWinConfService.findSelectGroupList(functionCode);
		List<Map<String, Object>> findcodegrouplist=basCodeGroupService.findBasCodeGroupList();
		Map<String, Object> list = basMenuWinConfService.findWindGatherById(id);
		//取得组id
		String groupid = (String) list.get("groupid");
		String group = groupid.substring(0,3)+"000";
		list.put("groupid", group);
		String orderCode = list.get("orderCode").toString();
		if(!orderCode.equals("")){
			list.put("realCode", orderCode);
			list.put("orderCode", orderCode.substring(2,4));
		}
		model.addAttribute("list", list);
		model.addAttribute("findSysList", findSysList);
		model.addAttribute("findgrouplist", findgrouplist);
		model.addAttribute("findcodegrouplist", findcodegrouplist);
		return "plateform/menuwinconf/windgather_edit";
	}
	
	@RequestMapping("saveGroupUpdate")
	@ResponseBody
	public Result saveGroupUpdate(HttpServletRequest request){
		logger.info("开始保存分组窗体");
		BasMenuWinConf basMenuWinConf=new BasMenuWinConf();
		basMenuWinConf.setId(getPara("gId"));
		basMenuWinConf.setName(getPara("name"));
		//String useFlag=StringUtils.isEmpty(request.getParameter("useFlag"))?"1":"0";
		String useFlag=getPara("useFlag","1");
		basMenuWinConf.setUseFlag(useFlag);
		String basicFlag=getPara("basicFlag","1");
		basMenuWinConf.setBasicFlag(basicFlag);
		String orderCode = request.getParameter("orderCode");
		orderCode = orderCode + ".00";
		basMenuWinConf.setOrderCode(orderCode);
		String functionCode = request.getParameter("functionCode");
		String groupId = request.getParameter("groupId");
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("functionCode", functionCode);
		map.put("type", 0);
		map.put("basicFlag", basicFlag);
		try {
			Boolean flag = basMenuWinConfService.saveGroupUpdate(basMenuWinConf,map);
			if(flag){
				//组序号更改时，会自动将输入的序号和原来的序号对调，例如：将序号1改为3，系统会自动将库里为3的序号改为1，实现对换
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("functionCode", functionCode);
				params.put("orderCode", orderCode.substring(0, 2));
				params.put("realCode", request.getParameter("realCode"));
				int i = basMenuWinConfService.changeGroupOrder(params);
				if(i > 0){
					params.put("groupId", groupId.substring(0, 3));
					params.put("orderCode", orderCode.substring(0, 1));
					int j = basMenuWinConfService.updateOrdersByGroup(params);
					if(j>0){
						return Result.successResult();
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("结束保存分组窗体");
		return Result.errorResult();
	}
	
	@RequestMapping("saveWindGatherUpdate")
	@ResponseBody
	public Result saveWindGatherUpdate(HttpServletRequest request){
		logger.info("开始保存分组窗体");
		
		BasMenuWinConf basMenuWinConf=new BasMenuWinConf();
		
		String id=getPara("id");
		String groupid = getPara("groupid");//获得组id，需对后三位做处理
		String name =getPara("name");
		String nullFlag=getPara("nullFlag","0");
		String collectType=getPara("collectType");
		String datasource=getPara("dataSource");
		String useFlag=getPara("useFlag","0");
		String alterFlag=getPara("alterFlag","0");
		
		basMenuWinConf.setId(id);
		basMenuWinConf.setGroupid(groupid);
		basMenuWinConf.setName(name );
		basMenuWinConf.setNullFlag(nullFlag);
		basMenuWinConf.setCollectType(collectType);
		basMenuWinConf.setDatasource(datasource);
		basMenuWinConf.setUseFlag(useFlag);
		basMenuWinConf.setAlterFlag(alterFlag);
		String orderCode = request.getParameter("orderCode");
		if(orderCode.length() == 1){
			orderCode = "0" + orderCode;
		}
		String realCode = request.getParameter("realCode");
		basMenuWinConf.setOrderCode(realCode.substring(0, 2)+orderCode);
		Boolean flag = basMenuWinConfService.saveWindGatherUpdate(basMenuWinConf);
		if(flag){
			return Result.successResult();
		}
		logger.info("结束保存分组窗体");
		return Result.errorResult();
	}
	
	@RequestMapping(value = "/findGroupList",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> findGroupList(HttpServletRequest request){
		logger.info("开始查询扩展明细下拉");
		String id=request.getParameter("id");
		List<Map<String, Object>> lstObj = sysDicExpService.findGroupList(id);
		List<Map<String, Object>> findcodegrouplist=basCodeGroupService.findBasCodeGroupList();
				List<Map<String, Object>> lstMap= new ArrayList<Map<String,Object>>();
		//（遍历）循环遍历List<HashMap<String, Object>>第二种方式(重新复制)
		for(Map<String,Object> map:lstObj){
			 Iterator<Entry<String, Object>> itor = map.entrySet().iterator();  
				Map<String, Object> mp=Maps.newHashMap();
		        while (itor.hasNext()) { 
		               Entry<String, Object> e = itor.next(); 
		               mp.put(e.getKey(), e.getValue());
		        } 
		        mp.put("children", findcodegrouplist); 
		        lstMap.add(mp);
		}
		logger.info("结束查询扩展明细下拉");
		return lstMap;
	}
	
	
	@RequestMapping(value = "/saveWindDetailUpdate",method=RequestMethod.POST)
	@ResponseBody
	public Result saveWindDetailUpdate(HttpServletRequest request){
		logger.info("开始保存明细框窗体");
		BasMenuWinConf basMenuWinConf=new BasMenuWinConf();
		String id =getPara("bmwcId");
		basMenuWinConf.setId(id);
		String type = getPara("type");
		basMenuWinConf.setType(type);
		basMenuWinConf.setName(getPara("name"));
		basMenuWinConf.setBindFiled(getPara("bindFiled"));//为空
		String groupid = getPara("groupid");
		basMenuWinConf.setGroupid(groupid);
		//如果组id未发生改变，则设为空，让字段在更新的时候保持不变
		if (basMenuWinConf.getGroupid().equals(getPara("oldGroupId"))) {
			basMenuWinConf.setGroupid(null);
		}else {
			basMenuWinConf.setGroupid(groupid);
		}
		String useFlag=getPara("useFlag","1");
		basMenuWinConf.setUseFlag(useFlag);
		basMenuWinConf.setNullFlag("");
		//明细扩展对象
		//String expGroupObjId = getPara("expGroupObj");
		//项目扩展明细表主键(有多个需特殊处理)
		 List<BasMenWinGrouConf> lst=new ArrayList<BasMenWinGrouConf>();
		String[] chkArrs = request.getParameterValues("chk");
		for (String chkid : chkArrs) {
			//菜单窗体拓展明细分组配置表
			BasMenWinGrouConf basMenWinGrouConf = new BasMenWinGrouConf(); 
			//String uuid2 = UUIDGenerator.getUUID();
			//basMenWinGrouConf.setId(uuid2);
			//basMenWinGrouConf.setBxgId(expGroupObjId);//明细扩展对象的值拓展明细分组主键
			basMenWinGrouConf.setId(chkid);
			//basMenWinGrouConf.setBredId(chkid);
			String isMust = getPara("isMust"+chkid);
			basMenWinGrouConf.setNullFlag(isMust);//selectData
			String alterFlag = getPara("isAlert"+chkid);
			basMenWinGrouConf.setAlterFlag(alterFlag);;
			String selectDataType = getPara("selectData"+chkid);
			if("1".equalsIgnoreCase(selectDataType)){//下拉框类型
				basMenWinGrouConf.setCollectType(selectDataType);
				String selectDataSorce = getPara("selectDataSorce"+chkid);
				basMenWinGrouConf.setDatasource(selectDataSorce);
			}else{
				basMenWinGrouConf.setCollectType(selectDataType);
				basMenuWinConf.setDatasource(null);
			}
			String useFlag2 = StringUtils.isEmpty(getPara("useFlag"+chkid))?"1":"0";
			basMenWinGrouConf.setUseFlag(useFlag2);
	
			lst.add(basMenWinGrouConf);
		}
		String orderCode = request.getParameter("orderCode");
		if(orderCode.length() == 1){
			orderCode = "0" + orderCode;
		}
		String realCode = request.getParameter("realCode");
		basMenuWinConf.setOrderCode(realCode.substring(0, 2)+orderCode);
		
		Boolean flag = basMenuWinConfService.saveBasMenuDetailConfUpdate(basMenuWinConf,lst,chkArrs);
		if(flag){
			return Result.successResult();
		}
		logger.info("结束保存明细框窗体");
		return Result.errorResult();
	}

	@RequestMapping("previewWinConf")
	public String previewWinConf(@RequestParam Map<String, Object> params, Model model){
		logger.info("开始生成预览界面");
		String tableName = SysUserUtils.getCurrentCommissionCode().toLowerCase();
		String functionCode = params.get("functionCode").toString();
		List<Map<String, Object>> list =basMenuWinConfService.getWinConfList(params);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> result = basMenuWinConfService.getTempaletName(params);
		//如果没有绑定模板就不生成
		if(result != null){
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
			String temptateFileName = result == null?null:result.get("tempName").toString();
			GroupTemplate gt = beetlConfig.getGroupTemplate();
			Template template = gt.getTemplate(SysContants.TEMPLTE_PATH+temptateFileName);
			template.binding("rs", resultList);
			template.binding("functionCode", functionCode);
			String render = template.render();
			String toPath = SysContants.TEMPLTE_TO_PATH+SysUserUtils.getCurrentCommissionCode().toLowerCase()+File.separator+functionCode+".html";
			//删除文件
			FileUtils.deleteFile(toPath);
			FileUtils.writeFile(render, toPath);
			logger.info("结束生成预览界面");
		}
		Map<String,Object> updateObj = new HashMap<String,Object>();
		model.addAttribute("updateObj", updateObj);//传一个空的对象,以免预览时报错
		model.addAttribute("projectId", "");//传一个空的对象,以免预览时报错
		model.addAttribute("functionCode", functionCode);
		return "ptsystems/"+tableName+"/"+functionCode;
	}
	
	@RequestMapping("getExtDetDataList")
	@ResponseBody
	public List<Map<String,Object>> getExtDetDataList(@RequestParam Map<String, Object> params,Model model){
		List<Map<String,Object>> list = basMenuWinConfService.getExtDetDataList(params);
		return list;
	}
	
	/**
	 * 获取附件类型,根据项目id查看会议状态
	 * @param projectId
	 * @return
	 */
	@RequestMapping("getProAttchType")
	@ResponseBody
	public List<Map<String,Object>> getProAttchType(@RequestParam Map<String, Object> params){
		String projectId = params.get("projectId").toString();
		if(projectId != null && !projectId.equals("")){
			Map<String,Object> metting = basMettingService.findOrComplement(projectId);
			if(metting != null && metting.get("stateId").toString().equals("10000102")){
				params.put("msFlag", "1");
			}else{
				params.put("msFlag", "0");
			}
			return fileUploadService.getattchTypeList(params);
		}
		return new ArrayList<Map<String,Object>>();
	}
	
	/**
	 * 获取窗口大小，预览时获取
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getWinSize")
	@ResponseBody
	public Map<String,Object> getWinSize(HttpServletRequest request,Model model){
		Map<String,Object> result = Maps.newHashMap();
		String functionCode=request.getParameter("functionCode");
		Map<String, Object> params = Maps.newHashMap();
		params.put("functionCode", functionCode);
		params.put("type", 1);
		Map<String, Object> winsize = basMenuWinConfService.selectWinSize(params);
		if(winsize != null){
			String[] weiheight = winsize.get("val").toString().split(";");
			result.put("height", weiheight[0]);
			result.put("width", weiheight[1]);
		}else{
			result.put("height", 60);
			result.put("width", 50);
		}
		return result;
	}
	
	@RequestMapping("getTempalet")
	@ResponseBody
	public Map<String,Object> getTempalet(@RequestParam Map<String, Object> params,Model model){
		return basMenuWinConfService.getTempaletName(params);
	}
}
