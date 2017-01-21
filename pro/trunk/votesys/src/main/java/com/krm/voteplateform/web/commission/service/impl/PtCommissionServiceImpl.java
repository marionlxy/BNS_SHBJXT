package com.krm.voteplateform.web.commission.service.impl;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.commission.dao.PtCommissionMapper;
import com.krm.voteplateform.web.commission.service.PtCommissionService;

@Service("ptCommissionService")
public class PtCommissionServiceImpl implements PtCommissionService{
	
	@Autowired
	private PtCommissionMapper ptCommissionMapper;

	
	@Autowired
	private SQLManager sqlManager;

}
