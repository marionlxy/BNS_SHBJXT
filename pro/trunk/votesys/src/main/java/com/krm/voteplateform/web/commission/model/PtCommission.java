package com.krm.voteplateform.web.commission.model;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtCommission extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5548609949462605986L;
		
	/**
	 * 
	 */
	private String id;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 系统标题
	 */
	private String sysTitle;
	/**
	 * 系统简称
	 */
	private String simpleTitle;
	/**
	 * 
	 */
	private String logo;
	/**
	 * 表决标志 0.表决 1.不需要表决
	 */
	private Integer voteFlag;
	/**
	 * 
	 */
	private String votePlanId;
	/**
	 * 
	 */
	private String votePlanName;
	/**
	 * 
	 */
	private String templateId;
	/**
	 * 
	 */
	private String suggestFlag;
	/**
	 * 
	 */
	private Integer oneMindFlag;
	/**
	 * 
	 */
	private String memberIds;
	/**
	 * 
	 */
	private String memberNames;
	/**
	 * 
	 */
	private Integer offineFlag;
	/**
	 * 
	 */
	private Integer humanSignFlag;
	/**
	 * 
	 */
	private Integer state;
	/**
	 * 
	 */
	private Integer sort;
	/**
	 * 
	 */
	private String delFlag;
	/**
	 * 
	 */
	private String createTime;
	/**
	 * 
	 */
	private String createBy;
	/**
	 * 
	 */
	private String updateTime;
	/**
	 * 
	 */
	private String updateBy;
	
	public void setId(String id){
		this.id=id;
	}
	@AssignID("uuid32")
	public String getId(){
		return this.id;
	}
	
	public void setCode(String code){
		this.code=code;
	}
	public String getCode(){
		return this.code;
	}
	
	public void setSysTitle(String sysTitle){
		this.sysTitle=sysTitle;
	}
	public String getSysTitle(){
		return this.sysTitle;
	}
	
	public void setSimpleTitle(String simpleTitle){
		this.simpleTitle=simpleTitle;
	}
	public String getSimpleTitle(){
		return this.simpleTitle;
	}
	
	public void setLogo(String logo){
		this.logo=logo;
	}
	public String getLogo(){
		return this.logo;
	}
	
	public void setVoteFlag(Integer voteFlag){
		this.voteFlag=voteFlag;
	}
	public Integer getVoteFlag(){
		return this.voteFlag;
	}
	
	public void setVotePlanId(String votePlanId){
		this.votePlanId=votePlanId;
	}
	public String getVotePlanId(){
		return this.votePlanId;
	}
	
	public void setVotePlanName(String votePlanName){
		this.votePlanName=votePlanName;
	}
	public String getVotePlanName(){
		return this.votePlanName;
	}
	
	public void setTemplateId(String templateId){
		this.templateId=templateId;
	}
	public String getTemplateId(){
		return this.templateId;
	}
	
	public void setSuggestFlag(String suggestFlag){
		this.suggestFlag=suggestFlag;
	}
	public String getSuggestFlag(){
		return this.suggestFlag;
	}
	
	public void setOneMindFlag(Integer oneMindFlag){
		this.oneMindFlag=oneMindFlag;
	}
	public Integer getOneMindFlag(){
		return this.oneMindFlag;
	}
	
	public void setMemberIds(String memberIds){
		this.memberIds=memberIds;
	}
	public String getMemberIds(){
		return this.memberIds;
	}
	
	public void setMemberNames(String memberNames){
		this.memberNames=memberNames;
	}
	public String getMemberNames(){
		return this.memberNames;
	}
	
	public void setOffineFlag(Integer offineFlag){
		this.offineFlag=offineFlag;
	}
	public Integer getOffineFlag(){
		return this.offineFlag;
	}
	
	public void setHumanSignFlag(Integer humanSignFlag){
		this.humanSignFlag=humanSignFlag;
	}
	public Integer getHumanSignFlag(){
		return this.humanSignFlag;
	}
	
	public void setState(Integer state){
		this.state=state;
	}
	public Integer getState(){
		return this.state;
	}
	
	public void setSort(Integer sort){
		this.sort=sort;
	}
	public Integer getSort(){
		return this.sort;
	}
	
	public void setDelFlag(String delFlag){
		this.delFlag=delFlag;
	}
	public String getDelFlag(){
		return this.delFlag;
	}
	
	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateBy(String createBy){
		this.createBy=createBy;
	}
	public String getCreateBy(){
		return this.createBy;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime=updateTime;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateBy(String updateBy){
		this.updateBy=updateBy;
	}
	public String getUpdateBy(){
		return this.updateBy;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("PtCommission[");
		sb.append("id=");
		sb.append(id);
		sb.append(",code=");
		sb.append(code);
		sb.append(",sysTitle=");
		sb.append(sysTitle);
		sb.append(",simpleTitle=");
		sb.append(simpleTitle);
		sb.append(",logo=");
		sb.append(logo);
		sb.append(",voteFlag=");
		sb.append(voteFlag);
		sb.append(",votePlanId=");
		sb.append(votePlanId);
		sb.append(",votePlanName=");
		sb.append(votePlanName);
		sb.append(",templateId=");
		sb.append(templateId);
		sb.append(",suggestFlag=");
		sb.append(suggestFlag);
		sb.append(",oneMindFlag=");
		sb.append(oneMindFlag);
		sb.append(",memberIds=");
		sb.append(memberIds);
		sb.append(",memberNames=");
		sb.append(memberNames);
		sb.append(",offineFlag=");
		sb.append(offineFlag);
		sb.append(",humanSignFlag=");
		sb.append(humanSignFlag);
		sb.append(",state=");
		sb.append(state);
		sb.append(",sort=");
		sb.append(sort);
		sb.append(",delFlag=");
		sb.append(delFlag);
		sb.append(",createTime=");
		sb.append(createTime);
		sb.append(",createBy=");
		sb.append(createBy);
		sb.append(",updateTime=");
		sb.append(updateTime);
		sb.append(",updateBy=");
		sb.append(updateBy);
		sb.append("]");
		return sb.toString();
	}
	
}
