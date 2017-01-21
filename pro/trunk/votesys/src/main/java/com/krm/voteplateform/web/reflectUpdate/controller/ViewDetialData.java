package com.krm.voteplateform.web.reflectUpdate.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.krm.voteplateform.common.prehandle.IRequestHandler;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.web.basProject.model.BasMetting;
import com.krm.voteplateform.web.basProject.service.BasMettingService;

public class ViewDetialData implements IRequestHandler{

	private Logger logger = LoggerFactory.getLogger(ViewDetialData.class);
	
	/**
	 * 反显出会议详细信息
	 * @author zhangYuHai
	 * @param param
	 * @return basMettingList
	 */
	@Override
	public Object exec(Map<String, Object> param) {
		logger.info("自定义一个ViewDetialData类，实现IRequestHandler接口，反显出会议属性（fun1302）修改的数据");
		BasMettingService basMettingService = SpringContextHolder.getBean("basMettingService");
		BasMetting basMettingList = basMettingService.findeMettingDetail(param);
		logger.info("结束反显详情信息的数据查询{}" + JSON.toJSONString(basMettingList));
		return basMettingList;
	}

}
