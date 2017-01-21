package com.krm.voteplateform.web.task.dao;

import java.util.List;

import com.krm.voteplateform.web.task.model.PtSysTaskParam;

/**
 * 平台任务参数Dao
 * 
 * @author JohnnyZhang
 */
public interface PtSysTaskParamMapper {
	List<PtSysTaskParam> getSysTaskParamList(String taskId);
}
