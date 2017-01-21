package com.krm.voteplateform.common.glue.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.krm.voteplateform.common.base.entity.BaseEntity;

/**
 * 动态代码表
 * 
 * @author JohnnyZhang
 */
public class PtDynamicSource extends BaseEntity {

	private static final long serialVersionUID = 3426836553532887259L;

	private String id;// ID
	private String sampleName;// 简称
	private String description;// 说明
	private String javaCode;// 代码
	private String dyType;// 类型
	private String updateTime;// 更新时间

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the javaCode
	 */
	public String getJavaCode() {
		return javaCode;
	}

	/**
	 * @param javaCode the javaCode to set
	 */
	public void setJavaCode(String javaCode) {
		this.javaCode = javaCode;
	}

	/**
	 * @return the dyType
	 */
	public String getDyType() {
		return dyType;
	}

	/**
	 * @param dyType the dyType to set
	 */
	public void setDyType(String dyType) {
		this.dyType = dyType;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the sampleName
	 */
	public String getSampleName() {
		return sampleName;
	}

	/**
	 * @param sampleName the sampleName to set
	 */
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
