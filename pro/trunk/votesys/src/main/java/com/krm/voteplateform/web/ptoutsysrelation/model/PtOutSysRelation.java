package com.krm.voteplateform.web.ptoutsysrelation.model;

import java.sql.Timestamp;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtOutSysRelation extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String code;
	
	private String relationCode;
	
	private String relationName;
	
	private String notifyAddr;
	
	private Timestamp updateTime;

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

	public String getRelationCode() {
		return relationCode;
	}

	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getNotifyAddr() {
		return notifyAddr;
	}

	public void setNotifyAddr(String notifyAddr) {
		this.notifyAddr = notifyAddr;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "PtOutSysRelation [id=" + id + ", code=" + code + ", relationCode=" + relationCode + ", relationName="
				+ relationName + ", notifyAddr=" + notifyAddr + ", updateTime=" + updateTime + "]";
	}
	
	
	

}
