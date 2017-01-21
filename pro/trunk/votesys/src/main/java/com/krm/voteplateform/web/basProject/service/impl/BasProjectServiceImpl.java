package com.krm.voteplateform.web.basProject.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.beetl.sql.core.db.KeyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.glue.GlueCodeFactory;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;
import com.krm.voteplateform.common.utils.BeanMapUtils;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.basProExDetail.dao.BasProExDetailMapper;
import com.krm.voteplateform.web.basProExDetail.model.BasProExDetail;
import com.krm.voteplateform.web.basProject.dao.BasMettingMapper;
import com.krm.voteplateform.web.basProject.dao.BasProjectMapper;
import com.krm.voteplateform.web.basProject.model.BasMetting;
import com.krm.voteplateform.web.basProject.model.BasProject;
import com.krm.voteplateform.web.basProject.service.BasProjectService;
import com.krm.voteplateform.web.ptvotematterconf.service.PtVoteMatterConfService;
import com.krm.voteplateform.web.util.SysUserUtils;


@Service("basProjectService")
public class BasProjectServiceImpl implements BasProjectService{
	
	private Logger logger = LoggerFactory.getLogger(BasProjectServiceImpl.class);

	
	@Resource
	private BasProjectMapper  basProjectMapper;
	
	@Resource
	private SQLManager sqlManager;
	
	@Resource
	private VoteSqlManager  voteSqlManager;
	
	@Resource
	private BasProExDetailMapper basProExDetailMapper;
	
	@Resource
	private PtVoteMatterConfService ptVoteMatterConfService;
	
	@Resource
	private BasMettingMapper basMettingMapper;
	
	

	@Override
	public List<Map<String, Object>> findNoAuditoItemList() {
		logger.info("开始执行未审项目列表查询");
		Map<String, String> map = Maps.newHashMap();
		logger.info("获取委员会编码");
//		SysUserUtils.setCurrentCommissionCode("CENT");
		logger.info("临时测试的SessionCode,这后要删除");
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		logger.info("结束执行未审项目列表查询");
		return basProjectMapper.findNoAuditoItemList(map);
	}

