package com.krm.voteplateform.web.ptvoterestmpl.service.impl;

import java.util.Map;

import org.beetl.sql.core.SQLManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.commission.dao.PtCommissionMapper;
import com.krm.voteplateform.web.ptvoterestmpl.dao.PtVoteResTmplMapper;
import com.krm.voteplateform.web.ptvoterestmpl.model.PtVoteResTmpl;
import com.krm.voteplateform.web.ptvoterestmpl.service.PtVoteResTmplService;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * 操作相关
 */
@Service("ptVoteResTmplService")
public class PtVoteResTmplServiceImpl implements PtVoteResTmplService {

	Logger logger = LoggerFactory.getLogger(PtVoteResTmplServiceImpl.class);
	
	@Autowired
	private PtVoteResTmplMapper ptVoteResTmplMapper;
	
	
	@Autowired
	private SQLManager sqlManager;

	@Override
	public void savePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl) {
		
		// TODO Auto-generated method stub
		//ptVoteResTmpl.setId(UUIDGenerator.getUUID());
		//ptVoteResTmplMapper.savePtVoteResTmpl(ptVoteResTmpl);
		sqlManager.insert(ptVoteResTmpl);
		logger.info("插入表决模板表结束");
		
	}

	@Override
	public void updatePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePtVoteResTmpl(PtVoteResTmpl ptVoteResTmpl) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public PtVoteResTmpl loadById(String id) {
		sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode()); 
		return sqlManager.unique(PtVoteResTmpl.class, id);
	}

	@Override
	public PtVoteResTmpl findByCode(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return ptVoteResTmplMapper.findByCode(params);
	}


}
