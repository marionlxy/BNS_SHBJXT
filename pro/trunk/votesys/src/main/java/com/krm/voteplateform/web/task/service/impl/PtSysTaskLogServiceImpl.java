package com.krm.voteplateform.web.task.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.task.dao.PtSysTaskLogMapper;
import com.krm.voteplateform.web.task.model.PtSysTaskLog;
import com.krm.voteplateform.web.task.run.TaskExcuResult;
import com.krm.voteplateform.web.task.service.PtSysTaskLogService;

/**
 * 平台任务日志表
 * 
 * @author JohnnyZhang
 */
@Service("ptSysTaskLogService")
public class PtSysTaskLogServiceImpl implements PtSysTaskLogService {

	@Resource
	private PtSysTaskLogMapper ptSysTaskLogMapper;

	@Override
	public List<PtSysTaskLog> getTaskLogbyTaskid(String taskId, String taskRunDate) {
		PtSysTaskLog sysTaskLog = new PtSysTaskLog();
		sysTaskLog.setTaskId(taskId);
		sysTaskLog.setTaskDate(taskRunDate);
		return ptSysTaskLogMapper.getTaskLogbyTaskid(sysTaskLog);
	}

	@Override
	public int updateSysTaskLogStatus(String taskRunStatus, String filePath, String taskId, String taskDate) {
		PtSysTaskLog sysTaskLog = new PtSysTaskLog();
		sysTaskLog.setTaskRunStatus(taskRunStatus);
		sysTaskLog.setTaskRunLogPath(filePath);
		sysTaskLog.setTaskId(taskId);
		sysTaskLog.setTaskDate(taskDate);
		return ptSysTaskLogMapper.updateSysTaskLogStatus(sysTaskLog);
	}

	@Override
	public int insertSysTaskLog(PtSysTaskLog sysTaskLog) {
		return ptSysTaskLogMapper.insert(sysTaskLog);
	}

	@Override
	public int deleteSysTaskLog(String id, String taskDate, String date) {
		PtSysTaskLog sysTaskLog = new PtSysTaskLog();
		sysTaskLog.setTaskId(id);
		sysTaskLog.setTaskDate(taskDate);
		sysTaskLog.setTaskRunDate(date);
		return ptSysTaskLogMapper.deleteSysTaskLog(sysTaskLog);
	}

	@Override
	public int deleteSysTaskLog1(String id, String taskDate) {
		PtSysTaskLog sysTaskLog = new PtSysTaskLog();
		sysTaskLog.setTaskId(id);
		sysTaskLog.setTaskDate(taskDate);
		return ptSysTaskLogMapper.deleteSysTaskLog1(sysTaskLog);
	}

	@Override
	public int saveSysTaskLogBak(String id, String taskDate, String date) {
		PtSysTaskLog sysTaskLog = new PtSysTaskLog();
		sysTaskLog.setTaskId(id);
		sysTaskLog.setTaskDate(taskDate);
		sysTaskLog.setTaskRunDate(date);
		return ptSysTaskLogMapper.backupsSysTaskLog(sysTaskLog);
	}

	/**
	 * 任务重置
	 * 
	 * @param id 重置任务id
	 * @param taskDate 重置日期
	 */
	@Override
	public void saveTaskRest(String id, String taskDate) {
		// 任务重置
		String time = DateUtils.getTime();// 得到时间格式转化的为HH:mm:ss
		String date = DateUtils.getDate();// 得到日期格式转化的为yyyy-mm-dd
		// 先备份重置日期
		saveSysTaskLogBak(id, taskDate, date);
		deleteSysTaskLog(id, taskDate, date);
		PtSysTaskLog sysTaskLog = new PtSysTaskLog();
		sysTaskLog.setId(UUIDGenerator.getUUID());
		sysTaskLog.setTaskId(id);
		sysTaskLog.setTaskRunDate(date);
		sysTaskLog.setTaskRunNum(new BigDecimal(0));
		sysTaskLog.setTaskRunStatus(TaskExcuResult.TASKURNINQUER.getStatusValue());
		sysTaskLog.setTaskRunTime(time);
		sysTaskLog.setTaskDate(taskDate);
		sysTaskLog.setTaskRunLogPath("");
		insertSysTaskLog(sysTaskLog);
	}
}
