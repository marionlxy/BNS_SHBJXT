package com.krm.voteplateform.web.task.run;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.common.utils.ExceptionCrashUtils;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.common.utils.LogUtils;
import com.krm.voteplateform.common.utils.Reflections;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.constants.SysContants;
import com.krm.voteplateform.web.task.dao.PtSysTaskParamMapper;
import com.krm.voteplateform.web.task.model.PtSysTaskInfo;
import com.krm.voteplateform.web.task.model.PtSysTaskLog;
import com.krm.voteplateform.web.task.model.PtSysTaskParam;
import com.krm.voteplateform.web.task.service.PtSysTaskInfoService;
import com.krm.voteplateform.web.task.service.PtSysTaskLogService;
import com.krm.voteplateform.web.util.PropertiesUtils;

/**
 * 任务管理核心类
 * 
 * @author JohnnyZhang
 */
@Component
public class TaskJobRun {

	public static Logger logger = LogUtils.getTaskLog();

	@Resource
	private PtSysTaskInfoService ptSysTaskInfoService;

	@Resource
	private PtSysTaskLogService ptSysTaskLogService;

	@Resource
	private PtSysTaskParamMapper ptSysTaskParamMapper;

	public static final String filepath = PropertiesUtils.getValue(SysContants.TASKLOG_ROOTPATH_KEY);

	private static List<String> lists = Lists.newArrayList(TaskExcuResult.TASKURNAFDATE.getStatusValue(),
			TaskExcuResult.TASKURNAFTASK.getStatusValue(), TaskExcuResult.TASKURNINQUER.getStatusValue(),
			TaskExcuResult.TASKURNERRINRUN.getStatusValue(), TaskExcuResult.TASKURNERRERR.getStatusValue(),
			TaskExcuResult.TASKURNERRHLERR.getStatusValue(), TaskExcuResult.TASKURNERRERRRUN.getStatusValue(),
			TaskExcuResult.TASKURNERRHLRNE.getStatusValue(), TaskExcuResult.TASKURNERRFILEDEF.getStatusValue());

	/**
	 * 定时启动任务
	 */
	public void runTask() {
		// 定时切日
		changetaskDate();
		// 查询出每一个需要执行的JALLOB
		// 查询启用任务中，任务运行记录状态为[2.依赖未满足, 3.进入队列,9.任务失败重做 10.忽略依赖 11.数据文件未到达]
		List<PtSysTaskInfo> taskInfoL = ptSysTaskInfoService.queryALLList();
		// 获取当前时间
		for (int i = 0; i < taskInfoL.size(); i++) {
			PtSysTaskInfo sysTaskInfo = taskInfoL.get(i);
			logger.info("进入执行队列" + sysTaskInfo.getTaskName());
			final String ClassName = sysTaskInfo.getTaskBody();
			final String MethodName = sysTaskInfo.getTaskMethod();
			final String taskType = sysTaskInfo.getTaskType();
			final String id = sysTaskInfo.getId();
			// final String taskDate = sysTaskInfo.getTaskDate();
			String taskstatus = "";
			final String taskRuneDate = sysTaskInfo.getTaskRuneDate();
			// 取得指定任务ID与运行日期的平台任务运行记录
			List<PtSysTaskLog> sysTaskL = ptSysTaskLogService.getTaskLogbyTaskid(id, sysTaskInfo.getTaskRuneDate());
			// 查询出
			if (sysTaskL.size() > 0
					&& !TaskExcuResult.TASKURNERRHLRNE.getStatusValue().equals(sysTaskL.get(0).getTaskRunStatus())) {
				if (compareDate(sysTaskInfo.getTaskRuneDate(), sysTaskInfo.getTaskDate())) {
					taskstatus = TaskExcuResult.TASKURNINQUER.getStatusValue();
				} else {
					taskstatus = TaskExcuResult.TASKURNAFDATE.getStatusValue();
				}
				// 判断运行依赖的作业是否运行成功 true 允许成功 false是
				if (checkBefTask(sysTaskInfo.getRelyTask(), sysTaskInfo.getTaskRuneDate())) {
					taskstatus = TaskExcuResult.TASKURNINQUER.getStatusValue();
				} else {
					taskstatus = TaskExcuResult.TASKURNAFTASK.getStatusValue();
				}
				ptSysTaskLogService.updateSysTaskLogStatus(taskstatus, "", sysTaskInfo.getId(), taskRuneDate);
			}
			if (taskstatus.equals(TaskExcuResult.TASKURNAFTASK.getStatusValue())
					|| taskstatus.equals(TaskExcuResult.TASKURNAFDATE.getStatusValue()) || taskstatus.equals("")) {
				continue;
			}
			// 更新状态
			ptSysTaskLogService.updateSysTaskLogStatus(taskstatus, "", sysTaskInfo.getId(), taskRuneDate);
			// 日期符合的跑批，日期
			Thread r = new Thread() {
				public void run() {
					// 更新状态为4
					ptSysTaskLogService.updateSysTaskLogStatus(TaskExcuResult.TASKURNERRINRUN.getStatusValue(), "", id,
							taskRuneDate);
					// 判断
					// 执行shell脚本
					if ("1".equals(taskType)) {
						try {
							String isSurrce = runShshell(ClassName, id, taskRuneDate);
							TaskExcuResult taskExcuResult = TaskExcuResult.ALL_MAP.get(isSurrce);
							if (taskExcuResult != null) {
								ptSysTaskLogService.updateSysTaskLogStatus(taskExcuResult.getStatusValue(), "", id,
										taskRuneDate);
							} else {
								ptSysTaskLogService.updateSysTaskLogStatus(
										TaskExcuResult.TASKURNERRSUC.getStatusValue(), "", id, taskRuneDate);
							}
						} catch (IOException e) {
							manageErrLog(e, id, taskRuneDate, "调用方法异常");
						} catch (InterruptedException e) {
							manageErrLog(e, id, taskRuneDate, "调用方法异常");
						}
					} else if ("0".equals(taskType)) {// 执行类文件
						runClassjob(ClassName, MethodName, id, taskRuneDate);
					}
				}
			};
			r.start();
		}
	}

