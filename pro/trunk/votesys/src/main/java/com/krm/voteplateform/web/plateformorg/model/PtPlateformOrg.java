package com.krm.voteplateform.web.plateformorg.model;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtPlateformOrg extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String parentId;
	
	private String orgname;
	
	private int sort;
	
	private String code;
	
	private String orgtype;

	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	@Override
	public String toString() {
		return "PtPlateformOrg [id=" + id + ", parentId=" + parentId + ", orgname=" + orgname + ", sort=" + sort
				+ ", code=" + code + "orgtype="+orgtype+"]";
	}
	
	
	
	

}
