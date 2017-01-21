package com.krm.voteplateform.web.reflectUpdate.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.krm.voteplateform.common.prehandle.IRequestHandler;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.web.basProject.model.BasMetting;
import com.krm.voteplateform.web.basProject.service.BasMettingService;

public class ViewCompleMetting implements IRequestHandler{

	private Logger logger = LoggerFactory.getLogger(ViewCompleMetting.class);
	
	/**
	 * @author zhangYuHai
	 * @param param
	 * @return 
	 */
	@Override
	public Object exec(Map<String, Object> param) {
		logger.info("自定义一个ViewCompleMetting类，实现IRequestHandler接口，反显出会议属性（fun1501）修改的数据");
		BasMettingService basMettingService = SpringContextHolder.getBean("basMettingService");
		BasMetting viewCompleMettingList = basMettingService.viewCompleMetting(param);
		logger.info("结束反显完成会议详情信息的数据查询{}" + JSON.toJSONString(viewCompleMettingList));
		return viewCompleMettingList;
	}

}
