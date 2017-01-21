package com.krm.voteplateform.web.synproject.schedule;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.utils.LogUtils;
import com.krm.voteplateform.web.synproject.service.StartSyncProjectService;

/**
 * 同步项目工作类
 * 
 * @author JohnnyZhang
 */
public class SynProjectJobWorker implements ApplicationContextAware {

	// 刷新队列
	private static ExecutorService freshCacheWorder = Executors.newSingleThreadExecutor();
	// 工作队列
	private static LinkedBlockingQueue<String> workQueue = new LinkedBlockingQueue<String>(1);

	private static Map<String, File> workFileMap = Maps.newConcurrentMap();

	private static SynProjectJobWorker synProjectJobWorker;
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SynProjectJobWorker.applicationContext = applicationContext;
		SynProjectJobWorker.synProjectJobWorker = (SynProjectJobWorker) applicationContext
				.getBean("synProjectJobWorker");
	}

	public static SynProjectJobWorker getInstance() {
		return SynProjectJobWorker.synProjectJobWorker;
	}

	public void init() {
		freshCacheWorder.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					LogUtils.getSynProject().info("开始刷新队列...");
					String id = null;
					try {
						id = workQueue.take();
					} catch (InterruptedException e) {
						LogUtils.getSynProject().error("从队列中取得数据异常", e);
					}
					// 若队列中取得的数据不为空
					if (id != null && id.trim().length() > 0 && workFileMap.get(id) != null) {
						// 进行逻辑处理
						File file = workFileMap.get(id);
						StartSyncProjectService service = (StartSyncProjectService) applicationContext
								.getBean("startSyncProjectService");
						service.insertSync(file);
					}
				}
			}
		});
	}

	/**
	 * 向任务队列中追加任务
	 * 
	 * @param file
	 */
	public void addTask(File file) {
		try {
			if (file != null && file.exists()) {
				SynProjectJobWorker.workQueue.put(file.getAbsolutePath());
				if (!workFileMap.containsKey(file.getAbsolutePath())) {
					workFileMap.put(file.getAbsolutePath(), file);
				}
			}
		} catch (InterruptedException e) {
			LogUtils.getSynProject().error("向任务队列中加入任务发生异常！", e);
		}
	}

	/**
	 * 任务队列中清空任务
	 * 
	 * @param file
	 */
	public void clearTask(File file) {
		if (file != null && file.exists()) {
			if (workFileMap.containsKey(file.getAbsoluteFile())) {
				workFileMap.remove(file.getAbsoluteFile());
			}
		}
	}

	/**
	 * 清空队列中的所有任务
	 */
	public void clearAllTask() {
		LogUtils.getSynProject().info("开始清空所有项目同步任务..");
		if (!freshCacheWorder.isShutdown()) {
			freshCacheWorder.shutdown();
		}
		workFileMap.clear();
		workFileMap = Maps.newConcurrentMap();
		workQueue.clear();
		LogUtils.getSynProject().info("清空所有项目同步任务完成");
	}

}