	//删除未审列表 信息
	@Override
	public Boolean removeBasProject(BasProject basProject) {
		logger.info("开始执行删除未审列表 信息");
		Boolean flag=false;
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basProject.setDelFlag("1");//删除
		basProject.setUpdateTime(nowTimestamp);
		basProject.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
		try {
			sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());
			int insert = sqlManager.updateTemplateById(basProject);
			logger.info("结束删除未审列表 信息 insert{}"+">0?true : false") ;
			flag=insert>0?true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	//查询已审列表信息
	@Override
	public List<Map<String, Object>> findAuditoItemList(Pagination<Map<String, Object>> page, BasProject basProject) {
		page.setOrderByField("reviewTime DESC ");
		logger.info("开始已审项目列表分页信息查询");
		return basProjectMapper.findAuditoItemList(basProject,page);
	}

	@Override
	public List<Map<String, Object>> exportData(BasProject basProject) {
		Map<String, Object> map = Maps.newHashMap();
		Map<String, Object> transBean2Map = BeanMapUtils.transBean2Map(basProject);
		map.putAll(transBean2Map);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		//map.put("basProject", basProject);
		return basProjectMapper.exportData(map);
	}

	//根据会议mettingId 查询出对应项目列表信息
	@Override
	public List<Map<String, Object>> findProjectByIdList(String mettingId) {
		logger.info("开始根据会议id=" + mettingId +"查询出项目列表信息" );
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("mettingId", mettingId);
		logger.info("结束开始根据会议id=" + mettingId +"查询出项目列表信息");
		return basProjectMapper.findProjectByIdList(map);
	}


	@Override
	public Boolean updateQuitMetting(String projectId) {
		logger.info("开始执行预备会议下id=" + projectId +"退出预备会议");
		Boolean  flag = false;
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("projectId", projectId);
		logger.info("逻辑执行退出会议{}" + projectId);
		try {
			flag = basProjectMapper.updateQuitMetById(map);
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("结束执行退出会议{}" + flag);
		return flag;
	}

	
	@Override
	public BasProject selectBasProject(Map<String, Object> param) {
		logger.info("获取当前委员的Code" + SysUserUtils.getCurrentCommissionCode());
		param.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		logger.info("结束修改前的反显数据{}" + param);
		return basProjectMapper.selectBasProjectAll(param);
	}

	@Override
	public List<Map<String, Object>> findCurMetting(String id) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("id", id);
		return basProjectMapper.findCurMetting(map);
	}

	@Override
	public Boolean saveItemList(Object objName) {
		Boolean falg = null;
		int result = voteSqlManager.insert(SysUserUtils.getCurrentCommissionCode(), objName);
		falg = result>0? true: false;
		return falg;
	}

	
	@Override
	public Integer updateByProjectId(String projectId, String mettingId) {
		List<BasProject> list = new ArrayList<BasProject>();
		BasProject  basProject= null;
		String specProCode = null;
		Integer result = 0;
		JSONArray ja = JSON.parseArray(projectId);
		Map<String, Object> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("id", mettingId);
		String a = basProjectMapper.getProjectOrder(map).get("projectOrder").toString();
		Integer projectOrder = Integer.parseInt(a);
		BasMetting basMetting = basMettingMapper.findParamsMetting(map);
		for (int i = 0; i < ja.size(); i++) {
			JSONObject ob = (JSONObject) ja.get(i);
			 if(ob.getString("projectId") !=null){
				 Map<String, Object> params = Maps.newHashMap();
				 params.put("projectId",  ob.getString("projectId").toString());
				 params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
				 basProject = basProjectMapper.findParamsList(params);
				 specProCode = basProject.getSpecProCode();
			 }
			 try{
					String id = SysUserUtils.getCurPtCommission().getPdsid();
					String proCodeType = SysUserUtils.getCurPtCommission().getProCodeType();
					if("2".equals(proCodeType)){ //proCodeType="1"或者"2"
						specProCode = GlueCodeFactory.glue(id, basProject,basMetting,2);
					}
				}catch(Exception e){
					logger.error("动态执行Java编码发生错误。",e);
					return result;
				}
				 
				basProject.setProjectOrder(projectOrder + i);//插入最大+1的项目序号
				 basProject.setConferenceId(mettingId);
				 basProject.setSpecProCode(specProCode);//得到特殊的项目编号
				 basProject.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
				 Timestamp nowTimestamp = DateUtils.getNowTimestamp();
				 basProject.setUpdateTime(nowTimestamp);
				 basProject.tableName=SysUserUtils.getCurrentCommissionCode();
				 list.add(basProject);
			}
			result =  voteSqlManager.updateByIdBatch(SysUserUtils.getCurrentCommissionCode(), list).length;
			return result;
	}


	//添加项目
	@Override
	public Boolean saveProjectByFunctionCode(BasProject basProject) {
		logger.info("开始插入项目数据");
		KeyHolder holder = new KeyHolder();
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basProject.setDelFlag("0");
		basProject.setProjectOrder(Integer.parseInt("0"));//默认给projectOrder 0;
		basProject.setCreateTime(nowTimestamp);
		basProject.setCreateName(SysUserUtils.getSessionLoginUser().getUserName());
		basProject.setCreateBy(SysUserUtils.getSessionLoginUser().getId());
		basProject.setProjectStateId("10200300");//未审状态
		basProject.setProjectStateName("未审");
		//String projectId = basProject.getProjectId();
		//int insert = voteSqlManager.insert(SysUserUtils.getCurrentCommissionCode(), basProject,true);
		//int insert = voteSqlManager.insert(SysUserUtils.getCurrentCommissionCode(), BasProject.class, basProject, holder);
		String uuid = UUIDGenerator.getUUID();
		basProject.setProjectId(uuid);
		sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());
		int insert = sqlManager.insert(BasProject.class, basProject);
		//int insert = sqlManager.insert(SysUserUtils.getCurrentCommissionCode(), basProject, holder);
		//String projectIds = basProject.getProjectId();
		logger.info("结束插入项目数据 insert{}");
		
		return insert>0?true:false;
	}

