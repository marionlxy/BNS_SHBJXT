package com.krm.voteplateform.web.test.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.krm.voteplateform.common.base.entity.BaseEntity;

public class Tm extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4567609781001972053L;

	private String category;

	private String username;
	private String password;
	private String sel;
	private MultipartFile file;

	//tms[0].file
	
	
	//(Tm tm)
	private List<Tm> tms;

	/**
	 * @return the tms
	 */
	public List<Tm> getTms() {
		return tms;
	}

	/**
	 * @param tms the tms to set
	 */
	public void setTms(List<Tm> tms) {
		this.tms = tms;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the sel
	 */
	public String getSel() {
		return sel;
	}

	/**
	 * @param sel the sel to set
	 */
	public void setSel(String sel) {
		this.sel = sel;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the file
	 */
	public MultipartFile getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
