package com.krm.voteplateform.web.ptcommissionorg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.web.plateformorg.dao.PtPlateformOrgMapper;
import com.krm.voteplateform.web.plateformorg.model.PtPlateformOrg;
import com.krm.voteplateform.web.ptcommissionorg.dao.PtCommissionOrgMapper;
import com.krm.voteplateform.web.ptcommissionorg.model.PtCommissionOrg;
import com.krm.voteplateform.web.ptcommissionorg.service.PtCommissionOrgService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("ptCommissionOrgService")
public class PtCommissionOrgServiceImpl implements PtCommissionOrgService{

	@Resource
	private SQLManager sqlManager;
	
	@Resource
	private PtCommissionOrgMapper ptCommissionOrgMapper;
	
	@Resource
	private PtPlateformOrgMapper ptPlateformOrgMapper;

	@Override
	public List<Map<String, Object>> toOrgList() {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code",SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> list=ptCommissionOrgMapper.toOrgList(map);
		return list;
	}

	@Override
	public PtPlateformOrg getPtPlateformOrgById(String id) {
		PtPlateformOrg ptPlateformOrg=ptPlateformOrgMapper.getPtPlateformOrgById(id);
		return ptPlateformOrg;
	}

	@Override
	public boolean saveOrg(PtCommissionOrg ptCommissionOrg) {
		int count=sqlManager.insert(ptCommissionOrg);
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean saveOrgs(List<PtCommissionOrg> list) {
		int[] count=sqlManager.insertBatch(PtCommissionOrg.class, list);
		if (count.length>0) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Map<String, Object> getOrg(String code, String porgid) {
		Map<String, Object> map=ptCommissionOrgMapper.getOrg(code,porgid);
		return map;
	}

	@Override
	public boolean saveOrgUpdate(PtCommissionOrg ptCommissionOrg) {
		int count=ptCommissionOrgMapper.saveOrgUpdate(ptCommissionOrg);
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean deleteOrg(PtCommissionOrg ptCommissionOrg) {
		int count=ptCommissionOrgMapper.deleteOrg(ptCommissionOrg);
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean ascOrder(PtCommissionOrg ptCommissionOrg,PtCommissionOrg ptCommissionOrg1) {
		boolean flag=false;
		Map<String, Object> map=new HashMap<String, Object>();
		int order=ptCommissionOrg.getOrgorder();
		map.put("order", order);
		map.put("code", ptCommissionOrg.getCode());
		//PtCommissionOrg ptCommissionOrg1=ptCommissionOrgMapper.findMaxOrfer(map);//找到比order1小的最大order
		int order2=ptCommissionOrg1.getOrgorder();
		
		ptCommissionOrg.setOrgorder(order2);
		ptCommissionOrg1.setOrgorder(order);
		
		int count=ptCommissionOrgMapper.updateOrder(ptCommissionOrg);
		if (count>0) {
			flag=true;
		}else {
			flag=false;
		}
		if(flag){
			int count1=ptCommissionOrgMapper.updateOrder(ptCommissionOrg1);
			if (count1>0) {
				flag=true;
			}else {
				flag=false;
			}
		}
		return flag;
	}

	@Override
	public boolean desOrder(PtCommissionOrg ptCommissionOrg,PtCommissionOrg ptCommissionOrg1) {
		boolean flag=false;
		Map<String, Object> map=new HashMap<String, Object>();
		int order=ptCommissionOrg.getOrgorder();
		map.put("order", order);
		map.put("code", ptCommissionOrg.getCode());
		//PtCommissionOrg ptCommissionOrg1=ptCommissionOrgMapper.findMinOrder(map);//找到比order1小的最大order
		int order2=ptCommissionOrg1.getOrgorder();
		
		ptCommissionOrg.setOrgorder(order2);
		ptCommissionOrg1.setOrgorder(order);
		
		int count=ptCommissionOrgMapper.updateOrder(ptCommissionOrg);
		if (count>0) {
			flag=true;
		}else {
			flag=false;
		}
		if(flag){
			int count1=ptCommissionOrgMapper.updateOrder(ptCommissionOrg1);
			if (count1>0) {
				flag=true;
			}else {
				flag=false;
			}
		}
		return flag;
	}

	@Override
	public int getMaxOrgOrder() {
		String code=SysUserUtils.getCurrentCommissionCode();
		int order=ptCommissionOrgMapper.getMaxOrgOrder(code);
		return order;
	}

	@Override
	public List<Map<String, Object>> getCodeListOrg(Map<String, Object> mpparam) {
		mpparam.put("state", "04");//不是停用的
		return ptCommissionOrgMapper.getCodeListOrg(mpparam);
	}
}
