package com.krm.voteplateform.web.menwingrouconf.model;

import java.io.Serializable;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

/**
* 菜单窗体拓展明细分组配置表
*/
public class BasMenWinGrouConf implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String id;
	/**
	 * 菜单窗体配置表主键
	 */
	private String bmwcId;
	/**
	 * 拓展明细分组主键
	 */
	private String bxgId;
	/**
	 * 项目扩展明细表主键
	 */
	private String bredId;
	/**
	 * 是否为空——0:可为空 1:不可为空
	 */
	private String nullFlag;
	/**
	 * 采集框类型
0:文本框 1:下拉框 2:日期框 3:数字框
	 */
	private String collectType;
	/**
	 * 
	 */
	private String datasource;
	/**
	 * 
	 */
	private String bindFiled;
	/**
	 * 
	 */
	private Integer orderCode;
	/**
	 * 
	 */
	private String useFlag;
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
	
	private String alterFlag;
	
	public String getAlterFlag() {
		return alterFlag;
	}
	public void setAlterFlag(String alterFlag) {
		this.alterFlag = alterFlag;
	}
	public void setId(String id){
		this.id=id;
	}
	@AssignID("uuid32")
	public String getId(){
		return this.id;
	}
	
	public void setBmwcId(String bmwcId){
		this.bmwcId=bmwcId;
	}
	public String getBmwcId(){
		return this.bmwcId;
	}
	
	public void setBxgId(String bxgId){
		this.bxgId=bxgId;
	}
	public String getBxgId(){
		return this.bxgId;
	}
	
	public void setBredId(String bredId){
		this.bredId=bredId;
	}
	public String getBredId(){
		return this.bredId;
	}
	
	public void setNullFlag(String nullFlag){
		this.nullFlag=nullFlag;
	}
	public String getNullFlag(){
		return this.nullFlag;
	}
	
	public void setCollectType(String collectType){
		this.collectType=collectType;
	}
	public String getCollectType(){
		return this.collectType;
	}
	
	public void setDatasource(String datasource){
		this.datasource=datasource;
	}
	public String getDatasource(){
		return this.datasource;
	}
	
	public void setBindFiled(String bindFiled){
		this.bindFiled=bindFiled;
	}
	public String getBindFiled(){
		return this.bindFiled;
	}
	
	public void setOrderCode(Integer orderCode){
		this.orderCode=orderCode;
	}
	public Integer getOrderCode(){
		return this.orderCode;
	}
	
	public void setUseFlag(String useFlag){
		this.useFlag=useFlag;
	}
	public String getUseFlag(){
		return this.useFlag;
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
		sb.append("BasMenWinGrouConf[");
		sb.append("id=");
		sb.append(id);
		sb.append(",bmwcId=");
		sb.append(bmwcId);
		sb.append(",bxgId=");
		sb.append(bxgId);
		sb.append(",bredId=");
		sb.append(bredId);
		sb.append(",nullFlag=");
		sb.append(nullFlag);
		sb.append(",collectType=");
		sb.append(collectType);
		sb.append(",datasource=");
		sb.append(datasource);
		sb.append(",bindFiled=");
		sb.append(bindFiled);
		sb.append(",orderCode=");
		sb.append(orderCode);
		sb.append(",useFlag=");
		sb.append(useFlag);
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
		sb.append(",alterFlag=");
		sb.append(alterFlag);
		sb.append("]");
		return sb.toString();
	}
}
