package com.krm.voteplateform.web.authorityrole.service.impl;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.authorityrole.service.PtAuthorityRoleService;

@Service("ptAuthorityRoleService")
public class PtAuthorityRoleServiceImpl implements PtAuthorityRoleService{

	@Resource
	private SQLManager sqlManager;
}
