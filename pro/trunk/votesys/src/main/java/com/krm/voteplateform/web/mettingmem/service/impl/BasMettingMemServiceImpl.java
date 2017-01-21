package com.krm.voteplateform.web.mettingmem.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.VoteSqlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.web.basProject.dao.BasProjectMapper;
import com.krm.voteplateform.web.mettingmem.dao.BasMettingMemMapper;
import com.krm.voteplateform.web.mettingmem.model.BasMettingMem;
import com.krm.voteplateform.web.mettingmem.service.BasMettingMemService;
import com.krm.voteplateform.web.plateformorg.dao.PtPlateformOrgMapper;
import com.krm.voteplateform.web.plateformorg.model.PtPlateformOrg;
import com.krm.voteplateform.web.ptcommissionuser.dao.PtBasUserMapper;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * 操作相关
 */
@Service("basMettingMemService")
public class BasMettingMemServiceImpl implements BasMettingMemService {

	Logger logger = LoggerFactory.getLogger(BasMettingMemServiceImpl.class);

	@Autowired
	private BasMettingMemMapper basMettingMemMapper;
	
	@Resource
	private BasProjectMapper  basProjectMapper;
	
	@Autowired
	private  PtBasUserMapper ptBasUserMapper;
	
	@Autowired
	private VoteSqlManager voteSqlManager;
	
	@Override
	public void saveBasMettingMem(Map<String, Object> map) {
		//basMettingMemMapper.saveBasMettingMem(basMettingMem);
		String conferenceId = (String)map.get("mettingId");
		if(StringUtils.isEmpty(conferenceId)){
			return;
		}
		BasMettingMem basMettingMem=new BasMettingMem();

		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		String userName = SysUserUtils.getSessionLoginUser().getUserName();//用户名
		String userId = SysUserUtils.getSessionLoginUser().getId();
		String porgId = SysUserUtils.getSessionLoginUser().getPorgId(); 
		String post = SysUserUtils.getSessionLoginUser().getPost();//职务
	
		Map<String, String> parammap=new HashMap<String, String>();
		parammap.put("porgId", porgId);
		parammap.put("userId", userId);
		parammap.put("code",tableNamePrefix);
		Map<String, Object> userInfoMap = ptBasUserMapper.findCommisionUserInfoByIds(parammap).get(0);
		if(userInfoMap.isEmpty()){
			return;
		}
		
		//查询委员是否已经登录同一个会议,如果是同一个会议就进行控制
		map.put("conferenceId", conferenceId);
		map.put("memUserCode", userId);
		map.put(MyBatisConstans.DYTABLE_KEY,tableNamePrefix);
		List<Map<String, Object>> findByMapList = basMettingMemMapper.findByMapId(map);
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		if(!findByMapList.isEmpty()){//id
			String id = (String)findByMapList.get(0).get("id");
			basMettingMem.setId(id);
			basMettingMem.setMemUserIp((String)map.get("IP"));
			basMettingMem.setUpdateTime(nowTimestamp);
			basMettingMem.setUpdaetBy(userId);
			basMettingMem.setUpdateName(userName);
			voteSqlManager.updateTemplateById(tableNamePrefix, basMettingMem);
			return;
		}
	
		
		//basMettingMem.setId(id);
		
		basMettingMem.setConferenceId(conferenceId);
		basMettingMem.setMemUserCode(userId);
		basMettingMem.setMemUserName((String)userInfoMap.get("realName"));
		//所在机构
		basMettingMem.setMemOrgId(porgId);
		basMettingMem.setMemOrgName((String)userInfoMap.get("orgname"));
		//职务Id
		basMettingMem.setMemPostId(post);
		basMettingMem.setMemPostName(post);
		//角色Id
		basMettingMem.setMemRoleId((String)userInfoMap.get("crid"));
		basMettingMem.setMemRoleName((String)userInfoMap.get("rolename"));
		
		basMettingMem.setMemUserIp((String)map.get("IP"));
		//关键地方表决权
		basMettingMem.setMemAuthorityFlagId(null);
		basMettingMem.setMemAuthorityFlagName(null);
		//排序
		basMettingMem.setMemOrderCode(1);
		basMettingMem.setDelFlag("0");//0：正常；1：删除
		//
		basMettingMem.setCreateTime(nowTimestamp);
		basMettingMem.setCreateBy(userId);
		basMettingMem.setCreateName(userName);
		basMettingMem.setCreateIp(null);
		
		basMettingMem.setUpdateTime(nowTimestamp);
		basMettingMem.setUpdaetBy(userId);
		basMettingMem.setUpdateName(userName);
		basMettingMem.setUpdateIp(null);
		voteSqlManager.insert(tableNamePrefix, basMettingMem);
	}

	@Override
	public void updateBasMettingMem(BasMettingMem basMettingMem) {
		basMettingMemMapper.updateBasMettingMem(basMettingMem);
	}

	@Override
	public void removeBasMettingMem(BasMettingMem basMettingMem) {
		basMettingMemMapper.deleteBasMettingMem(basMettingMem);
	}

	@Override
	public BasMettingMem loadById(String id) {
		return basMettingMemMapper.findById(id);
	}

