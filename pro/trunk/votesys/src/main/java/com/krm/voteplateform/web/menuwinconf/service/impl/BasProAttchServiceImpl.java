package com.krm.voteplateform.web.menuwinconf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.web.menuwinconf.dao.BasMenuWinConfMapper;
import com.krm.voteplateform.web.menuwinconf.model.BasProAttch;
import com.krm.voteplateform.web.menuwinconf.service.BasProAttchService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("basProAttchService")
public class BasProAttchServiceImpl implements BasProAttchService {

	@Resource
	private VoteSqlManager voteSqlManager;
	
	@Resource
	private SQLManager sqlManager;
	
	@Resource
	private BasMenuWinConfMapper basMenuWinConfMapper;
	
	@Override
	public int bachInsertFile(List<BasProAttch> list) {
		int[] result = voteSqlManager.insertBatch(SysUserUtils.getCurrentCommissionCode(), BasProAttch.class, list);
		return result.length;
	}

	@Override
	public List<Map<String, Object>> getattchTypeList(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMenuWinConfMapper.getattchTypeList(params);
	}

	@Override
	public List<Map<String, Object>> getProAttchList(HashMap<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMenuWinConfMapper.getProAttchList(params);
	}

	@Override
	public boolean checkFileName(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> list = basMenuWinConfMapper.checkFileName(params);
		if(list.size() == 0){
			return true;
		}
		return false;
	}

	@Override
	public BasProAttch getOneAttchType(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		BasProAttch basProAttch = basMenuWinConfMapper.getOneAttchType(params);
		return basProAttch;
	}

	@Override
	public int deleteOneAttchType(Map<String, Object> params) {
		return voteSqlManager.deleteById(SysUserUtils.getCurrentCommissionCode(), BasProAttch.class, params.get("pkid").toString());
	}
}
