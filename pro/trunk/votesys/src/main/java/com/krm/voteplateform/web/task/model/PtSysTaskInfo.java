package com.krm.voteplateform.web.task.model;

import java.math.BigDecimal;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

/**
 * 系统任务信息
 * 
 * @author JohnnyZhang
 */
public class PtSysTaskInfo extends BaseEntity {
	private static final long serialVersionUID = -4741461640793431514L;
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 任务说明
	 */
	private String taskExplain;
	/**
	 * 依赖运行任务编号
	 */
	private String relyTask;
	/**
	 * 任务运行日期
	 */
	private String taskRuneDate;
	/**
	 * 任务切换时间点
	 */
	private String taskRuneTime;
	/**
	 * 任务状态 0-停用，1-启用
	 */
	private String taskStatus;
	/**
	 * 0-执行类文件，1-执行shell文件
	 */
	private String taskType;
	/**
	 * 任务执行的类\文件
	 */
	private String taskBody;
	/**
	 * 执行需要资源量
	 */
	private BigDecimal taskResource;
	/**
	 * 执行方法名
	 */
	private String taskMethod;

	/**
	 * 执行配置
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskExplain() {
		return taskExplain;
	}

	public void setTaskExplain(String taskExplain) {
		this.taskExplain = taskExplain;
	}

	public String getRelyTask() {
		return relyTask;
	}

	public void setRelyTask(String relyTask) {
		this.relyTask = relyTask;
	}

	public String getTaskRuneDate() {
		return taskRuneDate;
	}

	public void setTaskRuneDate(String taskRuneDate) {
		this.taskRuneDate = taskRuneDate;
	}

	public String getTaskRuneTime() {
		return taskRuneTime;
	}

	public void setTaskRuneTime(String taskRuneTime) {
		this.taskRuneTime = taskRuneTime;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskBody() {
		return taskBody;
	}

	public void setTaskBody(String taskBody) {
		this.taskBody = taskBody;
	}

	public BigDecimal getTaskResource() {
		return taskResource;
	}

	public void setTaskResource(BigDecimal taskResource) {
		this.taskResource = taskResource;
	}

	public String getTaskMethod() {
		return taskMethod;
	}

	public void setTaskMethod(String taskMethod) {
		this.taskMethod = taskMethod;
	}
}
