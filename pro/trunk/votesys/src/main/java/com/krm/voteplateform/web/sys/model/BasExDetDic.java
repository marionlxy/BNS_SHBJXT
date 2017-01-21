package com.krm.voteplateform.web.sys.model;




import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;
import com.krm.voteplateform.common.mybatis.plugins.DyTableModel;

public class BasExDetDic extends DyTableModel {
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String groupId;
	private String dicId;
	private String enName;
	private String cnName;
	private String dataType;
	private Integer dataLength;
	private Integer precision;
	private String mapCnName;
	private Integer mapPrecision;
	private Integer nullFlag;
	private Integer plateFlag;
	private Integer state;
	private Integer sort;
	private String delFlag;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;
	
	
	@AssignID("uuid32")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getDicId() {
		return dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getDataLength() {
		return dataLength;
	}
	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}
	public Integer getPrecision() {
		return precision;
	}
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	public String getMapCnName() {
		return mapCnName;
	}
	public void setMapCnName(String mapCnName) {
		this.mapCnName = mapCnName;
	}
	public Integer getMapPrecision() {
		return mapPrecision;
	}
	public void setMapPrecision(Integer mapPrecision) {
		this.mapPrecision = mapPrecision;
	}
	public Integer getNullFlag() {
		return nullFlag;
	}
	public void setNullFlag(Integer nullFlag) {
		this.nullFlag = nullFlag;
	}
	public Integer getPlateFlag() {
		return plateFlag;
	}
	public void setPlateFlag(Integer plateFlag) {
		this.plateFlag = plateFlag;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "BasExDetDic [id=" + id + ", groupId=" + groupId + ", dicId=" + dicId + ", enName=" + enName
				+ ", cnName=" + cnName + ", dataType=" + dataType + ", dataLength=" + dataLength + ", precision="
				+ precision + ", mapCnName=" + mapCnName + ", mapPrecision=" + mapPrecision + ", nullFlag=" + nullFlag
				+ ", plateFlag=" + plateFlag + ", state=" + state + ", sort=" + sort + ", delFlag=" + delFlag
				+ ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + "]";
	}


	
	
	
	
	
	
	

}
