package com.krm.voteplateform.web.proattchtype.service.impl;

import javax.annotation.Resource;

import org.beetl.sql.core.VoteSqlManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.proattchtype.service.BasProAttchTypeService;

@Service("basProAttchTypeService")
public class BasProAttchTypeServiceImpl implements BasProAttchTypeService{

	@Resource
	private VoteSqlManager voteSqlManager;
}
