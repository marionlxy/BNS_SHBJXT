package com.krm.voteplateform.web.pageTemplet.service.impl;

import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.pageTemplet.dao.PtVotePageTempletMapper;
import com.krm.voteplateform.web.pageTemplet.model.PtVotePageTemplet;
import com.krm.voteplateform.web.pageTemplet.service.PtVotePageTempletService;

@Service("ptVotePageTempletService")
public class PtVotePageTempletServiceImpl implements PtVotePageTempletService{

	Logger logger = LoggerFactory.getLogger(PtVotePageTempletServiceImpl.class);
	
	@Autowired
	private PtVotePageTempletMapper ptVotePageTempletMapper;
	
	@Autowired
	private SQLManager sqlManager;

	@Override
	public List<Map<String, Object>> listPT() {
		List<Map<String, Object>> list = ptVotePageTempletMapper.getAllPt();
		return list;
	}

	@Override
	public boolean savePage(PtVotePageTemplet ptVotePageTemplet) {
		int count = 0;
		if(ptVotePageTemplet.getId() == null || ptVotePageTemplet.getId().equals("")){
			count = sqlManager.insert(PtVotePageTemplet.class, ptVotePageTemplet);
		}else{
			count = sqlManager.updateById(ptVotePageTemplet);
		}
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Map<String, Object> getPageTempletById(String id) {
		Map<String, Object> map =ptVotePageTempletMapper.findPageTempletById(id);
		return map;
	}

	@Override
	public boolean updatePageTemplet(PtVotePageTemplet ptVotePageTemplet) {
		int update = sqlManager.updateById(ptVotePageTemplet);
		if (update>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean isRepeat(String name,String id) {
		List<Map<String, Object>> list = ptVotePageTempletMapper.validateByName(id);
		for(Map<String, Object> map:list){
			String a =(String) map.get("name");
			if (name.equalsIgnoreCase(a)) {
				return true;
			}else {
				continue;
			}
		}
		return false;
	}
}
