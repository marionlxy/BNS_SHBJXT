package com.krm.voteplateform.web.synproject.model;

import java.io.Serializable;

/**
 * @author JohnnyZhang
 */
public class SynProjectExceptionModel implements Serializable {

	private static final long serialVersionUID = 6999008240580250419L;

	private String code = "";
	private String msg = "";

	public SynProjectExceptionModel() {
		super();
	}

	public SynProjectExceptionModel(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
