package com.krm.voteplateform.web.task.service;

import java.util.List;

import com.krm.voteplateform.web.task.model.PtSysTaskLog;

/**
 * 平台任务日志表
 * 
 * @author JohnnyZhang
 */
public interface PtSysTaskLogService {

	void saveTaskRest(String id, String taskDate);

	int saveSysTaskLogBak(String id, String taskDate, String date);

	int deleteSysTaskLog1(String id, String taskDate);

	int deleteSysTaskLog(String id, String taskDate, String date);

	int insertSysTaskLog(PtSysTaskLog sysTaskLog);

	int updateSysTaskLogStatus(String taskRunStatus, String filePath, String taskId, String taskDate);

	/**
	 * 取得指定任务ID与运行日期的平台任务运行记录
	 * @param taskId
	 * @param taskRunDate
	 * @return
	 */
	List<PtSysTaskLog> getTaskLogbyTaskid(String taskId, String taskRunDate);

}
