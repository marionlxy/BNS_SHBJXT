package com.krm.voteplateform.web.commission.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

/**
 * 委员会角色表
 * 
 * @author JohnnyZhang
 *
 */
public class PtCommissionRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2310599965842971740L;

	/** 主键 */
	private String crid;
	/** 编码 */
	private String code;
	/** 角色名称 */
	private String rolename;
	
	private String roleCategory;

	public String getRoleCategory() {
		return roleCategory;
	}

	public void setRoleCategory(String roleCategory) {
		this.roleCategory = roleCategory;
	}

	/**
	 * @return the crid
	 */
	@AssignID("uuid32")
	public String getCrid() {
		return crid;
	}

	/**
	 * @param crid the crid to set
	 */
	public void setCrid(String crid) {
		this.crid = crid;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the rolename
	 */
	public String getRolename() {
		return rolename;
	}

	/**
	 * @param rolename the rolename to set
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