	/**
	 * 
	 * @param taskId 前置任务ID
	 * @param taskRunDate 任务执行时间
	 * @return true 依赖执行成功，false 依赖没有执行或执行异常
	 */
	public boolean checkBefTask(String taskId, String taskRunDate) {
		if (null == taskId || "".equals(taskRunDate)) {
			return true;
		} else {
			List<PtSysTaskLog> sysTaskL = ptSysTaskLogService.getTaskLogbyTaskid(taskId, taskRunDate);
			String[] sysTaskArray = taskId.split(",");
			if (sysTaskArray.length > sysTaskL.size()) {
				return false;
			} else {
				boolean b = true;
				for (int i = 0; i < sysTaskL.size(); i++) {
					PtSysTaskLog sysT = sysTaskL.get(i);
					if (lists.contains(sysT.getTaskRunStatus())) {
						b = false;
						break;
					}
				}
				return b;
			}
		}
	}

	/**
	 * 
	 * @param taskrundate
	 * @param taskdate
	 * @return
	 */
	public boolean compareDate(String taskrundate, String taskdate) {
		if ("yyyymmdd".equalsIgnoreCase(taskdate)) {
			return true;
		} else if ("yyyymmed".equalsIgnoreCase(taskdate)) {
			// 判断是否是月末日期
			return DateUtils.isLastDayOfMonth(taskrundate);
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param name 执行命令语句
	 * @param id 执行任务ID
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String runShshell(String name, String id, String taskDate) throws IOException, InterruptedException {
		// 获取参数名称
		name = "sh " + name;
		String[] parameters = getshellParameter(id, taskDate);
		// 执行任务ID
		Runtime rt = Runtime.getRuntime();
		if (parameters != null && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				name += " " + parameters[i];
			}
		}
		Process exec = rt.exec(name);
		// 定义shell返回值
		String result = null;
		// 获取shell返回流
		BufferedInputStream in = new BufferedInputStream(exec.getInputStream());
		// 字符流转换字节流
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		exec.waitFor();
		String lineStr;
		while ((lineStr = br.readLine()) != null) {
			result = lineStr;
		}
		// 关闭输入流
		br.close();
		in.close();
		return result;
	}

	/**
	 * 
	 * @param name 执行命令语句
	 * @param id 执行任务ID
	 * @throws Exception
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public void runClassjob(String Classname, String methodName, String id, String taskDate) {
		try {
			String status = TaskExcuResult.TASKURNERRSUC.getStatusValue();
			// 获取参数名称
			Object[] parameters = getClassParameter(id, taskDate);
			Class<?> beanClass = Class.forName(Classname);
			Object benaObj = beanClass.newInstance();
			Object result = Reflections.invokeMethodByName(benaObj, methodName, parameters);
			if (result instanceof TaskExcuResult) {
				TaskExcuResult tr = (TaskExcuResult) result;
				status = tr.getStatusValue();
			}
			ptSysTaskLogService.updateSysTaskLogStatus(status, "", id, taskDate);
		} catch (ClassNotFoundException e) {
			manageErrLog(e, id, taskDate, "找不到调用任务类");
		} catch (IllegalAccessException e) {
			manageErrLog(e, id, taskDate, "调用方法出错,参数错误");
		} catch (InstantiationException e) {
			manageErrLog(e, id, taskDate, "调用方法出错");
		} catch (Exception e) {
			manageErrLog(e, id, taskDate, "调用方法异常");
		}
	}

	public String[] getshellParameter(String id, String taskDate) {
		List<PtSysTaskParam> taskPL = ptSysTaskParamMapper.getSysTaskParamList(id);
		if (taskPL != null && taskPL.size() != 0) {
			String[] parameters = new String[taskPL.size()];
			for (int i = 0; i < taskPL.size(); i++) {
				PtSysTaskParam sysTaskParam = (PtSysTaskParam) taskPL.get(i);
				// 1代表是 日期值 2:int,3:double,4：string
				if ("1".equals(sysTaskParam.getParamType())) {
					parameters[i] = DateUtils.formatDate(DateUtils.parseDate(taskDate), sysTaskParam.getParamValue());
				} else {
					parameters[i] = sysTaskParam.getParamValue();
				}
			}
			return parameters;
		} else {

			return null;
		}
	}

	/**
	 * 获取参数
	 * 
	 * @param id 任务ID
	 * @param taskDate 任务日期
	 * @return
	 */
	public Object[] getClassParameter(String id, String taskDate) {
		List<PtSysTaskParam> taskPL = ptSysTaskParamMapper.getSysTaskParamList(id);
		if (taskPL != null && taskPL.size() != 0) {
			Object[] parameters = new Object[taskPL.size()];
			for (int i = 0; i < taskPL.size(); i++) {
				PtSysTaskParam sysTaskParam = (PtSysTaskParam) taskPL.get(i);
				// 1代表是 日期值 2:int,3:double,4：string
				if ("1".equals(sysTaskParam.getParamType())) {
					parameters[i] = DateUtils.formatDate(DateUtils.parseDate(taskDate), sysTaskParam.getParamValue());
				} else if ("2".equals(sysTaskParam.getParamType())) {
					parameters[i] = Integer.parseInt(sysTaskParam.getParamValue());
				} else {
					parameters[i] = sysTaskParam.getParamValue();
				}
			}
			return parameters;
		} else {

			return null;
		}

	}

	/**
	 * 系统切换日期
	 */
	public void changetaskDate() {
		String time = DateUtils.getTime();// 得到时间格式转化的为HH:mm:ss
		String date = DateUtils.getDate();// 得到日期格式转化的为yyyy-mm-dd
		String nowHour = time.substring(0, 2);// 取得当前的小时数
		// 查找当天没有切日的任务
		List<PtSysTaskInfo> taskInfoL = ptSysTaskInfoService.queryNoChanageTaskList(date);
		// 给没有切日的任务切日
		for (int i = 0; i < taskInfoL.size(); i++) {
			PtSysTaskInfo sysTaskInfo = taskInfoL.get(i);
			// 取得当前任务切换时间点
			String taskRuneTime = sysTaskInfo.getTaskRuneTime();
			if (nowHour.equals(taskRuneTime.substring(0, 2))) {
				// 符合时间的进行切日
				String newsystaskdate = DateUtils.formatDate(DateUtils.getaferdate(DateUtils.parseDate(sysTaskInfo
						.getTaskRuneDate())));
				sysTaskInfo.setTaskRuneDate(newsystaskdate);
				ptSysTaskInfoService.updateSysTaskInfo(sysTaskInfo);
				// 添加运行日志
				PtSysTaskLog sysTaskLog = new PtSysTaskLog();
				sysTaskLog.setId(UUIDGenerator.getUUID());
				sysTaskLog.setTaskId(sysTaskInfo.getId());
				sysTaskLog.setTaskRunDate(date);
				sysTaskLog.setTaskRunNum(new BigDecimal(0));
				sysTaskLog.setTaskRunStatus(TaskExcuResult.TASKURNINQUER.getStatusValue());
				sysTaskLog.setTaskRunTime(time);
				sysTaskLog.setTaskDate(newsystaskdate);
				sysTaskLog.setTaskRunLogPath("");
				ptSysTaskLogService.insertSysTaskLog(sysTaskLog);
			}
		}
	}

	/**
	 * 错误信息保存到日志文件中
	 * 
	 * @param e
	 * @param id
	 * @param taskDate
	 * @param errstr
	 */
	public void manageErrLog(Exception e, String id, String taskDate, String errstr) {
		String taskDatel = taskDate;
		taskDatel = taskDatel.replaceAll("-", "");
		String filePaths = filepath + File.separator + id + "-" + taskDatel + ".log";
		try {
			String crashReport = ExceptionCrashUtils.getCrashReport(e);
			FileUtils.writeFile(crashReport, filePaths);
		} catch (Exception e1) {
			// ignore exception
		} finally {
			ptSysTaskLogService.updateSysTaskLogStatus(TaskExcuResult.TASKURNERRERR.getStatusValue(), filePaths, id,
					taskDate);
		}
	}

	/**
	 * 错误信息保存到日志文件中
	 * 
	 * @param e
	 * @param id
	 * @param taskDate
	 * @param errstr
	 */
	public static void writeErrLog(Exception e, String id, String taskDate, String errstr) {
		try {
			String taskDatel = taskDate;
			taskDatel = taskDatel.replaceAll("-", "");
			String filePaths = filepath + File.separator + id + "-" + taskDatel + ".log";
			String crashReport = ExceptionCrashUtils.getCrashReport(e);
			FileUtils.writeFile(crashReport, filePaths);
		} catch (Exception e1) {
			// ignore
		}
	}

}
