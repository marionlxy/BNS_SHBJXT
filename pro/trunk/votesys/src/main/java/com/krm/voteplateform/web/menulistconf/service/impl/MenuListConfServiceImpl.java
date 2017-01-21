package com.krm.voteplateform.web.menulistconf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.format.HexFormat;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.web.menulistconf.dao.MenuListConfMapper;
import com.krm.voteplateform.web.menulistconf.model.BasMenuListConf;
import com.krm.voteplateform.web.menulistconf.service.MenuListConfService;
import com.krm.voteplateform.web.sys.model.BasDict;
import com.krm.voteplateform.web.util.SysUserUtils;
import com.krm.voteplateform.web.voteplan.model.PtVotePlan;

@Service("menuListConfService")
public class MenuListConfServiceImpl implements MenuListConfService{
	
	@Resource
	private SQLManager sqlManager;
	
	@Resource
	private VoteSqlManager voteSqlManager;
	
	@Resource
	private MenuListConfMapper menuListConfMapper;

	@Override
	public List<Map<String, Object>> listMenuConf(String resid) {
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("resid", resid);
		List<Map<String, Object>> list = menuListConfMapper.getMenuAll(map);
		for(Map<String, Object> map2:list){
			Map<String, String> map3 =new HashMap<String, String>();
			String datasource = (String) map2.get("datasource");
			map3.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
			map3.put("datasource", datasource);
			String dataSourceName = menuListConfMapper.getDataSourceNameById(map3);
			map2.put("datasource", dataSourceName);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getAllField() {
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> list = menuListConfMapper.listField(map);
		return list;
	}

	@Override
	public List<String> getListType() {
		List<String> types = menuListConfMapper.getTypeList();
		return types;
	}

	@Override
	public List<Map<String, Object>> getDataSource() {
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> types = menuListConfMapper.getDataSource(map);
		return types;
	}

	@Override
	public boolean saveMenuConf(BasMenuListConf basMenuListConf) {
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		int insert = voteSqlManager.insert(tableNamePrefix, basMenuListConf);
		if (insert>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Map<String, Object> getMenuForm(String id) {
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("id", id);
		Map<String, Object> map2 = menuListConfMapper.getMenuById(map);
		return map2;
	}

	@Override
	public boolean saveMeUpdate(BasMenuListConf basMenuListConf) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("basMenuListConf", basMenuListConf);
		//int update = voteSqlManager.updateTemplateById(tableNamePrefix, basMenuListConf);
		int update = menuListConfMapper.updateTemplateById(map);
		if(update>0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void deleteById(String ids) {
		BasMenuListConf basMenuListConf = null;
		String[] idss = StringUtils.split(ids,",");
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		for(int i=0;i<idss.length;i++){
			basMenuListConf = new BasMenuListConf();
			basMenuListConf.setId(idss[i]);
			//basMenuListConf.setDelFlag("1");
			voteSqlManager.deleteById(tableNamePrefix, basMenuListConf.getClass(), basMenuListConf.getId());
			//sqlManager.deleteById(basMenuListConf.getClass(), basMenuListConf.getId());
		}
		
	}

	@Override
	public boolean isExistDicid(String repid,String resid) {
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("repid", repid);
		map.put("resid", resid);
		Map<String, Object> map2= menuListConfMapper.getMenuByRepid(map);
		if (map2==null) {
			return true;
		}else{
			return false;
		}
		
	}

	//导出信息的表头字段
	@Override
	public List<Map<String,Object>> findMenuListConf(String resid) {
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("resid",resid);
		return menuListConfMapper.findMenuListConf(map);
	}
}
