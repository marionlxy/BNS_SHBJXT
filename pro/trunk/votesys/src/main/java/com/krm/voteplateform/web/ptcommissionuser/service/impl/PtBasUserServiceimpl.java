package com.krm.voteplateform.web.ptcommissionuser.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.plateformorg.dao.PtPlateformOrgMapper;
import com.krm.voteplateform.web.plateformuser.model.PtPlateformUser;
import com.krm.voteplateform.web.plateformuserpass.PtPlateformUserPass;
import com.krm.voteplateform.web.ptcommissionuser.dao.PtBasUserMapper;
import com.krm.voteplateform.web.ptcommissionuser.model.PtCommissionUser;
import com.krm.voteplateform.web.ptcommissionuser.service.PtBasUserService;
import com.krm.voteplateform.web.ptuserrole.dao.PtCommissionUserRoleMapper;
import com.krm.voteplateform.web.ptuserrole.model.PtCommissionUserRole;
import com.krm.voteplateform.web.sysrole.dao.SysRoleMapper;
import com.krm.voteplateform.web.util.SysUserUtils;
@Service("ptBasUserService")
public class PtBasUserServiceimpl implements PtBasUserService{
	
	@Autowired
	private  PtBasUserMapper ptBasUserMapper;
	
	@Autowired
	private SQLManager sqlmanager;
	
	@Autowired
	private SysRoleMapper sysroleMapper;
	
	@Autowired
	private PtPlateformOrgMapper ptPlateformOrgMapper;
	
	@Autowired
	private PtCommissionUserRoleMapper ptCommissionUserRoleMapper;
	//展示列表
	@Override
	public List<Map<String, Object>> showList() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("TableName", SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> list = ptBasUserMapper.selectdate(map);
		return list;
	}
	//退出委员会
	@Override
	public Boolean deleteById(String puid) {
		PtCommissionUser pb = new PtCommissionUser();
		String code = SysUserUtils.getCurrentCommissionCode();
		pb.setCode(code);
		pb.setState("1");
		pb.setPuid(puid);
		Boolean flag=false;
		int update=sqlmanager.updateTemplateById(pb);
		if (update>0) {
			flag=true;
		}else {
			flag=false;
		}
		/*if (flag) {
			PtCommissionUserRole ptCommissionUserRole=new PtCommissionUserRole();
			String id=(String) ptBasUserMapper.getUserById(puid, code).get("id");
			ptCommissionUserRole.setCode(code);
			ptCommissionUserRole.setPuid(puid);
			ptCommissionUserRole.setId(id);
			sqlmanager.updateById(ptCommissionUserRole);
		}*/
		return flag;
	}
	//用户信息
	@Override
	public List<Map<String, Object>> findCommisionUserInfoByIds(Map<String, String> parammap) {
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		String userName = SysUserUtils.getSessionLoginUser().getUserName();//用户名
		String userId = SysUserUtils.getSessionLoginUser().getId();
		String porgId = SysUserUtils.getSessionLoginUser().getPorgId(); 
		String post = SysUserUtils.getSessionLoginUser().getPost();//职务
		parammap.put("porgId", porgId);
		parammap.put("userId", userId);
		parammap.put("code",tableNamePrefix);
		List<Map<String, Object>> findCommisionUserInfoByIds = ptBasUserMapper.findCommisionUserInfoByIds(parammap);
		return findCommisionUserInfoByIds;
	}
	
