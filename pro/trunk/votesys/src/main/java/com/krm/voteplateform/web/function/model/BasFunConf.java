package com.krm.voteplateform.web.function.model;

import java.sql.Timestamp;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class BasFunConf extends BaseEntity {
	private static final long serialVersionUID = -5548609949462605986L;
	
	private String skipClassMethod;
	
	private String id;
	
	private String fcid ;
	
	private String mlcid;
	
	private String functionCode;
	
	private String functionName;
	
	private String type;
	
	private String sysText;
	
	private String commtext;
	
	private String bindEventOrUrl;
	
	private String useFlag;
	
	private String delFlag;
	
	private Timestamp craeteTime;
	
	private String createBy;
	
	private Timestamp updateTime;
	
	private String updateBy;
	
	private String tempId;
	
	private String icon;
	
	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	private String tempName;

	
	public String getFcid() {
		return fcid;
	}

	public void setFcid(String fcid) {
		this.fcid = fcid;
	}

	public String getMlcid() {
		return mlcid;
	}

	public void setMlcid(String mlcid) {
		this.mlcid = mlcid;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSysText() {
		return sysText;
	}

	public void setSysText(String sysText) {
		this.sysText = sysText;
	}

	public String getCommtext() {
		return commtext;
	}

	public void setCommtext(String commtext) {
		this.commtext = commtext;
	}

	public String getBindEventOrUrl() {
		return bindEventOrUrl;
	}

	public void setBindEventOrUrl(String bindEventOrUrl) {
		this.bindEventOrUrl = bindEventOrUrl;
	}

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}



	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime){
		this.updateTime=updateTime;
	}
	
	public Timestamp getCraeteTime() {
		return craeteTime;
	}

	public void setCraeteTime(Timestamp craeteTime) {
		this.craeteTime = craeteTime;
	}

	public String getSkipClassMethod() {
		return skipClassMethod;
	}

	public void setSkipClassMethod(String skipClassMethod) {
		this.skipClassMethod = skipClassMethod;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "BasFunConf [id=" + id + ", fcid=" + fcid + ", mlcid=" + mlcid + ", functionCode=" + functionCode
				+ ", functionName=" + functionName + ", type=" + type + ", sysText=" + sysText + ", commtext="
				+ commtext + ", bindEventOrUrl=" + bindEventOrUrl + ", useFlag=" + useFlag + ", delFlag=" + delFlag
				+ ", craeteTime=" + craeteTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + "]";
	}
	
	
	
	
}
