package com.krm.voteplateform.web.ptvotematterconf.model;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

/**
* 
*/
public class PtVoteMatterConf extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 委员会编码
	 */
	private String code;
	/**
	 * 平台文本
	 */
	private String plateformText;
	/**
	 * 委员会文本
	 */
	private String committeeText;
	/**
	 * 类型 01：投票名 02:表决结果
	 */
	private String type;
	/**
	 * 
	 */
	private String val;
	/**
	 * 启用标志:0:启用 1:停用
	 */
	private String enable;
	
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
	
	public void setPlateformText(String plateformText){
		this.plateformText=plateformText;
	}
	public String getPlateformText(){
		return this.plateformText;
	}
	
	public void setCommitteeText(String committeeText){
		this.committeeText=committeeText;
	}
	public String getCommitteeText(){
		return this.committeeText;
	}
	
	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return this.type;
	}
	
	public void setVal(String val){
		this.val=val;
	}
	public String getVal(){
		return this.val;
	}
	
	public void setEnable(String enable){
		this.enable=enable;
	}
	public String getEnable(){
		return this.enable;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("PtVoteMatterConf[");
		sb.append("id=");
		sb.append(id);
		sb.append(",code=");
		sb.append(code);
		sb.append(",plateformText=");
		sb.append(plateformText);
		sb.append(",committeeText=");
		sb.append(committeeText);
		sb.append(",type=");
		sb.append(type);
		sb.append(",val=");
		sb.append(val);
		sb.append(",enable=");
		sb.append(enable);
		sb.append("]");
		return sb.toString();
	}
}
