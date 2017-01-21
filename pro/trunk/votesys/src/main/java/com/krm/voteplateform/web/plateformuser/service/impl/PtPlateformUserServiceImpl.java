package com.krm.voteplateform.web.plateformuser.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.plateformuser.dao.PtPlateformUserMapper;
import com.krm.voteplateform.web.plateformuser.service.PtPlateformUserService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("ptPlateformUserService")
public class PtPlateformUserServiceImpl implements PtPlateformUserService{
	
	@Resource
	private SQLManager sqlManager;
	
	@Resource
	private PtPlateformUserMapper ptPlateformUserMapper;

	@Override
	public List<Map<String, Object>> toPtUserList() {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("code", SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> list=ptPlateformUserMapper.toPtUserList(map);
		return list;
	}

	@Override
	public List<Map<String, Object>> toPtUserListByCondition(String organId, String userName) {
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> mapCode=new HashMap<String, Object>();
		mapCode.put("code", SysUserUtils.getCurrentCommissionCode());
		if ((organId==""||organId==null)&&(userName==""||userName==null)) {
			return ptPlateformUserMapper.toPtUserList(mapCode);
		}else {
			map.put("organId", organId);
			map.put("userName", userName);
			map.put("code", SysUserUtils.getCurrentCommissionCode());
			return ptPlateformUserMapper.toPtUserListByCondition(map);
		}
	}
	

}
