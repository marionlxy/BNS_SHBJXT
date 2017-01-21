package com.krm.voteplateform.web.PtCommissionFunction.service.impl;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.PtCommissionFunction.service.PtCommissionFunctionService;

@Service("ptCommissionFunctionService")
public class PtCommissionFunctionServiceImpl implements PtCommissionFunctionService{
	
	@Resource
	private SQLManager sqlManager;
}
