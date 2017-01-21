package com.krm.voteplateform.web.task.model;

import java.math.BigDecimal;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

/**
 * 平台任务日志信息
 * 
 * @author JohnnyZhang
 */
public class PtSysTaskLog extends BaseEntity {

	private static final long serialVersionUID = 2161926181035309293L;
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 执行任务日期
	 */
	private String taskRunDate;
	/**
	 * 执行时间
	 */
	private String taskRunTime;
	/**
	 * 执行次数
	 */
	private BigDecimal taskRunNum;
	/**
	 * 执行状态
	 */
	private String taskRunStatus;
	/**
	 * 错误日志文件路径
	 */
	private String taskRunLogPath;

	/**
	 * TASK_DATE
	 * 
	 * @return
	 */
	private String taskDate;

	public String getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}

	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskRunDate() {
		return taskRunDate;
	}

	public void setTaskRunDate(String taskRunDate) {
		this.taskRunDate = taskRunDate;
	}

	public String getTaskRunTime() {
		return taskRunTime;
	}

	public void setTaskRunTime(String taskRunTime) {
		this.taskRunTime = taskRunTime;
	}

	public BigDecimal getTaskRunNum() {
		return taskRunNum;
	}

	public void setTaskRunNum(BigDecimal taskRunNum) {
		this.taskRunNum = taskRunNum;
	}

	public String getTaskRunStatus() {
		return taskRunStatus;
	}

	public void setTaskRunStatus(String taskRunStatus) {
		this.taskRunStatus = taskRunStatus;
	}

	public String getTaskRunLogPath() {
		return taskRunLogPath;
	}

	public void setTaskRunLogPath(String taskRunLogPath) {
		this.taskRunLogPath = taskRunLogPath;
	}
}