	@Override
	public boolean ascOrder(PtCommissionUser ptCommissionUser,PtCommissionUser ptCommissionUser1) {
		boolean flag=false;
		Map<String, Object> map=new HashMap<String, Object>();
		int order=ptCommissionUser.getOrderCode();
		map.put("order", order);
		map.put("code", ptCommissionUser.getCode());
		//PtCommissionUser ptCommissionUser1=ptBasUserMapper.findMaxOrfer(map);//找到比order1小的最大order
		int order2=ptCommissionUser1.getOrderCode();
		
		ptCommissionUser.setOrderCode(order2);
		ptCommissionUser1.setOrderCode(order);
		
		int count=ptBasUserMapper.updateUserOrder(ptCommissionUser);
		if (count>0) {
			flag=true;
		}else {
			flag=false;
		}
		if(flag){
			int count1=ptBasUserMapper.updateUserOrder(ptCommissionUser1);
			if (count1>0) {
				flag=true;
			}else {
				flag=false;
			}
		}
		return flag;
	}
	@Override
	public boolean desOrder(PtCommissionUser ptCommissionUser,PtCommissionUser ptCommissionUser1) {
		boolean flag=false;
		Map<String, Object> map=new HashMap<String, Object>();
		int order=ptCommissionUser.getOrderCode();
		map.put("order", order);
		map.put("code", ptCommissionUser.getCode());
		//PtCommissionUser ptCommissionUser1=ptBasUserMapper.findMinOrder(map);//找到比order1小的最大order
		int order2=ptCommissionUser1.getOrderCode();
		
		ptCommissionUser.setOrderCode(order2);
		ptCommissionUser1.setOrderCode(order);
		
		int count=ptBasUserMapper.updateUserOrder(ptCommissionUser);
		if (count>0) {
			flag=true;
		}else {
			flag=false;
		}
		if(flag){
			int count1=ptBasUserMapper.updateUserOrder(ptCommissionUser1);
			if (count1>0) {
				flag=true;
			}else {
				flag=false;
			}
		}
		return flag;
	}
	@Override
	public Map<String, Object> getUserById(String puid, String code) {
		Map<String, Object> map=ptBasUserMapper.getUserById(puid,code);
		return map;
	}
	@Override
	public List<Map<String, Object>> getRoleList() {
		String code=SysUserUtils.getCurrentCommissionCode();
		List<Map<String, Object>> list=sysroleMapper.listRoles(code);
		return list;
	}
	@Override
	public boolean saveUserUpdate(PtCommissionUser ptCommissionUser, PtPlateformUser ptPlateformUser,
			PtCommissionUserRole ptCommissionUserRole,PtPlateformUserPass ptPlateformUserPass) {
		boolean flag=false;
		String code=ptCommissionUser.getCode();
		String puid=ptCommissionUser.getPuid();
		Map<String, Object> map=ptBasUserMapper.getUserPassword(puid);
		int count=ptBasUserMapper.saveUserUpdate(ptCommissionUser);
		if (count>0) {
			flag=true;
		}else {
			flag=false;
		}
		if (flag) {
			sqlmanager.updateTemplateById(ptPlateformUser);
			//sqlmanager.updateTemplateById(ptCommissionUserRole);
			//sqlmanager.updateById(ptCommissionUserRole);
			int update=ptBasUserMapper.updateUserRole(ptCommissionUserRole);
		}
		
		if (map==null) {
			sqlmanager.insert(ptPlateformUserPass);
		}else {
			//sqlmanager.updateTemplateById(ptPlateformUserPass);
			int update=ptBasUserMapper.updateUserPassword(ptPlateformUserPass);
		}
		return flag;
	}
	@Override
	public List<Map<String, Object>> listOrgs() {
		String code=SysUserUtils.getCurrentCommissionCode();
		List<Map<String, Object>> list=ptPlateformOrgMapper.listOrg(code);
		return list;
	}
	@Override
	public int getMaxOrder() {
		String code=SysUserUtils.getCurrentCommissionCode();
		int order=ptBasUserMapper.getMaxOrder(code);
		return order;
	}
	@Override
	public boolean addUsers(List<PtCommissionUser> list,List<PtCommissionUserRole> listUr) {
		//int count=sqlmanager.insert(ptCommissionUser);
		//int count=ptBasUserMapper.addUsers(ptCommissionUser);
		int[] count=sqlmanager.insertBatch(PtCommissionUser.class, list);
		boolean flag=false;
		if (count.length>0) {
			flag=true;
		}else {
			flag=false;
		}
		if (flag==true) {
			int[] inserts=sqlmanager.insertBatch(PtCommissionUserRole.class, listUr);
		}else {
			flag=false;
		}
		return flag;
	}
	@Override
	public List<Map<String, Object>> findCommisionUserList(Map<String, String> params) {
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		params.put("code",tableNamePrefix);
		List<Map<String, Object>> findCommisionUserInfoByIds = ptBasUserMapper.findCommisionUserInfoByIds(params);
		return findCommisionUserInfoByIds;
	}
	@Override
	public boolean updateState(PtCommissionUser ptCommissionUser) {
		int update=sqlmanager.updateTemplateById(ptCommissionUser);
		if (update>0) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public Map<String, Object> getUserRoleById(String puid, String code) {
		Map<String, Object> mapUr=new HashMap<String, Object>();
		mapUr.put("puid", puid);
		mapUr.put("code", code);
		Map<String, Object> map=ptCommissionUserRoleMapper.getUserRoleById(mapUr);
		return map;
	}
	@Override
	public List<Map<String, Object>> findHostUserIds(Map<String, Object> param) {
		List<Map<String, Object>> findHostIds = ptBasUserMapper.findHostUserIds(param);
		return findHostIds;
	}
}