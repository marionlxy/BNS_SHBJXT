package com.krm.voteplateform.web.task.model;

import java.math.BigDecimal;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

/**
 * 平台系统任务参数
 * 
 * @author JohnnyZhang
 */
public class PtSysTaskParam extends BaseEntity {

	private static final long serialVersionUID = 3373566087577112163L;
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 参数名称
	 */
	private String paramName;
	/**
	 * 参数类值
	 */
	private String paramValue;
	/**
	 * 参数描述
	 */
	private String paramDescribe;
	/**
	 * 参数状态
	 */
	private String paramStatus;
	/**
	 * 1-string,2-int,3-double
	 */
	private String paramType;

	/**
	 */
	private BigDecimal orderby;

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

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamDescribe() {
		return paramDescribe;
	}

	public void setParamDescribe(String paramDescribe) {
		this.paramDescribe = paramDescribe;
	}

	public String getParamStatus() {
		return paramStatus;
	}

	public void setParamStatus(String paramStatus) {
		this.paramStatus = paramStatus;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public BigDecimal getOrderby() {
		return orderby;
	}

	public void setOrderby(BigDecimal orderby) {
		this.orderby = orderby;
	}
}
