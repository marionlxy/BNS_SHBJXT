package com.krm.voteplateform.web.downloadmodel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.web.bascode.dao.BasCodeMapper;
import com.krm.voteplateform.web.downloadmodel.service.DownoadModelService;
import com.krm.voteplateform.web.menuwinconf.dao.BasMenuWinConfMapper;
import com.krm.voteplateform.web.sys.dao.SysDicMapper;

@Service("downoadModelService")
public class DownoadModelServiceImpl implements DownoadModelService{
	
	@Resource
	private SysDicMapper sysDicMapper;
	
	@Resource
	private BasMenuWinConfMapper basMenuWinConfMapper;
	
	@Resource
	private BasCodeMapper basCodeMapper;

	@Override
	public List<Map<String, Object>> getDicData(String code) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("category", "02");
		map.put("tableName", code);
		map.put("dataType","06");
		List<Map<String, Object>> list=sysDicMapper.findSysList(map);
		return list;
	}

	@Override
	public List<Map<String, Object>> getAttachType(String code) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put(MyBatisConstans.DYTABLE_KEY, code);
		map.put("msFlag","0");
		List<Map<String, Object>> list=basMenuWinConfMapper.getattchTypeList(map);
		return list;
	}

	@Override
	public List<Map<String, Object>> getCodeGroup(String code) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("code", code);
		List<Map<String, Object>> list=basCodeMapper.getCodeGroup(map);
		return list;
	}

}
