package com.krm.voteplateform.web.ptvoterestmpl.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

/**
* 
*/
public class PtVoteResTmpl extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private String commssionName;
	/**
	 * 
	 */
	private String suggShowFlag;
	/**
	 * 
	 */
	private String deptShowFlag;
	/**
	 * 
	 */
	private String signShowFlag;
	/**
	 * 
	 */
	private String delFlag;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String createBy;
	/**
	 * 
	 */
	private Date updateTime;
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
	
	public void setCommssionName(String commssionName){
		this.commssionName=commssionName;
	}
	public String getCommssionName(){
		return this.commssionName;
	}
	
	public void setSuggShowFlag(String suggShowFlag){
		this.suggShowFlag=suggShowFlag;
	}
	public String getSuggShowFlag(){
		return this.suggShowFlag;
	}
	
	public void setDeptShowFlag(String deptShowFlag){
		this.deptShowFlag=deptShowFlag;
	}
	public String getDeptShowFlag(){
		return this.deptShowFlag;
	}
	
	public void setSignShowFlag(String signShowFlag){
		this.signShowFlag=signShowFlag;
	}
	public String getSignShowFlag(){
		return this.signShowFlag;
	}
	
	public void setDelFlag(String delFlag){
		this.delFlag=delFlag;
	}
	public String getDelFlag(){
		return this.delFlag;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateBy(String createBy){
		this.createBy=createBy;
	}
	public String getCreateBy(){
		return this.createBy;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	public Date getUpdateTime(){
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
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
