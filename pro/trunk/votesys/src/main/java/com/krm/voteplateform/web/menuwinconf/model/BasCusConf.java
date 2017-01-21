package com.krm.voteplateform.web.menuwinconf.model;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AssignID;

public class BasCusConf implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String otid;
	
	private String type;
	
	private String val;
	
	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOtid() {
		return otid;
	}

	public void setOtid(String otid) {
		this.otid = otid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return "BasCusConf [id=" + id + ", otid=" + otid + ", type=" + type + ", val=" + val + "]";
	}
	
	

}
