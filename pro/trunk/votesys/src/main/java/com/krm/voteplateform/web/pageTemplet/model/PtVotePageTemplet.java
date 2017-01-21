package com.krm.voteplateform.web.pageTemplet.model;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtVotePageTemplet extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String name;
	
	private String fileName;
	
	private String explain;
	
	private String type;
	
	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PtVotePageTemplet [id=" + id + ", name=" + name + ", fileName=" + fileName + ", explain=" + explain
				+ ", type=" + type + "]";
	}
	
	

}
