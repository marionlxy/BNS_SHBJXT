package com.krm.voteplateform.web.function.service.impl;

import java.sql.Timestamp;
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
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.web.function.dao.PtBasFunConfMapper;
import com.krm.voteplateform.web.function.model.BasFunConf;
import com.krm.voteplateform.web.function.service.PtBasFunConfService;
import com.krm.voteplateform.web.util.SysUserUtils;
@Service("ptBasFunConfService")
public class PtBasFunConfServiceImpl implements PtBasFunConfService {
	Logger logger = LoggerFactory.getLogger(PtBasFunConfServiceImpl.class);
	
	@Autowired
	private PtBasFunConfMapper ptBasFunConfMapper;
	
	@Autowired
	private VoteSqlManager voSqlManager;
	
	@Override
	public List<Map<String, Object>> selectAll(String Mlcid) {
		Map<String, String> map = Maps.newHashMap();
		map.put("Mlcid", Mlcid);
		map.put("code1",  SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> list = ptBasFunConfMapper.showadd(map);
		return list;
	}
	@Override
	public boolean savaFunctionTable(BasFunConf basFunConf) {
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basFunConf.setCraeteTime(nowTimestamp);
		basFunConf.setCreateBy(SysUserUtils.getSessionLoginUser().getId());
		int count = 0;
		if(basFunConf.getId() != null){
			count = voSqlManager.updateTemplateById(SysUserUtils.getCurrentCommissionCode(), basFunConf);
		}else{
			count = voSqlManager.insert(SysUserUtils.getCurrentCommissionCode(), basFunConf);
		}
		if(count > 0){
			return true;
		}
		return false;
	}
	@Override
	public boolean selectSysCode(String functionCode) {
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("functionCode", functionCode);
		int row = ptBasFunConfMapper.selectSysFunctionCode(map);
		return row>0?true:false;
	}
	@Override
	public BasFunConf selectCommtext(String id) {
		Map<String, String> map = Maps.newHashMap();
		map.put("id", id);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return ptBasFunConfMapper.selectCommtext(map);
	}
	//保存更新操作
	@Override
	public Boolean saveUpdate(String basdicts) {
			 Boolean flag=true;
			 JSONArray ja=JSON.parseArray(basdicts);
			 Iterator<Object> it = ja.iterator();
			 //List<BasDict> list= new ArrayList<BasDict>();
			 while (it.hasNext()) {
	             JSONObject ob = (JSONObject) it.next();
	             BasFunConf basFunConf = null;
	             basFunConf=new BasFunConf();
	             if(ob.getString("id")!=null){
	            	 basFunConf.setId(ob.getString("id"));
	             }
	             if(ob.getString("useFlag")!=null){
	            	 basFunConf.setUseFlag(ob.getString("useFlag"));
	             }
	             //Date date = new Date();
	             Timestamp nowTimestamp = DateUtils.getNowTimestamp();
	             basFunConf.setUpdateTime(nowTimestamp);
	             basFunConf.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
	             voSqlManager.updateTemplateById(SysUserUtils.getCurrentCommissionCode(),basFunConf);
	         }
			return flag;
			
		}
	@Override
	public List<Map<String, Object>> selectpull() {
		Map<String, String> map = Maps.newHashMap();
		String code  = SysUserUtils.getCurrentCommissionCode();
		map.put("code",code);
		List<Map<String, Object>> list = ptBasFunConfMapper.showpull(map);
		return list;
	}
	@Override
	public String gettempNameById(String tempId) {
		String Name = ptBasFunConfMapper.selectName(tempId);
		return Name;
	}
	@Override
	public List<Map<String, Object>> findBtnBasFunConfWind(Map<String, String> params) {
		return ptBasFunConfMapper.findBtnBasFunConfWind(params);
	}
	
	
}
