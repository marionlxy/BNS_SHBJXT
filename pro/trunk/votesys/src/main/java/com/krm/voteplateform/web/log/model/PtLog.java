package com.krm.voteplateform.web.log.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

/**
 * 
 * @author
 */
public class PtLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String id; // id <主键id>

	private String sysFlag;

	private String description; // description <>

	private String exception; // exception <异常信息>

	private String method; // method <操作方式>

	private String params; // params <操作提交的数据>

	private String remoteAddr; // remote_addr <操作IP地址>

	private String requestUri; // request_uri <请求URI>

	private String type; // type <日志类型>

	private String userAgent; // user_agent <用户代理>

	private String createBy; // create_by <创建者>
	private Date createTime; // create_date <创建时间>

	public static final String TYPE_ACCESS = "1"; // 操作日志
	public static final String TYPE_EXCEPTION = "2"; // 异常日志

	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
