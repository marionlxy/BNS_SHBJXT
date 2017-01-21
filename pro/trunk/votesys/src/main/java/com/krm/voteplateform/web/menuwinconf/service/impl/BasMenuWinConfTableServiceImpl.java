package com.krm.voteplateform.web.menuwinconf.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.StringConvert;
import com.krm.voteplateform.web.function.model.BasFunConf;
import com.krm.voteplateform.web.menuwinconf.dao.BasMenuWinConfTableMapper;
import com.krm.voteplateform.web.menuwinconf.model.BasMenuWinConf;
import com.krm.voteplateform.web.menuwinconf.service.BasMenuWinConfTableService;
import com.krm.voteplateform.web.sys.model.BasDict;
import com.krm.voteplateform.web.util.SysUserUtils;
import com.objectplanet.image.a;
@Service("basMenuWinConfTableService")
public class BasMenuWinConfTableServiceImpl implements BasMenuWinConfTableService {

	Logger logger = LoggerFactory.getLogger(BasMenuWinConfTableServiceImpl.class);

	@Autowired
	private BasMenuWinConfTableMapper basMenuWinConfTableMapper;
	
	@Resource
	private VoteSqlManager voteSqlManager;
	//显示增加页面之后的保存
	@Override
	public boolean savawinconfTable(BasMenuWinConf basMenuWinConf,String groupid) {
		Date date = new Date();
		basMenuWinConf.setCreateTime(date);
		basMenuWinConf.setUpdateTime(date);
		//尾数加1处理
		Map<String, String> map = Maps.newHashMap();
		map.put("tableName",  SysUserUtils.getCurrentCommissionCode());
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		String subgroup = StringUtils.substring(groupid, 0,3);
		String searchText = new StringBuilder(subgroup).append("%").toString();
		map.put("groupid", searchText);
		List<Map<String, Object>> lstGroup = basMenuWinConfTableMapper.findSelectGroupList(map);
		int groupId=0;//这是后三位
		  for (Map<String, Object> mapMax : lstGroup)  
		    {  
			  String key="groupid";
			  String value=(String) mapMax.get(key);
			  if(!StringUtils.isEmpty(value)){
				  int result=Integer.parseInt(value.substring(3,6));
				  if(result>=groupId){
					  groupId=result+1;
				  }
				  continue;
			  }
		    } 
		//String newGroupId=subgroup+StringConvert.addZeroForNum(groupId+"", 3, true);
		String newGroupId = subgroup+StringConvert.addZeroForNum(groupId+"", 3, true);
		basMenuWinConf.setGroupid(newGroupId);  
		basMenuWinConf.setCreateBy(SysUserUtils.getSessionLoginUser().getId());
		voteSqlManager.insert(SysUserUtils.getCurrentCommissionCode(), basMenuWinConf);
		return true;
	}
	//页面上的数据的显示
	@Override
	public List<Map<String, Object>> selectAll(String functionCode) {
		Map<String, String> map = Maps.newHashMap();
		map.put("code",  SysUserUtils.getCurrentCommissionCode());
		map.put("functionCode", functionCode);
		List<Map<String, Object>> list = basMenuWinConfTableMapper.showadd(map);
		return list;
	}
	@Override
	public BasMenuWinConf selectCommtext(String functionCode,String id) {
		Map<String, String> map = Maps.newHashMap();
		map.put("functionCode", functionCode);
		map.put("id", id);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMenuWinConfTableMapper.selectCommtext(map);
	}
	@Override
	public boolean lastsave(BasMenuWinConf basMenuWinConf) {
		Date date = new Date();
		basMenuWinConf.setUpdateTime(date);
		String code  = SysUserUtils.getCurrentCommissionCode();
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("code", code);
		map.put("a", basMenuWinConf);
		int i = basMenuWinConfTableMapper.lastsave(map);
		if (i>0) {
			return true;
		}else{
		return false;
	}
	}
	//保存更新操作
		@Override
		public Boolean saveUpdate(String basdicts) {
				 Boolean flag=true;
				 JSONArray ja=JSON.parseArray(basdicts);
				 Iterator<Object> it = ja.iterator();
				 List<BasDict> list= new ArrayList<BasDict>();
				 while (it.hasNext()) {
		             JSONObject ob = (JSONObject) it.next();
		             BasMenuWinConf basMenuWinConf = null;
		             basMenuWinConf=new BasMenuWinConf();
		             if(ob.getString("id")!=null){
		            	 basMenuWinConf.setId(ob.getString("id"));
		             }
		             if(ob.getString("useFlag")!=null){
		            	 basMenuWinConf.setUseFlag(ob.getString("useFlag"));
		             }
		             Date date = new Date();
		             basMenuWinConf.setUpdateTime(date);
		             basMenuWinConf.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
		             voteSqlManager.updateTemplateById(SysUserUtils.getCurrentCommissionCode(),basMenuWinConf);
		         }
				return flag;
			}
		@Override
		public List<Map<String, Object>> selectpull(String functionCode) {
			Map<String, String> map = Maps.newHashMap();
			String code  = SysUserUtils.getCurrentCommissionCode();
			map.put("code",code);
			map.put("functionCode", functionCode);
			List<Map<String, Object>> list = basMenuWinConfTableMapper.showpull(map);
			return list;
		}
		@Override
		public Map<String, Object> getBtnOrder(Map<String, Object> params) {
			params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
			return basMenuWinConfTableMapper.getBtnOrder(params);
		}
	}