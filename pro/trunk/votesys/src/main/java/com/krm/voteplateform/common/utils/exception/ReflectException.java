package com.krm.voteplateform.common.utils.exception;

/**
 * 反射工具类抛出异常
 * 
 * @author JohnnyZhang
 */
public class ReflectException extends RuntimeException {

	private static final long serialVersionUID = 5703393970197813551L;

	public ReflectException() {
		super();
	}

	public ReflectException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ReflectException(String detailMessage) {
		super(detailMessage);
	}

	public ReflectException(Throwable throwable) {
		super(throwable);
	}

}
