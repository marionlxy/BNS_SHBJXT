package com.krm.voteplateform.web.ptsystemsmenu.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.function.dao.PtBasFunConfMapper;
import com.krm.voteplateform.web.function.model.BasFunConf;
import com.krm.voteplateform.web.ptsystemsmenu.service.PtSystemsMenuService;

@Service("ptSystemsMenuService")
public class PtSystemsMenuServiceImpl implements PtSystemsMenuService {

	@Resource
	private PtBasFunConfMapper ptBasFunConfMapper;

	@Override
	public BasFunConf getOneBasFunConf(Map<String, Object> map) {
		return ptBasFunConfMapper.getOneBasFunConf(map);
	}
}
