package com.krm.voteplateform.web.synproject.error;

import org.springframework.util.ErrorHandler;

/**
 * 同步项目异常处理类
 * 
 * @author JohnnyZhang
 */
public class SynProjectErrorHandler implements ErrorHandler {

	@Override
	public void handleError(Throwable throwable) {
		// String message = throwable.getMessage();
		// LogUtils.getSynProject().error(message, throwable);
		// if (message.startsWith("[" + SynProjectContants.ERROR_CODE_1001 + "]")) {
		// // LogUtils.getSynProject().error(message, throwable);
		// }
	}

}
