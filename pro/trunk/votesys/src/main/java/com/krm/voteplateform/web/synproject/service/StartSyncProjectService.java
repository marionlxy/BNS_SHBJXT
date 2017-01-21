package com.krm.voteplateform.web.synproject.service;

import java.io.File;

/**
 * 同步任务入口Service
 * 
 * @author JohnnyZhang
 */
public interface StartSyncProjectService {
	/**
	 * 任务同步
	 * 
	 * @param file
	 */
	public void insertSync(File file);
}
