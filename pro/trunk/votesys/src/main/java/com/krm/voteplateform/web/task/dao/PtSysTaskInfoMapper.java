package com.krm.voteplateform.web.task.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.task.model.PtSysTaskInfo;

/**
 * 平台任务信息Dao
 * 
 * @author JohnnyZhang
 */
public interface PtSysTaskInfoMapper {

	int updateByIdSelective(PtSysTaskInfo sysTaskInfo);

	int updateById(PtSysTaskInfo sysTaskInfo);

	List<PtSysTaskInfo> queryALLList();

	List<PtSysTaskInfo> queryNoChanageTaskList(PtSysTaskInfo sysTaskInfo);

	List<Map<String, Object>> getAllTaskList();
}
