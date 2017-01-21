package com.krm.voteplateform.web.bascode.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;



/**
* @author lixiaoyang
* 代码
*/
public class BasCode extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 编码分组外键
	 */
	private String cgId;
	/**
	 * 编码
	 */
	private String ecode;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 映射名称
	 */
	private String mapName;
	/**
	 * 启用阶段代码
	 */
	private String enableCode;
	/**
	 * 启用阶段名称
	 */
	private String enableName;
	/**
	 * 系统标志
	 */
	private Integer sysFlag;
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
	@AssignID("uuid32")
	public String getId(){
		return this.id;
	}
	
	public void setCgId(String cgId){
		this.cgId=cgId;
	}
	public String getCgId(){
		return this.cgId;
	}
	
	public void setEcode(String ecode){
		this.ecode=ecode;
	}
	public String getEcode(){
		return this.ecode;
	}
	
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	
	public void setMapName(String mapName){
		this.mapName=mapName;
	}
	public String getMapName(){
		return this.mapName;
	}
	
	public void setEnableCode(String enableCode){
		this.enableCode=enableCode;
	}
	public String getEnableCode(){
		return this.enableCode;
	}
	
	public void setEnableName(String enableName){
		this.enableName=enableName;
	}
	public String getEnableName(){
		return this.enableName;
	}
	
	public void setSysFlag(Integer sysFlag){
		this.sysFlag=sysFlag;
	}
	public Integer getSysFlag(){
		return this.sysFlag;
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
		StringBuilder sb = new StringBuilder();
		sb.append("BasCode[");
		sb.append("id=");
		sb.append(id);
		sb.append(",cgId=");
		sb.append(cgId);
		sb.append(",ecode=");
		sb.append(ecode);
		sb.append(",name=");
		sb.append(name);
		sb.append(",mapName=");
		sb.append(mapName);
		sb.append(",enableCode=");
		sb.append(enableCode);
		sb.append(",enableName=");
		sb.append(enableName);
		sb.append(",sysFlag=");
		sb.append(sysFlag);
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
