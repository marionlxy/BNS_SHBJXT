package com.krm.voteplateform.web.menuwinconf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.web.menuwinconf.dao.BasMessageMapper;
import com.krm.voteplateform.web.menuwinconf.model.BasMessage;
import com.krm.voteplateform.web.menuwinconf.service.BasMessageService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("basMessageService")
public class BasMessageServiceImpl implements BasMessageService{
	
	Logger logger = LoggerFactory.getLogger(BasMessage.class);
	
	@Resource
	private BasMessageMapper basMessageMapper;
	
	@Resource
	private SQLManager sqlManager;
	
	@Resource
	private VoteSqlManager voteSqlManager ;

	@Override
	public List<Map<String, Object>> finbasMessage(String massCodes) {
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("massCodes", massCodes);
		logger.info("获取所有有消息信息");
		return basMessageMapper.finbasMessageAll(map);
	}

	
	@Override
	public BasMessage upBasMessageText(String id) {
		Map<String, String> map = Maps.newHashMap();
		map.put("id", id);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMessageMapper.upBasMessageText(map);
	}


	@Override
	public Boolean savUpBasMessage(BasMessage basMessage) {
		Boolean flag=false;
		basMessage.setEnableFlag("0");
		basMessage.setDelFlag("0");
		Date dat1=new Date();
		basMessage.setUpdateTime(dat1);
		basMessage.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
		try {
			sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());
			int insert = sqlManager.updateTemplateById(basMessage);
			//
			flag=insert>0?true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	//获取所有的提示信息
	@Override
	public Result findMessageByMegfuncode(String msgfuncodes) {
		HashMap<String, String> map = new HashMap<String,String>();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("code", msgfuncodes);       //变量code
		Map<String, Object> besByMegfuncode = basMessageMapper.findContionMessage(map);
		String enableFlag="1";
		if(besByMegfuncode!=null&&!besByMegfuncode.isEmpty()){
			 enableFlag = besByMegfuncode.get("enableFlag").toString();
		}
		logger.info("是否启用enableFlag{}" +  enableFlag); 
		Result result = Result.successResult();
		if("0".equals(enableFlag)){ //enableFlag : 0:启用 1:未启用
			result.setMsg(besByMegfuncode.get("commText").toString());//给出提示委员会内容
		}
		return result;
	}


	
	

}
