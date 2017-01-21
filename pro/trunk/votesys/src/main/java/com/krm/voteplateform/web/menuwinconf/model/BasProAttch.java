package com.krm.voteplateform.web.menuwinconf.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.ColumnIgnore;
import org.springframework.web.multipart.MultipartFile;

public class BasProAttch implements Serializable {
	private static final long serialVersionUID = 4139984969057883766L;

	private String id;

	private String projectId;

	private String kindId;

	private String kindName;

	private String originalName;

	private String otherStsValue;

	private String orderCode;

	private String delFlag = "0";

	private MultipartFile file;

	private Timestamp createTime;

	private String createBy;

	private Timestamp updateTime;

	private String updateBy;

	private List<BasProAttch> attchList;

	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getKindId() {
		return kindId;
	}

	public void setKindId(String kindId) {
		this.kindId = kindId;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getOtherStsValue() {
		return otherStsValue;
	}

	public void setOtherStsValue(String otherStsValue) {
		this.otherStsValue = otherStsValue;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@ColumnIgnore(insert = true, update = true)
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@ColumnIgnore(insert = true, update = true)
	public List<BasProAttch> getAttchList() {
		return attchList;
	}

	public void setAttchList(List<BasProAttch> attchList) {
		this.attchList = attchList;
	}

}