	//添加明细
	@Override
	public Boolean saveForDetailList(List<BasProExDetail> detailForm) {
		logger.info("开始循环插入明细 ");
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		for (BasProExDetail basProExDetail : detailForm) {
			basProExDetail.setCreateTime(nowTimestamp);
			basProExDetail.setCreateName(SysUserUtils.getSessionLoginUser().getUserName());
			basProExDetail.setDelFlag("0");
		}
		int[] result = voteSqlManager.insertBatch(SysUserUtils.getCurrentCommissionCode(), BasProExDetail.class, detailForm);
		logger.info("结束循环插入明细 results" );
		Boolean flag = result.length>0?true:false;
		//return basProExDetailMapper.insetBatchList(detailForm);
		return flag;
	}


	/**
	 * 通过id查询一条项目
	 */
	@Override
	public BasProject selectProjectById(String projectId) {
		sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());
		return sqlManager.unique(BasProject.class,projectId);
	}

	
	/**
	 * 修改基础项目信息
	 */
	@Override
	public Boolean updateProjectByFunctionCode(BasProject basProject) {
		logger.info("开始修改项目数据");
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basProject.setUpdateTime(nowTimestamp);
		basProject.setUpdateName(SysUserUtils.getSessionLoginUser().getUserName());
		basProject.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
		basProject.setDelFlag("0");
		basProject.setProjectStateId("10200300");//未审状态
		basProject.setProjectStateName("未审");
		int result = voteSqlManager.updateTemplateById(SysUserUtils.getCurrentCommissionCode(), basProject);
		Boolean flag = result>0? true:false;
		return flag;
	}


	@Override
	public Map<String, Object> findProjectOne(Map<String, String> map) {
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		String userId = SysUserUtils.getSessionLoginUser().getId();
		String porgId = SysUserUtils.getSessionLoginUser().getPorgId(); 
		//Map<String, String> parammap=new HashMap<String, String>();
		map.put("porgId", porgId);
		map.put("userId", userId);
		map.put("code", tableNamePrefix);
		List<Map<String, Object>> findAuthsProjectByIdList = basProjectMapper.findAuthsProjectByIdList(map);
		return findAuthsProjectByIdList.get(0);
	}


	@Override
	public int updateProjectState(Map<String, Object> params) {
		sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());
		BasProject basProject = sqlManager.unique(BasProject.class, params.get("projectId"));
		String projectStateId = params.containsKey("projectState")?params.get("projectState").toString():"10200301";
		String projectStateName = params.containsKey("projectStateName")?params.get("projectStateName").toString():"未审";
		String reviewResultName = params.containsKey("reviewResultName")?params.get("reviewResultName").toString():"未表决";
		basProject.setProjectStateId(projectStateId);
		basProject.setProjectStateName(projectStateName);
		basProject.setReviewResultName(reviewResultName);
		return sqlManager.updateById(basProject);
	}
	
	//指定为再议功能
	@Override
	public Boolean curMenu5toreDis(BasProject basProject) {
		Map<String, Object> params = Maps.newHashMap();
		logger.info("开始执行指定为再议操作");
		//params.put("enable", "0");//0：启用，1：不启用
		params.put("code", SysUserUtils.getCurrentCommissionCode());
		params.put("val", "02"); //01：投表情况，02：表决结果
		logger.info("查询出表决结果委员会名称");
		List<Map<String, Object>> selecResultList = ptVoteMatterConfService.selecResultList(params);
		String resultText="再议没启用";
		if(selecResultList!=null&&!selecResultList.isEmpty()){
			 resultText = selecResultList.get(0).get("committeeText")
					.toString(); //取得表决结果委员会名称
		}
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basProject.setUpdateTime(nowTimestamp);
		basProject.setUpdateName(SysUserUtils.getSessionLoginUser().getUserName());
		basProject.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
		basProject.setDelFlag("0");
		basProject.setProjectStateId("10200301");//已审
		basProject.setProjectStateName("已审");
		basProject.setReviewResultId("02");//审议结果id
		basProject.setReviewResultName(resultText); //审议结果名称
		int result = voteSqlManager.updateTemplateById(SysUserUtils.getCurrentCommissionCode(), basProject);
		logger.info("结束执行指定再议操作 flag{}");
		Boolean flag = result>0? true:false;
		return flag;
	}

	//项目升序
	@Override
	public boolean ascProjectOrder(Map<String, Object> params) {
		boolean flag=false;
		String conferenceId=(String) params.get("mettingId");
		String projectOrder=(String) params.get("projectOrder");
		String projectId=(String) params.get("projectId");
		String preRowProjectId=(String) params.get("preRowProjectId");
		String preProjectOrder=(String) params.get("preProjectOrder");
		
		String sufRowProjectId=(String) params.get("sufRowProjectId");
		String sufProjectOrder=(String) params.get("sufProjectOrder");
		
		
		
		if(!StringUtils.isEmpty(preRowProjectId)){
			BasProject basProject1=new BasProject();
			basProject1.setConferenceId(conferenceId);
			basProject1.setProjectId(projectId);
			basProject1.setProjectOrder(Integer.parseInt(preProjectOrder));
			basProject1.tableName=SysUserUtils.getCurrentCommissionCode();
			basProjectMapper.updateProjectOrder(basProject1);
			BasProject basProject2=new BasProject();
			basProject2.setConferenceId(conferenceId);
			basProject2.setProjectId(preRowProjectId);
			basProject2.setProjectOrder(Integer.parseInt(projectOrder));
			basProject2.tableName=SysUserUtils.getCurrentCommissionCode();
			basProjectMapper.updateProjectOrder(basProject2);
		}
		if(!StringUtils.isEmpty(sufRowProjectId)){
			BasProject basProject1=new BasProject();
			basProject1.setConferenceId(conferenceId);
			basProject1.setProjectId(projectId);
			basProject1.setProjectOrder(Integer.parseInt(sufProjectOrder));
			basProject1.tableName=SysUserUtils.getCurrentCommissionCode();
			basProjectMapper.updateProjectOrder(basProject1);
			BasProject basProject3=new BasProject();
			basProject3.setConferenceId(conferenceId);
			basProject3.setProjectId(sufRowProjectId);
			basProject3.setProjectOrder(Integer.parseInt(projectOrder));
			basProject3.tableName=SysUserUtils.getCurrentCommissionCode();
			basProjectMapper.updateProjectOrder(basProject3);
		}
		
		flag=true;
		return flag;
	}

	@Override
	public boolean descProjectOrder(BasProject basProject) {
		boolean flag=false;
		Map<String, Object> map=new HashMap<String, Object>();
		int projectOrder = basProject.getProjectOrder();
		String conferenceId = basProject.getConferenceId();
		map.put("projectOrder", projectOrder);
		map.put("conferenceId", conferenceId);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		BasProject basProject1 = basProjectMapper.findMinprojectOrder(map);
		//String projectId = basProject1.getProjectId();
		int projectOrder1 = basProject1.getProjectOrder();
		
		basProject.setProjectOrder(projectOrder1);
		basProject1.setProjectOrder(projectOrder);
		
		basProject1.setConferenceId(conferenceId);
		
		basProject.tableName=SysUserUtils.getCurrentCommissionCode();
		int count=basProjectMapper.updateProjectOrder(basProject);
		if (count>0) {
			flag=true;
		}else {
			flag=false;
		}
		if(flag){
			
			//basProject1.setProjectId(projectId);
			basProject1.tableName=SysUserUtils.getCurrentCommissionCode();
			int count1=basProjectMapper.updateProjectOrder(basProject1);
			if (count1>0) {
				flag=true;
			}else {
				flag=false;
			}
		}
		return flag;
	}

	@Override
	public BasProject findParamsList(String projectId) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("projectId", projectId);
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		
		return basProjectMapper.findParamsList(params);
	}

}
