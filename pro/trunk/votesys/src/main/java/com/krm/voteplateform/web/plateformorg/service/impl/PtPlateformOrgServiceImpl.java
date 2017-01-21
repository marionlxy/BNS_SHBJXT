package com.krm.voteplateform.web.plateformorg.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.plateformorg.dao.PtPlateformOrgMapper;
import com.krm.voteplateform.web.plateformorg.service.PtPlateformOrgService;
import com.krm.voteplateform.web.plateformuser.dao.PtPlateformUserMapper;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("ptPlateformOrgService")
public class PtPlateformOrgServiceImpl implements PtPlateformOrgService{

	@Resource
	private PtPlateformOrgMapper plateformOrgMapper;
	
	@Resource
	private PtPlateformUserMapper ptPlateformUserMapper;
	
	@Resource
	private SQLManager sqlManager;

	@Override
	public List<Map<String, Object>> getOrgAll() {
		String code=SysUserUtils.getCurrentCommissionCode();
		List<Map<String, Object>> list=plateformOrgMapper.listOrg(code);
		return list;
	}

	@Override
	public List<Map<String, Object>> getUsersByOrgId(String id) {
		List<Map<String, Object>> list=ptPlateformUserMapper.getUserByOrgId(id);
		return list;
	}

	@Override
	public List<Map<String, Object>> toPtOrgList() {
		String code=SysUserUtils.getCurrentCommissionCode();
		List<Map<String, Object>> list=plateformOrgMapper.toPtOrgList(code);
		return list;
	}
}
