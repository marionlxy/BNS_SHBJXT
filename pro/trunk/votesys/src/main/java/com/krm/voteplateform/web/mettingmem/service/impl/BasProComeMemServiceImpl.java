package com.krm.voteplateform.web.mettingmem.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.krm.voteplateform.web.mettingmem.dao.BasProComeMemMapper;
import com.krm.voteplateform.web.mettingmem.model.BasMettingMem;
import com.krm.voteplateform.web.mettingmem.model.BasProComeMem;
import com.krm.voteplateform.web.mettingmem.service.BasProComeMemService;
import com.krm.voteplateform.web.ptcommissionuser.dao.PtBasUserMapper;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * 操作相关
 */
@Service("basProComeMemService")
public class BasProComeMemServiceImpl implements BasProComeMemService {

	Logger logger = LoggerFactory.getLogger(BasProComeMemServiceImpl.class);

	@Autowired
	private BasProComeMemMapper basProComeMemMapper;
	
	@Autowired
	private  PtBasUserMapper ptBasUserMapper;
	
	@Autowired
	private VoteSqlManager voteSqlManager;
	
	@Override
	public Boolean saveBasProComeMem(Map<String,String> map) {
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		String userName = SysUserUtils.getSessionLoginUser().getUserName();//用户名
		String userId = SysUserUtils.getSessionLoginUser().getId();
		String porgId = SysUserUtils.getSessionLoginUser().getPorgId(); 
		String post = SysUserUtils.getSessionLoginUser().getPost();//职务
		//real_name
		Map<String, String> parammap=new HashMap<String, String>();
		parammap.put("porgId", porgId);
		parammap.put("userId", userId);
		parammap.put("code",tableNamePrefix);
		Map<String, Object> userInfoMap = ptBasUserMapper.findCommisionUserInfoByIds(parammap).get(0);
		
		BasProComeMem basProComeMem=new BasProComeMem();
		basProComeMem.setProjectId(map.get("projectId"));
		basProComeMem.setUserCode(userId);
		basProComeMem.setUserName((String)userInfoMap.get("realName"));
		basProComeMem.setUserIp(map.get("clientAddress"));
		basProComeMem.setVoteResult(map.get("passVal"));
		basProComeMem.setSuggestion(map.get("suggestionContent"));//审查意见
		basProComeMem.setOrderCode(999);
		basProComeMem.setDelFlag("0");
		
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basProComeMem.setCreateTime(nowTimestamp);
		basProComeMem.setCreateBy(userId);
		basProComeMem.setCreateName(userName);
		basProComeMem.setCreateIp(null);
		
		basProComeMem.setUpdateTime(nowTimestamp);
		basProComeMem.setUpdateBy(userId);
		basProComeMem.setUpdateName(userName);
		basProComeMem.setUpdateIp(null);
		int row = voteSqlManager.insert(tableNamePrefix, basProComeMem);
		return row>0?true:false;
	}

	@Override
	public void updateBasProComeMem(BasProComeMem basProComeMem) {
		basProComeMemMapper.updateBasProComeMem(basProComeMem);
	}

	@Override
	public void removeBasProComeMem(BasProComeMem basProComeMem) {
		basProComeMemMapper.deleteBasProComeMem(basProComeMem);
	}

	@Override
	public BasProComeMem loadById(String id) {
		return basProComeMemMapper.findById(id);
	}

	@Override
	public Boolean loadByMapId(Map<String, Object> objectmap) {
		String userId = SysUserUtils.getSessionLoginUser().getId();
		objectmap.put("userCode", userId);
		List<Map<String, Object>> lst= basProComeMemMapper.loadByMapId(objectmap);
		return lst.isEmpty()?false:true;
	}

	@Override
	public Boolean saveBatchBasProComeMemList(Map<String, Object> params) {
		List<BasProComeMem> lst=new ArrayList<BasProComeMem>();
		logger.info("处理与会人员意见：{}",JSON.toJSONString(params));
		String selectRows=(String) params.get("selectRows");
		String meetingId=(String) params.get("meetingId");
		JSONArray memArray = JSONArray.parseArray(selectRows);
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		String userName = SysUserUtils.getSessionLoginUser().getUserName();//用户名
		String userId = SysUserUtils.getSessionLoginUser().getId();
		for (Iterator iterator = memArray.iterator(); iterator.hasNext();) {
			JSONObject object = (JSONObject) iterator.next();
			Map<String, Object> map=new HashMap<String, Object>();
			BasProComeMem basProComeMem=new BasProComeMem();
			basProComeMem.setProjectId((String)params.get("projectId"));
		    basProComeMem.setUserCode(object.getString("puid"));
			basProComeMem.setUserName(object.getString("realName"));
			basProComeMem.setUserIp((String)params.get("clientAddress"));
		   basProComeMem.setVoteResult(object.getString("passVal"));
  		   basProComeMem.setSuggestion(object.getString("passContent"));//审查意见
			basProComeMem.setOrderCode(Integer.parseInt(object.getString("orderCode")));
			basProComeMem.setDelFlag("0");
			
			Timestamp nowTimestamp = DateUtils.getNowTimestamp();
			basProComeMem.setCreateTime(nowTimestamp);
			basProComeMem.setCreateBy(userId);
			basProComeMem.setCreateName(userName);
			basProComeMem.setCreateIp("离线表决意见");
			
			basProComeMem.setUpdateTime(nowTimestamp);
			basProComeMem.setUpdateBy(userId);
			basProComeMem.setUpdateName(userName);
			basProComeMem.setUpdateIp(null);
			lst.add(basProComeMem);
		}
		int[] count=voteSqlManager.insertBatch(tableNamePrefix,BasProComeMem.class, lst);
		// TODO Auto-generated method stub
		return count.length>0?true:false;
	}



}
