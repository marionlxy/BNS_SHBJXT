package com.krm.voteplateform.web.bascode.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.bascode.dao.BasCodeMapper;
import com.krm.voteplateform.web.bascode.model.BasCode;
import com.krm.voteplateform.web.bascode.service.BasCodeService;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * 操作相关
 */
@Service("basCodeService")
public class BasCodeServiceImpl implements BasCodeService {

	Logger logger = LoggerFactory.getLogger(BasCodeServiceImpl.class);

	@Autowired
	private BasCodeMapper basCodeMapper;
	
	@Resource
	private SQLManager sqlManager;

	@Override
	public Boolean saveBasCode(BasCode basCode,String code) {
		Boolean flag=false;
		basCode.setId(UUIDGenerator.getUUID());
		basCode.setDelFlag("0");
		Date dat1=new Date();
		basCode.setCreateTime(dat1);
		basCode.setCreateBy(SysUserUtils.getSessionLoginUser().getUserName());
		basCode.setUpdateTime(dat1);
		basCode.setUpdateBy(SysUserUtils.getSessionLoginUser().getUserName());
		try {
			//basCodeGroupMapper.saveBasCodeGroup(code,basCodeGroup);
			sqlManager.setTableNamePrefix(code);
			int insert = sqlManager.insert(BasCode.class, basCode);
			//
			flag=insert>0?true:false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Boolean updateBasCode(String code,BasCode basCode) {
		Boolean flag=false;
		//basCode.setId(UUIDGenerator.getUUID());
		Date dat1=new Date();
		//
		basCode.setUpdateTime(dat1);
		basCode.setUpdateBy(SysUserUtils.getSessionLoginUser().getUserName());
		try {
			//basCodeGroupMapper.saveBasCodeGroup(code,basCodeGroup);
			sqlManager.setTableNamePrefix(code);
			int insert = sqlManager.updateTemplateById(basCode);
			//
			flag=insert>0?true:false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Boolean removeBasCode(String code,BasCode basCode) {
		Boolean flag=false;
		//basCode.setId(UUIDGenerator.getUUID());
		Date dat1=new Date();
		basCode.setDelFlag("1");//删除
		basCode.setUpdateTime(dat1);
		basCode.setUpdateBy(SysUserUtils.getSessionLoginUser().getUserName());
		try {
			//basCodeGroupMapper.saveBasCodeGroup(code,basCodeGroup);
			sqlManager.setTableNamePrefix(code);
			int insert = sqlManager.updateTemplateById(basCode);
			//
			flag=insert>0?true:false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public BasCode loadById(String code,String id) {
		return basCodeMapper.findById(code,id);
	}

	@Override
	public List<Map<String, Object>> selectByCode(String currentCommissionCode, String cgId) {
		List<Map<String, Object>> lap=basCodeMapper.selectByCode(currentCommissionCode,cgId);
		return lap;
	}



}
