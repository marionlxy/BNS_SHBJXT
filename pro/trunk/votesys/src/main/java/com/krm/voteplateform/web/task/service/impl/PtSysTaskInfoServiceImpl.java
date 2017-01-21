package com.krm.voteplateform.web.task.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.krm.voteplateform.web.task.dao.PtSysTaskInfoMapper;
import com.krm.voteplateform.web.task.model.PtSysTaskInfo;
import com.krm.voteplateform.web.task.service.PtSysTaskInfoService;

/**
 * 
 * @author JohnnyZhang
 *
 */
@Service("ptSysTaskInfoService")
public class PtSysTaskInfoServiceImpl implements PtSysTaskInfoService{

	@Resource
	private PtSysTaskInfoMapper ptSysTaskInfoMapper;

	@Override
	public List<PtSysTaskInfo> queryALLList() {
		return ptSysTaskInfoMapper.queryALLList();
	}

	@Override
	public int updatePtSysTaskInfo(PtSysTaskInfo ptSysTaskInfo) {
		return ptSysTaskInfoMapper.updateById(ptSysTaskInfo);
	}

	@Override
	public List<Map<String, Object>> getAllTaskList() {
		return ptSysTaskInfoMapper.getAllTaskList();
	}

	@Override
	public int updateByIdSelective(PtSysTaskInfo ptSysTaskInfo) {
		return ptSysTaskInfoMapper.updateByIdSelective(ptSysTaskInfo);
	}

	@Override
	public List<PtSysTaskInfo> queryNoChanageTaskList(String date) {
		PtSysTaskInfo ptSysTaskInfo = new PtSysTaskInfo();
		ptSysTaskInfo.setTaskDate(date);
		return ptSysTaskInfoMapper.queryNoChanageTaskList(ptSysTaskInfo);
	}

	@Override
	public int updateSysTaskInfo(PtSysTaskInfo ptSysTaskInfo) {
		return ptSysTaskInfoMapper.updateByIdSelective(ptSysTaskInfo);
	}
}
