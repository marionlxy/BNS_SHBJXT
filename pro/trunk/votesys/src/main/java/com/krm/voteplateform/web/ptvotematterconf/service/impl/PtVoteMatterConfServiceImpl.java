package com.krm.voteplateform.web.ptvotematterconf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.voteplateform.web.ptvotematterconf.dao.PtVoteMatterConfMapper;
import com.krm.voteplateform.web.ptvotematterconf.model.PtVoteMatterConf;
import com.krm.voteplateform.web.ptvotematterconf.service.PtVoteMatterConfService;

import net.sf.ehcache.search.aggregator.Count;

/**
 * 操作相关
 */
@Service("ptVoteMatterConfService")
public class PtVoteMatterConfServiceImpl implements PtVoteMatterConfService {

	Logger logger = LoggerFactory.getLogger(PtVoteMatterConfServiceImpl.class);

	@Autowired
	private PtVoteMatterConfMapper ptVoteMatterConfMapper;
	
	
	@Autowired
	private SQLManager sqlManager;
	
	@Override
	public void savePtVoteMatterConf(PtVoteMatterConf ptVoteMatterConf) {
		//Map<String,Object> map=new HashMap<String,Object>();
		List<PtVoteMatterConf> lmaps=new ArrayList<PtVoteMatterConf>();
		
		PtVoteMatterConf pConf=new PtVoteMatterConf();
		for (int i = 0; i <1; i++) {
			pConf.setCode(ptVoteMatterConf.getCode());
			pConf.setPlateformText("同意");
			pConf.setCommitteeText("同意");
			pConf.setType("01");
			pConf.setVal("");
			pConf.setType("0");
			lmaps.add(pConf);
		}
		/*map.put("code", ptVoteMatterConf.getCode());
		map.put("plateformText", "");
		map.put("committeeText","");
		map.put("type", "");
		map.put("val", "");
		map.put("enable", "");*/
		
		sqlManager.insertBatch(PtVoteMatterConf.class, lmaps);
		//ptVoteMatterConfMapper.savePtVoteMatterConf(ptVoteMatterConf);
	}

	@Override
	public List<Map<String, Object>> selectAll(Map<String, Object> params) {
		Map<String, String> map = Maps.newHashMap();
		//map.put("code", code);
		List<Map<String, Object>> list = ptVoteMatterConfMapper.showadd(params);
		return list;
	}
	@Override
	public List<Map<String, Object>> selecResultList(Map<String, Object> params) {
		Map<String, String> map = Maps.newHashMap();
		//map.put("code", code);
		List<Map<String, Object>> list = ptVoteMatterConfMapper.showResultadd(params);
		return list;
	}

	@Override
	public PtVoteMatterConf selectchoose(String id) {
		Map<String, String> map = Maps.newHashMap();
		map.put("id", id);
		PtVoteMatterConf ptVoteMatterConf =ptVoteMatterConfMapper.selectUpdate(map);
		return ptVoteMatterConf;
	}

	@Override
	public boolean updateSave(PtVoteMatterConf ptVoteMatterConf) {
		int i = ptVoteMatterConfMapper.lastSave(ptVoteMatterConf);
		if (i>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public  List<Map<String, Object>> findBtnInfo(Map<String, String> map) {
		map.put("type", "01");
		List<Map<String, Object>> list = ptVoteMatterConfMapper.findBtnInfoList(map);
		logger.info("查询到委员端表决按钮信息{}",JSON.toJSONString(list));
		return list;
	}

	@Override
	public String selectticket(String code) {
		String lot =  ptVoteMatterConfMapper.selectlot(code);
		return lot;
	}
	@Override
	public String selectonemind(Map<String, Object> map) {
		List<PtVoteMatterConf> oneMindFlag  = ptVoteMatterConfMapper.selecontmind(map);
		int i = oneMindFlag.size();
		return i+"" ;
	}

}
