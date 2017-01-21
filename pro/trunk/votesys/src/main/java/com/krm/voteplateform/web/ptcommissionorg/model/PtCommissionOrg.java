package com.krm.voteplateform.web.ptcommissionorg.model;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtCommissionOrg extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String porgid;
	
	private String orgname;
	
	private String orgtype;
	
	private int orgorder;
	
	private String orgremark;
	
	private String state;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPorgid() {
		return porgid;
	}

	public void setPorgid(String porgid) {
		this.porgid = porgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public int getOrgorder() {
		return orgorder;
	}

	public void setOrgorder(int orgorder) {
		this.orgorder = orgorder;
	}

	public String getOrgremark() {
		return orgremark;
	}

	public void setOrgremark(String orgremark) {
		this.orgremark = orgremark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "PtCommissionOrg [code=" + code + ", porgid=" + porgid + ", orgname=" + orgname + ", orgtype=" + orgtype
				+ ", orgorder=" + orgorder + ", orgremark=" + orgremark + ", state=" + state + "]";
	}
	
	

}
