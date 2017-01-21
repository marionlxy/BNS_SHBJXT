package com.krm.voteplateform.web.resource.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.utils.TreeEntity;

public class BasResource extends TreeEntity{

	/**
	 * 
	 */
private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String resourceCode;
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String parentid;
	/**
	 * 
	 */
	private String sysText;
	/**
	 * 
	 */
	private String commText;
	/**
	 * 
	 */
	private String url;
	/**
	 * 
	 */
	private String autoGenFlag;
	
	private String tempId;
	
	private String tempName;
	
	private String parentIds;
	
	public String getAutoGenFlag() {
		return this.getString("autoGenFlag");
	}

	public String getParentIds() {
		return this.getString("parentIds");
	}

	public void setParentIds(String parentIds) {
		this.set("parentIds", parentIds);
	}

	public void setAutoGenFlag(String autoGenFlag) {
		this.set("autoGenFlag", autoGenFlag);
	}

	public String getTempId() {
		return this.getString("tempId");
	}

	public void setTempId(String tempId) {
		this.set("tempId", tempId);
	}

	public String getTempName() {
		return this.getString("tempName");
	}

	public void setTempName(String tempName) {
		this.set("tempName", tempName);
	}

	private String enableFlag;
	/**
	 * 
	 */
	private String delFlag;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String createBy;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private String updateBy;
	
	
	public String getResourceCode() {
		return this.getString("resourceCode");
	}

	public void setResourceCode(String resourceCode) {
		this.set("resourceCode", resourceCode);
	}

	@AssignID("uuid32")
	public String getId() {
		return this.getString("id");
	}

	public void setId(String id) {
		this.set("id", id);
	}

	public String getType() {
		return this.getString("type");
	}

	public void setType(String type) {
		this.set("type", type);
	}

	public String getParentid() {
		return this.getString("parentid");
	}

	public void setParentid(String parentid) {
		this.set("parentid", parentid);
	}



	public String getSysText() {
		return this.getString("sysText");
	}

	public void setSysText(String sysText) {
		this.set("sysText", sysText);
	}

	public String getCommText() {
		return this.getString("commText");
	}

	public void setCommText(String commText) {
		this.set("commText", commText);
	}

	public String getUrl() {
		return this.getString("url");
	}

	public void setUrl(String url) {
		this.set("url", url);
	}

	public String getEnableFlag() {
		return this.getString("enableFlag");
	}

	public void setEnableFlag(String enableFlag) {
		this.set("enableFlag", enableFlag);
	}

	public String getDelFlag() {
		return this.getString("delFlag");
	}

	public void setDelFlag(String delFlag) {
		this.set("delFlag", delFlag);
	}

	public Date getCreateTime() {
		return this.getDate("createTime");
	}

	public void setCreateTime(Date createTime) {
		this.set("createTime", createTime);
	}

	public String getCreateBy() {
		return this.getString("createBy");
	}

	public void setCreateBy(String createBy) {
		this.set("createBy", createBy);
	}

	public Date getUpdateTime() {
		return this.getDate("updateTime");
	}

	public void setUpdateTime(Date updateTime) {
		this.set("updateTime", updateTime);
	}

	public String getUpdateBy() {
		return this.getString("updateBy");
	}

	public void setUpdateBy(String updateBy) {
		this.set("updateBy", updateBy);
	}


	
	
	

}
