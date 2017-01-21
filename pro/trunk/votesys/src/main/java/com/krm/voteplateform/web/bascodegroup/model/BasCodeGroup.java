package com.krm.voteplateform.web.bascodegroup.model;

import java.util.Date;

import com.krm.voteplateform.common.base.entity.BaseEntity;

/**
* @author lixiaoyang
* 代码组
*/
public class BasCodeGroup extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String pefPass;
	

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 分组名称
	 */
	private String codeGroupName;
	/**
	 * 上级id
	 */
	private String parentId;
	/**
	 * 状态
	 */
	private Integer state;
	/**
	 * 删除标记
	 */
	private String delFlag;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 更改时间
	 */
	private Date updateTime;
	/**
	 * 更改人
	 */
	private String updateBy;
	
	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return this.id;
	}
	
	public void setCodeGroupName(String codeGroupName){
		this.codeGroupName=codeGroupName;
	}
	public String getCodeGroupName(){
		return this.codeGroupName;
	}
	
	public void setParentId(String parentId){
		this.parentId=parentId;
	}
	public String getParentId(){
		return this.parentId;
	}
	
	public void setState(Integer state){
		this.state=state;
	}
	public Integer getState(){
		return this.state;
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
	
	public String getPefPass() {
		return pefPass;
	}
	public void setPefPass(String pefPass) {
		this.pefPass = pefPass;
	}

	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("BasCodeGroup[");
		sb.append("id=");
		sb.append(id);
		sb.append(",codeGroupName=");
		sb.append(codeGroupName);
		sb.append(",parentId=");
		sb.append(parentId);
		sb.append(",state=");
		sb.append(state);
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
