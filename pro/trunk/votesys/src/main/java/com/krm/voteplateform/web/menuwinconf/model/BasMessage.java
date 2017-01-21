package com.krm.voteplateform.web.menuwinconf.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class BasMessage extends BaseEntity{
	
	
	private static final long serialVersionUID = 1L;
	private String id; //主键
	private String code; //编码
	private String sysText; //平台文本
	private String commText;//委员会文本
	private String enableFlag;//启用
	private String description;//说明
	private String  delFlag;//删除标志
	private Date createTime;//创建时间
	private String createBy;//创建人
	private Date updateTime;//修改时间
	private String updateBy;//修改人
	
	@AssignID("uuid32")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSysText() {
		return sysText;
	}
	public void setSysText(String sysText) {
		this.sysText = sysText;
	}
	public String getCommText() {
		return commText;
	}
	public void setCommText(String commText) {
		this.commText = commText;
	}
	public String getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "BasMessage [id=" + id + ", code=" + code + ", sysText=" + sysText + ", commText=" + commText
				+ ", enableFlag=" + enableFlag + ", description=" + description + ", delFlag=" + delFlag
				+ ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + "]";
	}
	
	

}
