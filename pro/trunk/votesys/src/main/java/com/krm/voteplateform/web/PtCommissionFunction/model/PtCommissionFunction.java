package com.krm.voteplateform.web.PtCommissionFunction.model;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtCommissionFunction extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cfid;
	
	private String code;
	
	private String crid;
	
	private String resid;
	
	@AssignID("uuid32")
	public String getCfid() {
		return cfid;
	}

	public void setCfid(String cfid) {
		this.cfid = cfid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCrid() {
		return crid;
	}

	public void setCrid(String crid) {
		this.crid = crid;
	}

	public String getResid() {
		return resid;
	}

	public void setResid(String resid) {
		this.resid = resid;
	}

	@Override
	public String toString() {
		return "PtCommissionFunction [cfid=" + cfid + ", code=" + code + ", crid=" + crid + ", resid=" + resid + "]";
	}
	
	

}
