package com.krm.voteplateform.web.plateformuser.model;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtPlateformUser extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String porgId;
	
	private String userName;
	
	private String loginPassword;
	
	private String post;
	
	private String email;
	
	private String phone;
	
	private String realName;

	@AssignID("uuid32")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPorgId() {
		return porgId;
	}

	public void setPorgId(String porgId) {
		this.porgId = porgId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String toString() {
		return "PtPlateformUser [id=" + id + ", porgId=" + porgId + ", userName=" + userName + ", loginPassword="
				+ loginPassword + ", post=" + post + ", email=" + email + ", phone=" + phone + ", realName=" + realName
				+ "]";
	}
	
	

}
