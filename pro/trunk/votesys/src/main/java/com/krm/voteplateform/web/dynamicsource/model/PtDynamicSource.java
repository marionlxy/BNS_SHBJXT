package com.krm.voteplateform.web.dynamicsource.model;

import java.sql.Timestamp;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtDynamicSource extends BaseEntity{
	private static final long serialVersionUID = -5548609949462605986L;
	private String id;
	
	private String sampleName;
	
	private String description;
	
	private String javaCode;
	
	private String dyType;
	
	private Timestamp updateTime;
	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJavaCode() {
		return javaCode;
	}

	public void setJavaCode(String javaCode) {
		this.javaCode = javaCode;
	}

	public String getDyType() {
		return dyType;
	}

	public void setDyType(String dyType) {
		this.dyType = dyType;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	
	
	
	
}