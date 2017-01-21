package com.krm.voteplateform.common.birt.loader;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Birt上下文环境
 * 
 * @author JohnnyZhang
 */
public class BirtAppContextLoader {

	private static Logger logger = LoggerFactory.getLogger(BirtAppContextLoader.class);

	private static BirtAppContextLoader instance;

	private ServletContext sc;

	private BirtAppContextLoader() {
		logger.info("加载Birt上下文环境");
	}

	public static BirtAppContextLoader getInstance() {
		if (instance == null) {
			instance = new BirtAppContextLoader();
		}
		return instance;
	}

	public ServletContext getServletContext() {
		return sc;
	}

	public void setServletContext(ServletContext sc) {
		this.sc = sc;
	}

}
