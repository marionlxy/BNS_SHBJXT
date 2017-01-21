package com.krm.voteplateform.web.util;

import com.krm.voteplateform.common.utils.ResourcePropertyPlaceholderConfigurer;

public abstract class PropertiesUtils {

	public static String getValue(String key, String defaultValue) {
		return ResourcePropertyPlaceholderConfigurer.getStrPropertyValue(key, defaultValue);
	}

	public static String getValue(String key) {
		return getValue(key, "");
	}

	public static Object getObjValue(String key) {
		return ResourcePropertyPlaceholderConfigurer.getContentProperty(key);
	}
}
