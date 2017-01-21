package com.krm.voteplateform.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 取得错误信息
 * 
 * @author JohnnyZhang
 */
public class ExceptionCrashUtils {
	/**
	 * 取得错误信息
	 * 
	 * @param context
	 * @param ex
	 * @return 错误信息
	 */
	public static String getCrashReport(Throwable ex) {
		if (ex == null) {
			return "";
		}
		StringBuffer exceptionStr = new StringBuffer("[" + DateUtils.getDateTime() + "]");
		exceptionStr.append("Exception:" + ex.getMessage() + "\n");
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		exceptionStr.append("Cause By:").append(info.toString());
		printWriter.close();
		return exceptionStr.toString();
	}
}
