package com.krm.voteplateform.web.menuwinconf.model;

import java.io.Serializable;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

/**
* 
*/
public class BasMenuWinConf implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 排序号
	 */
	private String orderCode;
	/**
	 * 是否使用
	 */
	private String useFlag;
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
	/**
	 * 组ID
	 */
	private String groupid;
	/**
	 * 
	 */
	private String id;
	/**
	 * 名称
	 */
	private String functionCode;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 
	 */
	private String name;
	/**
	 * 是否为空
	 */
	private String nullFlag;
	/**
	 * 采集框类型
	 */
	private String collectType;
	/**
	 * 采集框数据源
	 */
	private String datasource;
	/**
	 * 绑定字段
	 */
	private String bindFiled;
	/**
	 * 绑定事件或URL
	 */
	private String bindEventOrUrl;
	
	/**
	 * 是否可更改
	 */
	private String alterFlag;
	/**
	 * 是否为基本项
	 */
	private String basicFlag;
	
	public String getBasicFlag() {
		return basicFlag;
	}
	public void setBasicFlag(String basicFlag) {
		this.basicFlag = basicFlag;
	}
	public String getAlterFlag() {
		return alterFlag;
	}
	public void setAlterFlag(String alterFlag) {
		this.alterFlag = alterFlag;
	}
	public void setOrderCode(String orderCode){
		this.orderCode=orderCode;
	}
	public String getOrderCode(){
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
	
	public void setGroupid(String groupid){
		this.groupid=groupid;
	}
	public String getGroupid(){
		return this.groupid;
	}
	
	public void setId(String id){
		this.id=id;
	}
	@AssignID("uuid32")
	public String getId(){
		return this.id;
	}
	
	public void setFunctionCode(String functionCode){
		this.functionCode=functionCode;
	}
	public String getFunctionCode(){
		return this.functionCode;
	}
	
	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return this.type;
	}
	
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
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
	
	public void setBindEventOrUrl(String bindEventOrUrl){
		this.bindEventOrUrl=bindEventOrUrl;
	}
	public String getBindEventOrUrl(){
		return this.bindEventOrUrl;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("BasMenuWinConf[");
		sb.append("orderCode=");
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
		sb.append(",groupid=");
		sb.append(groupid);
		sb.append(",id=");
		sb.append(id);
		sb.append(",functionCode=");
		sb.append(functionCode);
		sb.append(",type=");
		sb.append(type);
		sb.append(",name=");
		sb.append(name);
		sb.append(",nullFlag=");
		sb.append(nullFlag);
		sb.append(",collectType=");
		sb.append(collectType);
		sb.append(",datasource=");
		sb.append(datasource);
		sb.append(",bindFiled=");
		sb.append(bindFiled);
		sb.append(",bindEventOrUrl=");
		sb.append(bindEventOrUrl);
		sb.append(",alterFlag=");
		sb.append(alterFlag);
		sb.append("]");
		return sb.toString();
	}
}
