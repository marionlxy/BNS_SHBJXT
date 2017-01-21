package com.krm.voteplateform.web.plateformuserpass;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class PtPlateformUserPass extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String loginPassword;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Override
	public String toString() {
		return "PtPlateformUserPass [id=" + id + ", loginPassword=" + loginPassword + "]";
	}
	
	

}
