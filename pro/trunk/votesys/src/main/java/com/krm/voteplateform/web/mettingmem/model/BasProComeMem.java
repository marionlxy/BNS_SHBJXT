package com.krm.voteplateform.web.mettingmem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.beetl.sql.core.annotatoin.AssignID;

/**
* 
*/
public class BasProComeMem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String delFlag;
	/**
	 * 
	 */
	private Timestamp createTime;
	/**
	 * 
	 */
	private String createBy;
	/**
	 * 
	 */
	private String createName;
	/**
	 * 
	 */
	private String createIp;
	/**
	 * 
	 */
	private Timestamp updateTime;
	/**
	 * 
	 */
	private String updateBy;
	/**
	 * 
	 */
	private String updateName;
	/**
	 * 
	 */
	private String updateIp;
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String projectId;
	/**
	 * 
	 */
	private String userCode;
	/**
	 * 
	 */
	private String userName;
	/**
	 * 
	 */
	private String userIp;
	/**
	 * 
	 */
	private String voteResult;
	/**
	 * 
	 */
	private String suggestion;
	/**
	 * 
	 */
	private Integer orderCode;
	
	public void setDelFlag(String delFlag){
		this.delFlag=delFlag;
	}
	public String getDelFlag(){
		return this.delFlag;
	}
	
	public void setCreateTime(Timestamp createTime){
		this.createTime=createTime;
	}
	public Timestamp getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateBy(String createBy){
		this.createBy=createBy;
	}
	public String getCreateBy(){
		return this.createBy;
	}
	
	public void setCreateName(String createName){
		this.createName=createName;
	}
	public String getCreateName(){
		return this.createName;
	}
	
	public void setCreateIp(String createIp){
		this.createIp=createIp;
	}
	public String getCreateIp(){
		return this.createIp;
	}
	
	public void setUpdateTime(Timestamp updateTime){
		this.updateTime=updateTime;
	}
	public Timestamp getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateBy(String updateBy){
		this.updateBy=updateBy;
	}
	public String getUpdateBy(){
		return this.updateBy;
	}
	
	public void setUpdateName(String updateName){
		this.updateName=updateName;
	}
	public String getUpdateName(){
		return this.updateName;
	}
	
	public void setUpdateIp(String updateIp){
		this.updateIp=updateIp;
	}
	public String getUpdateIp(){
		return this.updateIp;
	}
	
	public void setId(String id){
		this.id=id;
	}
	@AssignID("uuid32")
	public String getId(){
		return this.id;
	}
	
	public void setProjectId(String projectId){
		this.projectId=projectId;
	}
	public String getProjectId(){
		return this.projectId;
	}
	
	public void setUserCode(String userCode){
		this.userCode=userCode;
	}
	public String getUserCode(){
		return this.userCode;
	}
	
	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getUserName(){
		return this.userName;
	}
	
	public void setUserIp(String userIp){
		this.userIp=userIp;
	}
	public String getUserIp(){
		return this.userIp;
	}
	
	public void setVoteResult(String voteResult){
		this.voteResult=voteResult;
	}
	public String getVoteResult(){
		return this.voteResult;
	}
	
	public void setSuggestion(String suggestion){
		this.suggestion=suggestion;
	}
	public String getSuggestion(){
		return this.suggestion;
	}
	
	public void setOrderCode(Integer orderCode){
		this.orderCode=orderCode;
	}
	public Integer getOrderCode(){
		return this.orderCode;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("BasProComeMem[");
		sb.append("delFlag=");
		sb.append(delFlag);
		sb.append(",createTime=");
		sb.append(createTime);
		sb.append(",createBy=");
		sb.append(createBy);
		sb.append(",createName=");
		sb.append(createName);
		sb.append(",createIp=");
		sb.append(createIp);
		sb.append(",updateTime=");
		sb.append(updateTime);
		sb.append(",updateBy=");
		sb.append(updateBy);
		sb.append(",updateName=");
		sb.append(updateName);
		sb.append(",updateIp=");
		sb.append(updateIp);
		sb.append(",id=");
		sb.append(id);
		sb.append(",projectId=");
		sb.append(projectId);
		sb.append(",userCode=");
		sb.append(userCode);
		sb.append(",userName=");
		sb.append(userName);
		sb.append(",userIp=");
		sb.append(userIp);
		sb.append(",voteResult=");
		sb.append(voteResult);
		sb.append(",suggestion=");
		sb.append(suggestion);
		sb.append(",orderCode=");
		sb.append(orderCode);
		sb.append("]");
		return sb.toString();
	}
}
