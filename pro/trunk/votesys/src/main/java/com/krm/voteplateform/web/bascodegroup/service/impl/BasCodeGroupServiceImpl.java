package com.krm.voteplateform.web.bascodegroup.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.bascodegroup.dao.BasCodeGroupMapper;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;
import com.krm.voteplateform.web.bascodegroup.service.BasCodeGroupService;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * 操作相关
 */
@Service("basCodeGroupService")
public class BasCodeGroupServiceImpl implements BasCodeGroupService {

	Logger logger = LoggerFactory.getLogger(BasCodeGroupServiceImpl.class);

	@Autowired
	private BasCodeGroupMapper basCodeGroupMapper;
	
	@Resource
	private SQLManager sqlManager;

	@Override
	public Boolean saveBasCodeGroup(BasCodeGroup basCodeGroup,String code) {
		Boolean flag=false;
		basCodeGroup.setId(UUIDGenerator.getUUID());
		basCodeGroup.setParentId("0");
		basCodeGroup.setDelFlag("0");
		Date dat1=new Date();
		basCodeGroup.setCreateTime(dat1);
		String userName = SysUserUtils.getSessionLoginUser().getUserName();
		basCodeGroup.setCreateBy(userName);
		basCodeGroup.setUpdateTime(dat1);
		basCodeGroup.setUpdateBy(userName);
		try {
			//basCodeGroupMapper.saveBasCodeGroup(code,basCodeGroup);
			sqlManager.setTableNamePrefix(code);
			int insert = sqlManager.insert(BasCodeGroup.class, basCodeGroup);
			//
			flag=insert>0?true:false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Boolean updateBasCodeGroup(BasCodeGroup basCodeGroup,String code) {
		Boolean flag=false;
		//basCodeGroup.setId(UUIDGenerator.getUUID());
		basCodeGroup.setParentId("0");
		basCodeGroup.setDelFlag("0");
		Date dat1=new Date();
		basCodeGroup.setUpdateTime(dat1);
		basCodeGroup.setUpdateBy(SysUserUtils.getSessionLoginUser().getUserName());
		try {
			//basCodeGroupMapper.saveBasCodeGroup(code,basCodeGroup);
			sqlManager.setTableNamePrefix(code);
			int insert = sqlManager.updateTemplateById(basCodeGroup);
			//
			flag=insert>0?true:false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Boolean removeBasCodeGroup(BasCodeGroup basCodeGroup,String code) {
		Boolean flag=false;
		sqlManager.setTableNamePrefix(code);
		Date dat1=new Date();
		basCodeGroup.setState(1);
		basCodeGroup.setDelFlag("1");
		basCodeGroup.setUpdateTime(dat1);
		basCodeGroup.setUpdateBy(SysUserUtils.getSessionLoginUser().getUserName());
		try{
			int insert = sqlManager.updateTemplateById(basCodeGroup);
			flag=insert>0?true:false;
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			return flag;
	}

	@Override
	public BasCodeGroup loadById(String code,String id) {
		return basCodeGroupMapper.findById(code,id);
	}

	@Override
	public List<BasCodeGroup> selectAlls(String codeparameter) {
//		Map<String,String> map=new HashMap<String,String>();
//		map.put("del_flag", codeparameter);
//		sqlManager.select(BasCodeGroup.class, map);
		//sqlManager.all(clazz, mapper);
		//String codeparameternew=codeparameter+"_code"+"_group";
		 // List<BasCodeGroup> lst = sqlManager.execute(new SQLReady("select * from "+codeparameternew+" where name=? and age = ?","xiandafu",18),BasCodeGroup.class);
		  //List<BasCodeGroup> lst = sqlManager.execute(new SQLReady("select * from "+codeparameternew+" where del_flag='0'"),BasCodeGroup.class);
		
		List<BasCodeGroup> lst = basCodeGroupMapper.selectAlls(codeparameter);
		return lst;
	}

	@Override
	public List<Map<String, Object>> findBasCodeGroupList() {
		logger.info("开始查询编码分组下拉框");
		Map<String, Object> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("state","0");
		map.put("delFlag","0");
		List<Map<String, Object>> findSelectGroupList =basCodeGroupMapper.findBasCodeGroupList(map);
		logger.info("结束查询编码分组下拉框");
		return findSelectGroupList;
	}

	@Override
	public boolean selectName(Map<String, Object> paramap) {
		paramap.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		paramap.put("state","0");
		paramap.put("delFlag","0");
		List<Map<String, Object>> findSelectGroupList =basCodeGroupMapper.findBasCodeGroupList(paramap);
		if(findSelectGroupList==null || findSelectGroupList.isEmpty()||findSelectGroupList.size()<1)
		{
		  return false;  
		} 
		return true;
	}



}
