package com.krm.voteplateform.web.dynamicsource.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.dynamicsource.dao.PtDynamicSourceMapper;
import com.krm.voteplateform.web.dynamicsource.service.PtDynamicSourceService;
		@Service("PtDynamicSourceService")
public class PtDynamicSourceServiceImpl implements PtDynamicSourceService{
		
		@Resource
		private  PtDynamicSourceMapper ptDynamicSourceMapper;
		
		@Override
		public List<Map<String, Object>> selectDate() {
			return ptDynamicSourceMapper.listAll();
		}
	
	
	
}