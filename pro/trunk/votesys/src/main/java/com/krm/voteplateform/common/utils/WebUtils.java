package com.krm.voteplateform.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class WebUtils {
	private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

	/**
	 * 将数据以 JSON 格式写入响应中
	 */
	public static void writeJSON(HttpServletResponse response, Object data) {
		try {
			// 设置响应头
			response.setContentType("application/json"); // 指定内容类型为 JSON 格式
			response.setCharacterEncoding("UTF-8"); // 防止中文乱码
			// 向响应中写入数据
			PrintWriter writer = response.getWriter();
			writer.write(JSON.toJSONString(data)); // 转为 JSON 字符串
			writer.flush();
			writer.close();
		} catch (Exception e) {
			logger.error("在响应中写数据出错！", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将数据以 HTML 格式写入响应中（在 JS 中获取的是 JSON 字符串，而不是 JSON 对象）
	 */
	public static void writeHTML(HttpServletResponse response, Object data) {
		try {
			// 设置响应头
			response.setContentType("text/html"); // 指定内容类型为 HTML 格式
			response.setCharacterEncoding("UTF-8"); // 防止中文乱码
			// 向响应中写入数据
			PrintWriter writer = response.getWriter();
			writer.write(JSON.toJSONString(data)); // 转为 JSON 字符串
			writer.flush();
			writer.close();
		} catch (Exception e) {
			logger.error("在响应中写数据出错！", e);
			throw new RuntimeException(e);
		}
	}

	private static boolean checkParamName(String paramName) {
		return !paramName.equals("_"); // 忽略 jQuery 缓存参数
	}

	/**
	 * 字符串分隔符
	 */
	public static final String SEPARATOR = String.valueOf((char) 29);

	/**
	 * 将输入流复制到输出流
	 */
	public static void copyStream(InputStream inputStream, OutputStream outputStream) {
		try {
			int length;
			byte[] buffer = new byte[4 * 1024];
			while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();
		} catch (Exception e) {
			logger.error("复制流出错！", e);
			throw new RuntimeException(e);
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (Exception e) {
				logger.error("释放资源出错！", e);
			}
		}
	}

	/**
	 * 从输入流中获取字符串
	 */
	public static String getString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			logger.error("Stream 转 String 出错！", e);
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	/**
	 * 从请求中获取所有参数（当参数名重复时，用后者覆盖前者）
	 */
	public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		try {
			String method = request.getMethod();
			if (method.equalsIgnoreCase("put") || method.equalsIgnoreCase("delete")) {
				String queryString = decodeURL(getString(request.getInputStream()));
				if (StringUtils.isNotEmpty(queryString)) {
					String[] qsArray = StringUtils.split(queryString, "&");
					if (ArrayUtils.isNotEmpty(qsArray)) {
						for (String qs : qsArray) {
							String[] array = StringUtils.split(qs, "=");
							if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
								String paramName = array[0];
								String paramValue = array[1];
								if (checkParamName(paramName)) {
									if (paramMap.containsKey(paramName)) {
										paramValue = paramMap.get(paramName) + SEPARATOR + paramValue;
									}
									paramMap.put(paramName, paramValue);
								}
							}
						}
					}
				}
			} else {
				Enumeration<String> paramNames = request.getParameterNames();
				while (paramNames.hasMoreElements()) {
					String paramName = paramNames.nextElement();
					if (checkParamName(paramName)) {
						String[] paramValues = request.getParameterValues(paramName);
						if (ArrayUtils.isNotEmpty(paramValues)) {
							if (paramValues.length == 1) {
								paramMap.put(paramName, paramValues[0]);
							} else {
								StringBuilder paramValue = new StringBuilder("");
								for (int i = 0; i < paramValues.length; i++) {
									paramValue.append(paramValues[i]);
									if (i != paramValues.length - 1) {
										paramValue.append(SEPARATOR);
									}
								}
								paramMap.put(paramName, paramValue.toString());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取请求参数出错！", e);
			throw new RuntimeException(e);
		}
		return paramMap;
	}

	/**
	 * 从 Cookie 中获取数据
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		String value = "";
		try {
			Cookie[] cookieArray = request.getCookies();
			if (cookieArray != null) {
				for (Cookie cookie : cookieArray) {
					if (StringUtils.isNotEmpty(name) && name.equals(cookie.getName())) {
						value = decodeURL(cookie.getValue());
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取 Cookie 出错！", e);
			throw new RuntimeException(e);
		}
		return value;
	}

	/**
	 * 将 URL 解码
	 */
	public static String decodeURL(String str) {
		String target;
		try {
			target = URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			logger.error("解码出错！", e);
			throw new RuntimeException(e);
		}
		return target;
	}

}
