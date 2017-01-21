package com.krm.voteplateform.web.proattchtype.model;

import java.sql.Timestamp;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class BasProAttchType extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String attchName;
	
	private String msFlag;
	
	private String delFlag;
	
	private Timestamp createTime;
	
	private String createBy;
	
	private Timestamp updateTime;
	
	private String updateBy;
	
	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttchName() {
		return attchName;
	}

	public void setAttchName(String attchName) {
		this.attchName = attchName;
	}

	public String getMsFlag() {
		return msFlag;
	}

	public void setMsFlag(String msFlag) {
		this.msFlag = msFlag;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	public String toString() {
		return "BasProAttchType [id=" + id + ", attchName=" + attchName + ", msFlag=" + msFlag + ", delFlag=" + delFlag
				+ ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + "]";
	}
	
	

}
