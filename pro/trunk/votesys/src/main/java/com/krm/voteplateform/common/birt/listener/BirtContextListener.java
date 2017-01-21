package com.krm.voteplateform.common.birt.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krm.voteplateform.common.birt.loader.BirtAppContextLoader;
import com.krm.voteplateform.web.synproject.schedule.SynProjectJobWorker;

/**
 * Birt上下文监听器
 * 
 * @author JohnnyZhang
 */
public class BirtContextListener implements ServletContextListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.info("Web容器关闭处理开始..");
		logger.info("开始清空任务同步....");
		SynProjectJobWorker.getInstance().clearAllTask();
		logger.info("清空任务同步结束");
		logger.info("Web容器关闭处理结束");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("向Birt上下文环境中注入Servlet上下文");
		// 向Birt上线文环境中注入Servlet上下文
		BirtAppContextLoader.getInstance().setServletContext(servletContextEvent.getServletContext());
	}

}
