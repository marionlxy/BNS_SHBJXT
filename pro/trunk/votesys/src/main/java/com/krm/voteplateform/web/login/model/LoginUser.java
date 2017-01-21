package com.krm.voteplateform.web.login.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.krm.voteplateform.common.base.entity.BaseEntity;
import com.krm.voteplateform.web.commission.model.PtCommission;

/**
 * 登录用户模型类
 * 
 * @author JohnnyZhang
 */
public class LoginUser extends BaseEntity {

	private static final long serialVersionUID = 1734958245142122911L;

	// 所属平台标志
	private String userBelongFlag;

	// ///////////////////////////////////// 委员会属性/////////////////////////////////////////////
	private String sysTitle;

	private Integer state;

	private String logo;

	// ///////////////////////////////////// 委员会用户属性////////////////////////////////////////
	// 平台编码
	private String code;

	// 用户名
	private String userName;

	// 职务
	private String post;

	// 浏览标志 0:浏览全部 1:浏览本部门
	private String viewFlag;

	// ///////////////////////////////////// 平台用户表数据////////////////////////////////////////
	// 用户编号
	private String id;

	// 平台机构ID
	private String porgId;

	// 登录密码
	private String loginPassword;

	// 真实用户名
	private String realName;

	// 是委员会用户但没有对应的委员会标志
	private boolean errorFlag = false;

	private PtCommission ptCommission;

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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the post
	 */
	public String getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * @return the viewFlag
	 */
	public String getViewFlag() {
		return viewFlag;
	}

	/**
	 * @param viewFlag the viewFlag to set
	 */
	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the porgId
	 */
	public String getPorgId() {
		return porgId;
	}

	/**
	 * @param porgId the porgId to set
	 */
	public void setPorgId(String porgId) {
		this.porgId = porgId;
	}

	/**
	 * @return the userBelongFlag
	 */
	public String getUserBelongFlag() {
		return userBelongFlag;
	}

	/**
	 * @param userBelongFlag the userBelongFlag to set
	 */
	public void setUserBelongFlag(String userBelongFlag) {
		this.userBelongFlag = userBelongFlag;
	}

	/**
	 * @return the sysTitle
	 */
	public String getSysTitle() {
		return sysTitle;
	}

	/**
	 * @param sysTitle the sysTitle to set
	 */
	public void setSysTitle(String sysTitle) {
		this.sysTitle = sysTitle;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}

	/**
	 * @param loginPassword the loginPassword to set
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the ptCommission
	 */
	public PtCommission getPtCommission() {
		return ptCommission;
	}

	/**
	 * @param ptCommission the ptCommission to set
	 */
	public void setPtCommission(PtCommission ptCommission) {
		this.ptCommission = ptCommission;
	}

	public boolean isErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(boolean errorFlag) {
		this.errorFlag = errorFlag;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
