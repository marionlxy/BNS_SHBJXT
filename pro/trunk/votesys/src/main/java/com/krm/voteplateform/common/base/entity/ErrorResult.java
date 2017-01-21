package com.krm.voteplateform.common.base.entity;

import java.util.HashMap;
import java.util.Map;

public class ErrorResult extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6423550943456443764L;

	/**
	 * 封装多个 错误信息
	 */
	private Map<String, Object> errors = new HashMap<>();

	public Map<String, Object> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, Object> errors) {
		this.errors = errors;
	}

	public ErrorResult() {

	}

}
