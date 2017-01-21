package com.krm.voteplateform.web.basProject.model;

import java.sql.Timestamp;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.mybatis.plugins.DyTableModel;

public class BasMetting extends DyTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3056816944637065908L;
	
	private String id; 
	private String specMettingCode;
	private String specMettingTitle;
	private String topics;
	private String address;
	private String hostId;
	private String hoteName;
	private String scheduledStartTime;
	private String scheduledEndTime;
	private String startTime;
	private String endTime;
	private String applyBankId;
	private String applyBankName;
	private String joinDeptId;
	private String joinDeptName;
	private String restrictions;
	private String description;
	private String stateId;
	private String stateName;
	private String offineFlagId;
	private String offineFlagName;
	private String delFlag;
	private Timestamp createTime;
	private String createBy;
	private String createName;
	private String createIp;
	private Timestamp updateTime;
	private String updateBy;
	private String updateName;
	private String updateIp;
	private String specApproveorg;
	
	
	@AssignID("uuid32")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSpecMettingCode() {
		return specMettingCode;
	}
	public void setSpecMettingCode(String specMettingCode) {
		this.specMettingCode = specMettingCode;
	}
	public String getSpecMettingTitle() {
		return specMettingTitle;
	}
	public void setSpecMettingTitle(String specMettingTitle) {
		this.specMettingTitle = specMettingTitle;
	}
	public String getTopics() {
		return topics;
	}
	public void setTopics(String topics) {
		this.topics = topics;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public String getHoteName() {
		return hoteName;
	}
	public void setHoteName(String hoteName) {
		this.hoteName = hoteName;
	}
	public String getScheduledStartTime() {
		return scheduledStartTime;
	}
	public void setScheduledStartTime(String scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}
	public String getScheduledEndTime() {
		return scheduledEndTime;
	}
	public void setScheduledEndTime(String scheduledEndTime) {
		this.scheduledEndTime = scheduledEndTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getApplyBankId() {
		return applyBankId;
	}
	public void setApplyBankId(String applyBankId) {
		this.applyBankId = applyBankId;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}
	public String getJoinDeptCodes() {
		return joinDeptId;
	}
	public void setJoinDeptCodes(String joinDeptId) {
		this.joinDeptId = joinDeptId;
	}
	public String getJoinDeptNames() {
		return joinDeptName;
	}
	public void setJoinDeptNames(String joinDeptName) {
		this.joinDeptName = joinDeptName;
	}
	public String getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getOffineFlagId() {
		return offineFlagId;
	}
	public void setOffineFlagId(String offineFlagId) {
		this.offineFlagId = offineFlagId;
	}
	public String getOffineFlagName() {
		return offineFlagName;
	}
	public void setOffineFlagName(String offineFlagName) {
		this.offineFlagName = offineFlagName;
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
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getCreateIp() {
		return createIp;
	}
	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public String getUpdateIp() {
		return updateIp;
	}
	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
	public String getSpecApproveorg() {
		return specApproveorg;
	}
	public void setSpecApproveorg(String specApproveorg) {
		this.specApproveorg = specApproveorg;
	}
	@Override
	public String toString() {
		return "BasMetting [id=" + id + ", specMettingCode=" + specMettingCode + ", specMettingTitle="
				+ specMettingTitle + ", topics=" + topics + ", address=" + address + ", hostId=" + hostId
				+ ", hoteName=" + hoteName + ", scheduledStartTime=" + scheduledStartTime + ", scheduledEndTime="
				+ scheduledEndTime + ", startTime=" + startTime + ", endTime=" + endTime + ", applyBankId="
				+ applyBankId + ", applyBankName=" + applyBankName + ", joinDeptId=" + joinDeptId
				+ ", joinDeptName=" + joinDeptName + ", restrictions=" + restrictions + ", description=" + description
				+ ", stateId=" + stateId + ", stateName=" + stateName + ", offineFlagId=" + offineFlagId
				+ ", offineFlagName=" + offineFlagName + ", delFlag=" + delFlag + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", createName=" + createName + ", createIp=" + createIp + ", updateTime="
				+ updateTime + ", updateBy=" + updateBy + ", updateName=" + updateName + ", updateIp=" + updateIp
				+ ", specApproveorg=" + specApproveorg + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
