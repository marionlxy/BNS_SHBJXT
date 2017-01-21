package com.krm.voteplateform.autotask.task;

import java.io.File;

import org.slf4j.Logger;

import com.krm.voteplateform.common.utils.LogUtils;
import com.krm.voteplateform.web.task.run.TaskExcuResult;

/**
 * 判断CD软件传输的文件是否到达
 * 
 * @author JohnnyZhang
 */
public class CdFilesExistTask {

	private static Logger logger = LogUtils.getTaskLog();
	public static final String OK_LOGFILE_NAME = "OK.log";

	/**
	 * 
	 * @param path
	 * @param taskDate
	 * @param taskId
	 */
	public TaskExcuResult checkCdFilesExistTask(String path, String taskDate, String taskId) {
		logger.info("开始运行判断CD软件传输的文件是否到达任务，参数[请求文件路径:{},任务日期:{},任务编号:{}]", path, taskDate, taskId);
		// 格式化日期
		taskDate = taskDate.replace("-", "");
		// 若路径不以路径分隔符号结束
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		path += taskDate;
		File file = new File(path);
		// 如果路径不存在
		if (!file.exists() || file.list() == null || file.list().length == 0) {
			logger.error("文件夹" + path + "不存在或文件夹内无文件");
			return TaskExcuResult.TASKURNERRFILEDEF;
		}
		File[] listFiles = file.listFiles();
		boolean flag = false;
		for (File file2 : listFiles) {
			if (OK_LOGFILE_NAME.equals(file2.getName())) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			logger.error("文件夹" + path + "不存在文件" + OK_LOGFILE_NAME);
			return TaskExcuResult.TASKURNERRFILEDEF;
		}
		logger.info("CD软件传输的文件是否到达任务成功结束，参数[请求文件路径:{},任务日期:{},任务编号:{}]", path, taskDate, taskId);
		return TaskExcuResult.TASKURNERRSUC;
	}
}