	@Override
	public Boolean findCommisionVoteAuths(Map<String, String> map) {
		Boolean flag=false;
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		String userId = SysUserUtils.getSessionLoginUser().getId();
		String porgId = SysUserUtils.getSessionLoginUser().getPorgId(); 
		Map<String, Object> parammap=new HashMap<String, Object>();
		map.put("porgId", porgId);
		map.put("userId", userId);
		map.put("code", tableNamePrefix);
		map.put(MyBatisConstans.DYTABLE_KEY, tableNamePrefix);
		List<Map<String, Object>> initAuthsList = ptBasUserMapper.findCommisionVoteAuths(map);
		if(!initAuthsList.isEmpty()){
			logger.info("{}初始有表决权......",userId);
			List<Map<String, Object>> normalAuthList=basProjectMapper.findAuthsProjectByIdList(map);
			
			
			if(!normalAuthList.isEmpty()){
				for (Map<String, Object> votemap : normalAuthList) {
					//有表决权
					String projectStateId = (String)votemap.get("projectStateId");
					if("10200302".equalsIgnoreCase(projectStateId)){
						String conferenceId = (String)map.get("conferenceId");
						logger.info("{}有表决权项目{}......",userId,projectStateId);
						if(!StringUtils.isEmpty(conferenceId)){
							parammap.put("conferenceId", conferenceId);
							parammap.put("memUserCode", userId);
							parammap.put(MyBatisConstans.DYTABLE_KEY, tableNamePrefix);
							List<Map<String, Object>> findByMapList = basMettingMemMapper.findByMapId(parammap);
							if(!findByMapList.isEmpty()){
								String memAuthorityFlagId =(String) findByMapList.get(0).get("memAuthorityFlagId");
								if("10100100".equalsIgnoreCase(memAuthorityFlagId)){
									flag=true;
									logger.info("{}有表决权项目{}最终{}......",userId,projectStateId,memAuthorityFlagId);
								}
								
							}
							
						}
						
						
						
						
					}
					
				}
				
			}else{
				logger.info("{}无最终表决权!!",userId);
			}
			
		}else{
			logger.info("{}初始无表决权!",userId);	
		}
		return flag;
	}

	@Override
	public Boolean saveMemMettingList(Map<String, Object> params) {
		List<BasMettingMem> lst=new ArrayList<BasMettingMem>();
		logger.info("处理与会人员信息：{}",JSON.toJSONString(params));
		String selectRows=(String) params.get("selectRows");
		String meetingId=(String) params.get("meetingId");
		JSONArray memArray = JSONArray.parseArray(selectRows);
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
				
				
		for (Iterator iterator = memArray.iterator(); iterator.hasNext();) {
			JSONObject object = (JSONObject) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();
			BasMettingMem basMettingMem=new BasMettingMem();
			String userName = SysUserUtils.getSessionLoginUser().getUserName();//用户名
			
			String userId = SysUserUtils.getSessionLoginUser().getId();
			//查询委员是否已经登录同一个会议,如果是同一个会议就进行控制
			map.put("conferenceId", meetingId);
			map.put("memUserCode", object.getString("puid"));
			map.put(MyBatisConstans.DYTABLE_KEY,tableNamePrefix);
			List<Map<String, Object>> findByMapList = basMettingMemMapper.findByMapId(map);
			
			if(!findByMapList.isEmpty()){//是否已经是同一次会议
				String id = (String)findByMapList.get(0).get("id");
				logger.info("{}是同一条记录",id);
				continue;
			}
			
			basMettingMem.setConferenceId(meetingId);
			basMettingMem.setMemUserCode(object.getString("puid"));
			
			basMettingMem.setMemUserName(object.getString("realName"));
			//所在机构
			basMettingMem.setMemOrgId(object.getString("porgid"));
			basMettingMem.setMemOrgName(object.getString("orgname"));
			//职务Id
			basMettingMem.setMemPostId(object.getString("post"));
			basMettingMem.setMemPostName(object.getString("post"));
			//角色Id
			basMettingMem.setMemRoleId(object.getString("crid"));
			basMettingMem.setMemRoleName(object.getString("rolename"));
			
			basMettingMem.setMemUserIp((String)params.get("IP"));
			//关键地方表决权
			basMettingMem.setMemAuthorityFlagId("10100100");
			basMettingMem.setMemAuthorityFlagName("离线表决权");
			//排序
			basMettingMem.setMemOrderCode(Integer.parseInt(object.getString("orderCode")));
			basMettingMem.setDelFlag("0");//0：正常；1：删除
			
	
			
			Timestamp nowTimestamp = DateUtils.getNowTimestamp();
			//
			basMettingMem.setCreateTime(nowTimestamp);
			basMettingMem.setCreateBy(userId);
			basMettingMem.setCreateName(userName);
			basMettingMem.setCreateIp(null);
			
			basMettingMem.setUpdateTime(nowTimestamp);
			basMettingMem.setUpdaetBy(userId);
			basMettingMem.setUpdateName(userName);
			basMettingMem.setUpdateIp(null);
			lst.add(basMettingMem);
		
		}
		int[] count=voteSqlManager.insertBatch(tableNamePrefix,BasMettingMem.class, lst);
		// TODO Auto-generated method stub
		return count.length>0?true:false;
	}


}
