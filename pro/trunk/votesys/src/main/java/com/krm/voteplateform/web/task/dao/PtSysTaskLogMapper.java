package com.krm.voteplateform.web.task.dao;

import java.util.List;

import com.krm.voteplateform.web.task.model.PtSysTaskLog;

/**
 * 平台任务日志Dao
 * 
 * @author JohnnyZhang
 */
public interface PtSysTaskLogMapper {
	
	int insert(PtSysTaskLog sysTaskLog);
	
	int updateSysTaskLogStatus(PtSysTaskLog sysTaskLog);

	int backupsSysTaskLog(PtSysTaskLog sysTaskLog);

	int deleteSysTaskLog(PtSysTaskLog sysTaskLog);

	int deleteSysTaskLog1(PtSysTaskLog sysTaskLog);

	List<PtSysTaskLog> getTaskLogbyTaskid(PtSysTaskLog sysTaskLog);
}
