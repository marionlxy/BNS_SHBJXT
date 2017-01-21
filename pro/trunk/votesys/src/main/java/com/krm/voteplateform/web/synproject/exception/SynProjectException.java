package com.krm.voteplateform.web.synproject.exception;

/**
 * 同步项目异常
 * 
 * @author JohnnyZhang
 */
public class SynProjectException extends RuntimeException {

	private static final long serialVersionUID = 3214168941517730514L;
	private String code;
	private String errorMsg;

	public SynProjectException() {
		super();
	}

	public SynProjectException(Throwable throwable) {
		super(throwable);
	}

	public SynProjectException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public SynProjectException(String code, String errorMsg) {
		super();
		this.code = code;
		this.errorMsg = errorMsg;
	}

	public SynProjectException(String code, String errorMsg, Throwable throwable) {
		super(throwable);
		this.code = code;
		this.errorMsg = errorMsg;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
