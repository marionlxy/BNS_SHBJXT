package com.krm.voteplateform.web.mettingmem.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.beetl.sql.core.annotatoin.AssignID;

/**
* 
*/
public class BasMettingMem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String memAuthorityFlagName;
	/**
	 * 
	 */
	private String memPostName;
	/**
	 * 
	 */
	private String id;
	/**
	 * 会议外键ID
	 */
	private String conferenceId;
	/**
	 * 人员代码
	 */
	private String memUserCode;
	/**
	 * 人员名称
	 */
	private String memUserName;
	/**
	 * 所在机构
	 */
	private String memOrgId;
	/**
	 * 所在机构名称
	 */
	private String memOrgName;
	/**
	 * 职务id
	 */
	private String memPostId;
	/**
	 * 角色id
	 */
	private String memRoleId;
	/**
	 * 角色名称
	 */
	private String memRoleName;
	/**
	 * 计算机地址
	 */
	private String memUserIp;
	/**
	 * 是否有表决权
	 */
	private String memAuthorityFlagId;
	/**
	 * 排序
	 */
	private Integer memOrderCode;
	/**
	 * 删除标记
	 */
	private String delFlag;
	/**
	 * 记录创建时间
	 */
	private Timestamp createTime;
	/**
	 * 记录创建人编码 
	 */
	private String createBy;
	/**
	 * 记录创建人姓名
	 */
	private String createName;
	/**
	 * 创建者计算机地址 
	 */
	private String createIp;
	/**
	 * 最后修改时间
	 */
	private Timestamp updateTime;
	/**
	 * 最后修改人编码 
	 */
	private String updaetBy;
	/**
	 * 最后修改人姓名
	 */
	private String updateName;
	/**
	 * 
	 */
	private String updateIp;
	
	public void setMemAuthorityFlagName(String memAuthorityFlagName){
		this.memAuthorityFlagName=memAuthorityFlagName;
	}
	public String getMemAuthorityFlagName(){
		return this.memAuthorityFlagName;
	}
	
	public void setMemPostName(String memPostName){
		this.memPostName=memPostName;
	}
	public String getMemPostName(){
		return this.memPostName;
	}
	
	public void setId(String id){
		this.id=id;
	}
	@AssignID("uuid32")
	public String getId(){
		return this.id;
	}
	
	public void setConferenceId(String conferenceId){
		this.conferenceId=conferenceId;
	}
	public String getConferenceId(){
		return this.conferenceId;
	}
	
	public void setMemUserCode(String memUserCode){
		this.memUserCode=memUserCode;
	}
	public String getMemUserCode(){
		return this.memUserCode;
	}
	
	public void setMemUserName(String memUserName){
		this.memUserName=memUserName;
	}
	public String getMemUserName(){
		return this.memUserName;
	}
	
	public void setMemOrgId(String memOrgId){
		this.memOrgId=memOrgId;
	}
	public String getMemOrgId(){
		return this.memOrgId;
	}
	
	public void setMemOrgName(String memOrgName){
		this.memOrgName=memOrgName;
	}
	public String getMemOrgName(){
		return this.memOrgName;
	}
	
	public void setMemPostId(String memPostId){
		this.memPostId=memPostId;
	}
	public String getMemPostId(){
		return this.memPostId;
	}
	
	public void setMemRoleId(String memRoleId){
		this.memRoleId=memRoleId;
	}
	public String getMemRoleId(){
		return this.memRoleId;
	}
	
	public void setMemRoleName(String memRoleName){
		this.memRoleName=memRoleName;
	}
	public String getMemRoleName(){
		return this.memRoleName;
	}
	
	public void setMemUserIp(String memUserIp){
		this.memUserIp=memUserIp;
	}
	public String getMemUserIp(){
		return this.memUserIp;
	}
	
	public void setMemAuthorityFlagId(String memAuthorityFlagId){
		this.memAuthorityFlagId=memAuthorityFlagId;
	}
	public String getMemAuthorityFlagId(){
		return this.memAuthorityFlagId;
	}
	
	public void setMemOrderCode(Integer memOrderCode){
		this.memOrderCode=memOrderCode;
	}
	public Integer getMemOrderCode(){
		return this.memOrderCode;
	}
	
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
	
	public void setUpdaetBy(String updaetBy){
		this.updaetBy=updaetBy;
	}
	public String getUpdaetBy(){
		return this.updaetBy;
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
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("BasMettingMem[");
		sb.append("memAuthorityFlagName=");
		sb.append(memAuthorityFlagName);
		sb.append(",memPostName=");
		sb.append(memPostName);
		sb.append(",id=");
		sb.append(id);
		sb.append(",conferenceId=");
		sb.append(conferenceId);
		sb.append(",memUserCode=");
		sb.append(memUserCode);
		sb.append(",memUserName=");
		sb.append(memUserName);
		sb.append(",memOrgId=");
		sb.append(memOrgId);
		sb.append(",memOrgName=");
		sb.append(memOrgName);
		sb.append(",memPostId=");
		sb.append(memPostId);
		sb.append(",memRoleId=");
		sb.append(memRoleId);
		sb.append(",memRoleName=");
		sb.append(memRoleName);
		sb.append(",memUserIp=");
		sb.append(memUserIp);
		sb.append(",memAuthorityFlagId=");
		sb.append(memAuthorityFlagId);
		sb.append(",memOrderCode=");
		sb.append(memOrderCode);
		sb.append(",delFlag=");
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
		sb.append(",updaetBy=");
		sb.append(updaetBy);
		sb.append(",updateName=");
		sb.append(updateName);
		sb.append(",updateIp=");
		sb.append(updateIp);
		sb.append("]");
		return sb.toString();
	}
}
