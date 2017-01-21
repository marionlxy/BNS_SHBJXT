package com.krm.voteplateform.web.ptuserrole.model;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtCommissionUserRole extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String code;//编码
	
	private String puid;//平台用户表id
	
	private String crid;//委员会角色表id

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

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public String getCrid() {
		return crid;
	}

	public void setCrid(String crid) {
		this.crid = crid;
	}

	@Override
	public String toString() {
		return "PtCommissionUserRole [id=" + id + ", code=" + code + ", puid=" + puid + ", crid=" + crid + "]";
	}
	
	
	
	

}
