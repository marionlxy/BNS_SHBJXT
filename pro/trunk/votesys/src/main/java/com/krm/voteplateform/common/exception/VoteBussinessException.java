package com.krm.voteplateform.common.exception;

public class VoteBussinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2387418428058100117L;

	private String code;
	private String message;

	public VoteBussinessException() {
		super();
	}

	public VoteBussinessException(Throwable throwable) {
		super(throwable);
	}

	public VoteBussinessException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public VoteBussinessException(String code, String message, Throwable throwable) {
		super(message, throwable);
		this.code = code;
	}

	public VoteBussinessException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
