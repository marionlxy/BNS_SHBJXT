package com.krm.voteplateform.web.sysrole.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.PtCommissionFunction.model.PtCommissionFunction;
import com.krm.voteplateform.web.authorityrole.model.PtAuthorityRole;
import com.krm.voteplateform.web.commission.model.PtCommissionRole;
import com.krm.voteplateform.web.resource.dao.BaseResourceMapper;
import com.krm.voteplateform.web.sysrole.dao.SysRoleMapper;
import com.krm.voteplateform.web.sysrole.service.SysRoleService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService{
	
	@Resource
	private SQLManager sqlManager;
	
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	@Resource
	private BaseResourceMapper resourceMapper;

	@Override
	public List<Map<String, Object>> listRoles() {
		String code=SysUserUtils.getCurrentCommissionCode();
		List<Map<String, Object>> list=sysRoleMapper.listRoles(code);
		return list;
	}

	@Override
	public List<Map<String, Object>> listFunctionsByRole(String roleCategory) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		String pid="";
		if ("01".equalsIgnoreCase(roleCategory)) {
			map.put("type", "0");
			pid = resourceMapper.getParentidByType(map).get("id").toString();
		}else if ("02".equalsIgnoreCase(roleCategory)) {
			map.put("type", "1");
			pid = resourceMapper.getParentidByType(map).get("id").toString();
		}
		map.put("pid", pid);
		List<Map<String, Object>> list=sysRoleMapper.listFunctionsByRole(map);
		return list;
	}

	@Override
	public List<Map<String, Object>> listAuthorityByRole() {
		List<Map<String, Object>> list=sysRoleMapper.listAuthorityByRole();
		return list;
	}

	@Override
	public Boolean saveRole(PtCommissionRole ptCommissionRole, String resIds,
			List<PtAuthorityRole> authorityRoleList) {
		boolean flag=false;
		int count = 0;
		if(ptCommissionRole.getCrid() != null && !ptCommissionRole.getCrid().equals("")){
			count = sqlManager.updateTemplateById(ptCommissionRole);
			sysRoleMapper.deleteRoleFunctionsById(ptCommissionRole.getCrid());//根据角色id先把角色下的所有功能进行删除
			sysRoleMapper.deleteRoleAuthoritysById(ptCommissionRole.getCrid());//根据角色id先把角色下的所有权限进行删除
			
		}else{
			count=sqlManager.insert(ptCommissionRole);
			
		}
		String uuid="";
		if (count>0) {
			flag=true;
			uuid=ptCommissionRole.getCrid();
		}else {
			return false;
		}
		List<PtCommissionFunction> menuList=new ArrayList<PtCommissionFunction>();
		String[] resId = resIds.split(",");
		if (resId.length > 0) {
			for (int i = 0; i < resId.length; i++) {
				PtCommissionFunction ptCommissionFunction=new PtCommissionFunction();
				ptCommissionFunction.setCode(SysUserUtils.getCurrentCommissionCode());
				ptCommissionFunction.setResid(resId[i]);
				ptCommissionFunction.setCrid(uuid);
				menuList.add(ptCommissionFunction);
			}
		}
		
		if (authorityRoleList.size()>0) {
			for(PtAuthorityRole ptAuthorityRole:authorityRoleList){
				ptAuthorityRole.setCrid(uuid);
			}
		}
		
		if (flag) {
			if (menuList.size()>0) {
				sqlManager.insertBatch(PtCommissionFunction.class, menuList);
			}
			if (authorityRoleList.size()>0) {
				sqlManager.insertBatch(PtAuthorityRole.class, authorityRoleList);
			}
		}
		return flag;
	}

	@Override
	public Map<String, Object> getRoleById(String id) {
		Map<String, Object> map=sysRoleMapper.getRoleById(id);
		return map;
	}

	@Override
	public List<Long> getRoleFuntionsByRoleId(String crid) {
		List<Long> list=sysRoleMapper.getRoleFuntionsByRoleId(crid);
		return list;
	}

	@Override
	public List<Map<String, Object>> getRoleAuthoritysByRoleId(String crid) {
		List<Map<String, Object>> list=sysRoleMapper.getRoleAuthoritysByRoleId(crid);
		return list;
	}

	@Override
	public boolean saveRoleUpdate(PtCommissionRole ptCommissionRole, List<PtCommissionFunction> list,
			List<PtAuthorityRole> list2) {
		int count = 0;
		if(ptCommissionRole.getCrid() != null && !ptCommissionRole.getCrid().equals("")){
			count = sqlManager.updateTemplateById(ptCommissionRole);
		}
		boolean flag=false;
		if (count>0) {
			flag=true;
		}else {
			flag=false;
		}
		String crid=ptCommissionRole.getCrid();
		
		
		List<PtCommissionFunction> listParent=new ArrayList<PtCommissionFunction>();
		for (PtCommissionFunction ptCommissionFunction:list) {
			String resid=ptCommissionFunction.getResid();
			Map<String, String> map=new HashMap<String, String>();
			map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
			map.put("id", resid);
			Map<String, Object> mapResource=resourceMapper.getResourceById(map).get(0);
			String[] parentIds=mapResource.get("parentIds").toString().split(",");
			for(int i=0;i<parentIds.length;i++){
				if (!"1".equalsIgnoreCase(parentIds[i])) {
					PtCommissionFunction function=new PtCommissionFunction();
					function.setResid(parentIds[i]);
					function.setCode(SysUserUtils.getCurrentCommissionCode());
					listParent.add(function);
				}else {
					continue;
				}
			}
			
		}
		
		for(int i=0;i<listParent.size();i++){
			for(int j=i+1;j<listParent.size();i++){
				if (listParent.get(i).getResid().equalsIgnoreCase(listParent.get(j).getResid())) {
					listParent.remove(i);
					break;
				}else {
					continue;
				}
			}
			
		}
		
		list.addAll(listParent);
		
		if (list.size()>0) {
			for(PtCommissionFunction ptCommissionFunction:list){
				ptCommissionFunction.setCrid(crid);
			}
		}
		
		if (list2.size()>0) {
			for(PtAuthorityRole ptAuthorityRole:list2){
				ptAuthorityRole.setCrid(crid);
			}
		}
		
		if (flag) {
			if (list.size()>0) {
				sqlManager.insertBatch(PtCommissionFunction.class, list);
			}
			if (list2.size()>0) {
				sqlManager.insertBatch(PtAuthorityRole.class, list2);
			}
		}
		
		
		return flag;
	}

	@Override
	public boolean deleteRoleById(String crid) {
		boolean flag=false;
		int count=sqlManager.deleteById(PtCommissionRole.class, crid);
		if(count>0){
			flag=true;
		}else {
			flag=false;
		}
		sysRoleMapper.deleteRoleFunctionsById(crid);
		sysRoleMapper.deleteRoleAuthoritysById(crid);
		return flag;
	}

}
