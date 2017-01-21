package com.krm.voteplateform.web.task.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.task.model.PtSysTaskInfo;

/**
 * 
 * @author JohnnyZhang
 *
 */
public interface PtSysTaskInfoService {

	int updateSysTaskInfo(PtSysTaskInfo ptSysTaskInfo);

	List<PtSysTaskInfo> queryNoChanageTaskList(String date);

	int updateByIdSelective(PtSysTaskInfo ptSysTaskInfo);

	List<Map<String, Object>> getAllTaskList();

	int updatePtSysTaskInfo(PtSysTaskInfo ptSysTaskInfo);

	/**
	 * 查询启用任务中，任务运行记录状态为[2.依赖未满足, 3.进入队列,9.任务失败重做 10.忽略依赖]
	 * 
	 * @return
	 */
	List<PtSysTaskInfo> queryALLList();

}
