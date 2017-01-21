package com.krm.voteplateform.web.menulistconf.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;
import com.krm.voteplateform.web.sys.model.BasDict;

public class BasMenuListConf extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String resid;
	
	private String dicid;
	
	private String searchFlag;
	
	private String searchType;
	
	private String datasource;
	
	private String useFlag;
	
	private String delFlag;
	
	private Date createTime;
	
	private String createBy;
	
	private Date updateTime;
	
	private String updateBy;
	
	private BasDict ptSysDic;
	
	public BasDict getPtSysDic() {
		return ptSysDic;
	}

	public void setPtSysDic(BasDict ptSysDic) {
		this.ptSysDic = ptSysDic;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResid() {
		return resid;
	}

	public void setResid(String resid) {
		this.resid = resid;
	}

	public String getDicid() {
		return dicid;
	}

	public void setDicid(String dicid) {
		this.dicid = dicid;
	}

	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	public String toString() {
		return "BasMenuListConf [id=" + id + ", resid=" + resid + ", dicid=" + dicid + ", searchFlag=" + searchFlag
				+ ", searchType=" + searchType + ", datasource=" + datasource + ", useFlag=" + useFlag + ", delFlag="
				+ delFlag + ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime
				+ ", updateBy=" + updateBy + ", ptSysDic=" + ptSysDic + "]";
	}

	
	
	
	

}
