package com.krm.voteplateform.web.sys.model;

import java.sql.Timestamp;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;
import com.krm.voteplateform.common.mybatis.plugins.DyTableModel;

public class BasDict extends DyTableModel {
	
	private static final long serialVersionUID = -5548609949462605986L;
	//
	private String id;
	
	//英文名称
	private String enName;
	
	//中文名称
	private String cnName;
	
	//数据类型   01:数字  02:字符串  03:编码&名称  04:日期  05:金额
	private String dataType;
	
	//数据长度
	private Integer dateLenget;
	
	//精度
	private Integer precision;
	
	//映射名称
	private String mapCnName;
	
	//映射精度
	private Integer mapPrecision;
	
	//允许为空  0:可为空  1:不可为空
	private Integer  nullFlag;
	
	//类别  01:会议字典 02:项目字典 03:申请分类明细字典
	//04:附件字典  05:项目到会人员字典 06:会议到会人员字典 07:项目拓展明细字典
	private String category;
	
	//平台标志
	private Integer plateFlag;
	
	//排序
	private Integer sort;
	//删除标志  0：正常；1：删除
	private String delFlag;
	
	//创建时间
	private Timestamp createTime;
	
	//创建人
	private String createBy;
	
	//update_time
	private Timestamp updateTime;
	
	//更改人
	private String updateBy;
	
	//状态
	private String state;
	
	
	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setDateType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getDateLenget() {
		return dateLenget;
	}

	public void setDateLenget(Integer dateLenget) {
		this.dateLenget = dateLenget;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getPlateFlag() {
		return plateFlag;
	}

	public void setPlateFlag(Integer plateFlag) {
		this.plateFlag = plateFlag;
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}



	public void setUpdateTime(Timestamp date) {
		this.updateTime = date;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "BasDict [id=" + id + ", enName=" + enName + ", cnName=" + cnName + ", dataType=" + dataType
				+ ", dateLenget=" + dateLenget + ", precision=" + precision + ", mapCnName=" + mapCnName
				+ ", mapPrecision=" + mapPrecision + ", nullFlag=" + nullFlag + ", category=" + category
				+ ", plateFlag=" + plateFlag + ", sort=" + sort + ", delFlag=" + delFlag + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", state="
				+ state + "]";
	}

	
	
	
	

}
