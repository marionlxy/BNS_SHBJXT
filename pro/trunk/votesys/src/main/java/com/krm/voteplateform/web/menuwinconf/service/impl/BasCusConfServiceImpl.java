package com.krm.voteplateform.web.menuwinconf.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.VoteSqlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.ParameterizedOutputVisitor;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.web.menuwinconf.dao.BasCusConfMapper;
import com.krm.voteplateform.web.menuwinconf.model.BasCusConf;
import com.krm.voteplateform.web.menuwinconf.service.BasCusConfService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("basCusConfService")
public class BasCusConfServiceImpl implements BasCusConfService{
	
	Logger logger = LoggerFactory.getLogger(BasMenuWinConfServiceImpl.class);

	@Autowired
	private BasCusConfMapper basCusConfMapper;
	
	@Resource
	private VoteSqlManager voteSqlManager;

	@Override
	public boolean save(BasCusConf basCusConf) {
		String tableNamePrefix = SysUserUtils.getCurrentCommissionCode();
		int insert = voteSqlManager.insert(tableNamePrefix, basCusConf);
		if (insert>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean isExist(String functionCode) {
		Map<String, String> map =new HashMap<String, String>();
		String tableName = SysUserUtils.getCurrentCommissionCode();
		map.put("tableName", tableName);
		map.put("functionCode", functionCode);
		int count = basCusConfMapper.findOne(map);
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void delete(String functionCode) {
		Map<String, String> map = new HashMap<String,String>();
		String tableName = SysUserUtils.getCurrentCommissionCode();
		map.put(MyBatisConstans.DYTABLE_KEY, tableName);
		map.put("functionCode", functionCode);
		basCusConfMapper.delete(map);
		
	}
}
