package com.krm.voteplateform.web.voteplan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.voteplan.dao.PtVotePlanMapper;
import com.krm.voteplateform.web.voteplan.model.PtVotePlan;
import com.krm.voteplateform.web.voteplan.service.PtVotePlanService;

import net.sf.ehcache.search.aggregator.Count;

@Service("ptVotePlanService")
public class PtVotePlanServiceImpl implements PtVotePlanService {

	@Resource
	private PtVotePlanMapper ptVotePlanMapper;

	@Resource
	private SQLManager sqlManager;

	@Override
	public List<Map<String, Object>> listAll() {
		return ptVotePlanMapper.listAll();
	}

	@Override
	public boolean saveVotePlan(PtVotePlan ptVotePlan) {
		int insert = sqlManager.insert(ptVotePlan);
		if (insert > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void delete(String id) {
		PtVotePlan pt = null;
		String[] ids = StringUtils.split(id,",");
		for(int i=0;i<ids.length;i++){
			pt = new PtVotePlan();
			pt.setId(ids[i]);
			pt.setDelFlag("1");
			sqlManager.updateTemplateById(pt);
		}
		
	}

	@Override
	public void stop(String id) {
		PtVotePlan pt = null;
		String[] ids = StringUtils.split(id,",");
		for(int i=0;i<ids.length;i++){
			pt = new PtVotePlan();
			pt.setId(ids[i]);
			pt.setState(1);
			sqlManager.updateTemplateById(pt);
		}
	}

	@Override
	public boolean isUseFlag(String votePlanid) {
		int count =ptVotePlanMapper.recordCount(votePlanid);
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean startById(String id) {
		PtVotePlan pt = new PtVotePlan();
		pt.setId(id);
		pt.setState(0);
		int update=sqlManager.updateTemplateById(pt);
		return update>0?true:false;
	}

	@Override
	public List<Map<String, Object>> listAll(Map<String, Object> map) {
		map.put("state", "0");
		return ptVotePlanMapper.listAll(map);
	}

}
