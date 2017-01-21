package com.krm.voteplateform.web.menwingrouconf.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.menwingrouconf.dao.BasMenWinGrouConfMapper;
import com.krm.voteplateform.web.menwingrouconf.model.BasMenWinGrouConf;
import com.krm.voteplateform.web.menwingrouconf.service.BasMenWinGrouConfService;

/**
 * 菜单窗体拓展明细分组配置表操作相关
 */
@Service("basMenWinGrouConfService")
public class BasMenWinGrouConfServiceImpl implements BasMenWinGrouConfService {

	Logger logger = LoggerFactory.getLogger(BasMenWinGrouConfServiceImpl.class);

	@Autowired
	private BasMenWinGrouConfMapper basMenWinGrouConfMapper;

	@Override
	public void saveBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf) {
		basMenWinGrouConfMapper.saveBasMenWinGrouConf(basMenWinGrouConf);
	}

	@Override
	public void updateBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf) {
		basMenWinGrouConfMapper.updateBasMenWinGrouConf(basMenWinGrouConf);
	}

	@Override
	public void removeBasMenWinGrouConf(BasMenWinGrouConf basMenWinGrouConf) {
		basMenWinGrouConfMapper.deleteBasMenWinGrouConf(basMenWinGrouConf);
	}

	@Override
	public BasMenWinGrouConf loadById(String id) {
		return basMenWinGrouConfMapper.findById(id);
	}



}
