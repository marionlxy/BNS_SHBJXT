package com.krm.voteplateform.web.synproject.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.common.utils.LogUtils;
import com.krm.voteplateform.web.synproject.event.SynProjectEvent;
import com.krm.voteplateform.web.synproject.service.StartSyncProjectService;

/**
 * 同步任务服务实现类
 * 
 * @author JohnnyZhang
 */
@Service("startSyncProjectService")
public class StartSyncProjectServiceImpl implements StartSyncProjectService {

	@Override
	public void insertSync(File file) {
		LogUtils.getSynProject().info("注册同步事件");
		SpringContextHolder.getApplicationContext().publishEvent(new SynProjectEvent(file));
	}

}
