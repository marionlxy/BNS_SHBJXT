package com.krm.voteplateform.web.reflectUpdate.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.krm.voteplateform.common.prehandle.IRequestHandler;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.web.basProject.model.BasProject;
import com.krm.voteplateform.web.basProject.service.BasProjectService;

public class ViewUpdateData implements IRequestHandler{

	
	
	private Logger logger = LoggerFactory.getLogger(ViewUpdateData.class);
	
	/**
	 * 修改前的反显数据信息
	 * @author zhangYuHai
	 * @param param
	 * @return basProMessage
	 */
	@Override
	public Object exec(Map<String, Object> param) {
		logger.info("自定义一个ViewFun1102Data类，实现IRequestHandler接口，反显出未审项目（Fun1102）修改的数据");
		BasProjectService basProjectService = SpringContextHolder.getBean("basProjectService");
		BasProject basProMessage = basProjectService.selectBasProject(param);
		logger.info("结束修改前的反显数据查询{}" + JSON.toJSONString(basProMessage));
		return basProMessage;
	}

}
