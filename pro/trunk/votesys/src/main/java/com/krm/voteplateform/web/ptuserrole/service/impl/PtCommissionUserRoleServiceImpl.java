package com.krm.voteplateform.web.ptuserrole.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.plateformorg.dao.PtPlateformOrgMapper;
import com.krm.voteplateform.web.plateformorg.model.PtPlateformOrg;
import com.krm.voteplateform.web.plateformuser.dao.PtPlateformUserMapper;
import com.krm.voteplateform.web.plateformuser.model.PtPlateformUser;
import com.krm.voteplateform.web.ptcommissionorg.dao.PtCommissionOrgMapper;
import com.krm.voteplateform.web.ptcommissionorg.model.PtCommissionOrg;
import com.krm.voteplateform.web.ptcommissionuser.dao.PtBasUserMapper;
import com.krm.voteplateform.web.ptcommissionuser.model.PtCommissionUser;
import com.krm.voteplateform.web.ptuserrole.dao.PtCommissionUserRoleMapper;
import com.krm.voteplateform.web.ptuserrole.model.PtCommissionUserRole;
import com.krm.voteplateform.web.ptuserrole.service.PtCommissionUserRoleService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("ptCommissionUserRoleService")
public class PtCommissionUserRoleServiceImpl implements PtCommissionUserRoleService{
	
	@Resource
	private PtCommissionUserRoleMapper ptCommissionUserRoleMapper;
	
	@Resource
	private PtBasUserMapper ptBasUserMapper;
	
	@Resource
	private PtPlateformUserMapper ptPlateformUserMapper;
	
	@Resource
	private PtCommissionOrgMapper ptCommissionOrgMapper;
	
	@Resource
	private PtPlateformOrgMapper ptPlateformOrgMapper;
	
	@Resource
	private SQLManager sqlManager;

	@Override
	public List<Map<String, Object>> getCurrentCommissionUsers() {
		String code=SysUserUtils.getCurrentCommissionCode();
		List<Map<String, Object>> list=ptCommissionUserRoleMapper.findAllUser(code);
		return list;
	}

	@Override
	public boolean deleteById(String id) {
		PtCommissionUserRole ptCommissionUserRole=new PtCommissionUserRole();
		ptCommissionUserRole.setId(id);
		int delete=sqlManager.deleteById(ptCommissionUserRole.getClass(), id);
		if (delete>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean addPtAdmin(PtCommissionUserRole ptCommissionUserRole) {
		String code=SysUserUtils.getCurrentCommissionCode();
		ptCommissionUserRole.setCode(code);
		ptCommissionUserRole.setCrid("1");//在创建委员会时就给初始值，id为1
		Map<String, Object> mapUr=new HashMap<String, Object>();
		mapUr.put("puid", ptCommissionUserRole.getPuid());
		mapUr.put("code", code);
		Map<String, Object> mapRole=ptCommissionUserRoleMapper.getUserRoleById(mapUr);
		boolean flag=false;
		if (mapRole==null) {
			int insert=sqlManager.insert(ptCommissionUserRole);
			flag=true;
		}else {
			ptCommissionUserRole.setId(mapRole.get("id").toString());
			int update=sqlManager.updateTemplateById(ptCommissionUserRole);
			flag=true;
		}
		String puid=ptCommissionUserRole.getPuid();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("code", code);
		map.put("puid", puid);
		int count=ptBasUserMapper.existUser(map);
		int maxOrder=ptBasUserMapper.getMaxOrder(code);
		if(count>0){
			System.out.println("委员会用户表存在此用户");
			//Map<String, Object> mapUser=ptBasUserMapper.getUserById(puid, code);
			//String state=(String) mapUser.get("state");
			PtCommissionUser updateUser=new PtCommissionUser();
			updateUser.setCode(code);
			updateUser.setPuid(puid);
			updateUser.setOrderCode(maxOrder+1);
			updateUser.setState("0");
			//updateUser.setOrderCode(Integer.parseInt(String.valueOf(mapUser.get("orderCode"))));
			ptBasUserMapper.updateUserState(updateUser);
		}else {
			PtPlateformUser ptPlateformUser=ptPlateformUserMapper.getPtUserById(puid);
			PtCommissionUser ptCommissionUser=new PtCommissionUser();
			ptCommissionUser.setCode(code);
			ptCommissionUser.setOrderCode(maxOrder+1);
			ptCommissionUser.setPuid(puid);
			ptCommissionUser.setState("0");
			ptCommissionUser.setUserName(ptPlateformUser.getUserName());
			ptCommissionUser.setViewFlag("0");
			sqlManager.insert(ptCommissionUser);
		}
		PtPlateformUser ptPlateformUser=ptPlateformUserMapper.getPtUserById(puid);
		Map<String, Object> mapUserOrg=ptCommissionOrgMapper.getOrg(code, ptPlateformUser.getPorgId());
		if (mapUserOrg==null) {
			PtPlateformOrg ptPlateformOrg=ptPlateformOrgMapper.getPtPlateformOrgById(ptPlateformUser.getPorgId());
			int orgorder=ptCommissionOrgMapper.getMaxOrgOrder(code);
			PtCommissionOrg ptCommissionOrg=new PtCommissionOrg();
			ptCommissionOrg.setCode(code);
			ptCommissionOrg.setPorgid(ptPlateformUser.getPorgId());
			ptCommissionOrg.setState("01");
			ptCommissionOrg.setOrgname(ptPlateformOrg.getOrgname());
			ptCommissionOrg.setOrgorder(orgorder);
			ptCommissionOrg.setOrgtype(ptPlateformOrg.getOrgtype());
			sqlManager.insert(ptCommissionOrg);
		}
		if (flag==true) {
			return true;
		}else {
			return false;
		}
	}

}
