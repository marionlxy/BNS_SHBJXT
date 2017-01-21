package com.krm.voteplateform.web.ptcommissionuser.model;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtCommissionUser extends  BaseEntity{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1530522117918596329L;

	private String code;
	
	private String puid;
	
	private String userName;
	
	private String state;
	
	private String viewFlag;
	
	private int orderCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	public int getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}