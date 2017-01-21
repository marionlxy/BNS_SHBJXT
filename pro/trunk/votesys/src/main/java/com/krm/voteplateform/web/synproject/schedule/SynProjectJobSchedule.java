package com.krm.voteplateform.web.synproject.schedule;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.common.utils.LogUtils;
import com.krm.voteplateform.web.synproject.contants.SynProjectContants;

/**
 * 同步项目任务调度中心
 * 
 * @author JohnnyZhang
 */
public class SynProjectJobSchedule {

	/** 调度中心主运行方法 */
	public void run() {
		// 初始化其他系统传递的目录
		List<File> listFiles = FileUtils.getFileList(SynProjectContants.OHTERSYS_FILES_REACH_FILE, null);
		// 如果文件不存在，返回不做处理
		if (listFiles == null || listFiles.size() == 0) {
			return;
		}
		// 循环加入到任务队列中
		for (int i = 0; i < listFiles.size(); i++) {
			SynProjectJobWorker.getInstance().addTask(listFiles.get(i));
		}
	}

	private static ExecutorService worker = Executors.newSingleThreadExecutor();

	/** 调度中心系统初始化方法 */
	public void init() {
		worker.execute(new Runnable() {

			private List<File> getZipFileList(String strPath, List<File> filelist) {
				if (filelist == null) {
					filelist = Lists.newArrayList();
				}
				File dir = new File(strPath);
				File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						String fileName = files[i].getName();
						// 若zip包肯定是在对接系统编码文件夹下
						if (files[i].getAbsolutePath().contains(SynProjectContants.DOC_FILE_NAME)) {
							continue;
						} else if (files[i].isDirectory()) { // 判断是文件还是文件夹
							getZipFileList(files[i].getAbsolutePath(), filelist); // 获取文件绝对路径
						} else if (fileName.toLowerCase().endsWith(".zip")) { // 判断文件名是否以.zip结尾
							filelist.add(files[i]);
						} else {
							continue;
						}
					}
				}
				return filelist;
			}

			@Override
			public void run() {
				LogUtils.getSynProject().info("系统启动初始化查询是否有未完成的项目同步调度任务");
				// 初始化运行目录zip文件
				List<File> listFiles = this.getZipFileList(SynProjectContants.OHTERSYS_FILES_RUNNING_FILE, null);
				// 如果文件不存在，返回不做处理
				if (listFiles == null || listFiles.size() == 0) {
					return;
				}
				LogUtils.getSynProject().info("系统中含有上次未运行完毕的同步项目文件，数量为{}个", listFiles.size());
				File tempFile = null;
				for (File file : listFiles) {
					String absolutePath = file.getAbsolutePath();
					LogUtils.getSynProject().info("开始处理文件[{}]...", absolutePath);
					// 取得父文件夹目录名称,即关联系统名称
					String parentDirName = file.getParentFile().getName();
					// 取得上两层的目录,即委员会CODE
					String secondParentDirName = file.getParentFile().getParentFile().getName();
					String destFilePath = SynProjectContants.OHTERSYS_FILES_REACH_FILE + File.separator
							+ secondParentDirName + File.separator + parentDirName;
					tempFile = new File(destFilePath);
					try {
						// 将遗留的运行目录中的文件转移至到达目录
						LogUtils.getSynProject().info("开始转移文件[{}]...", absolutePath);
						FileUtils.moveFileToDirectory(file, tempFile, true);
						String fileNameNoSuffix = FileUtils.getFileNameNoSuffix(absolutePath);
						FileUtils.deleteFileByCmd(fileNameNoSuffix);
						LogUtils.getSynProject().info("处理文件[{}]完毕.", absolutePath);
					} catch (IOException e) {
						LogUtils.getSynProject().error("系统启动初始化转移同步任务发生异常,异常文件路径为:" + absolutePath, e);
					}
				}
			}
		});

	}

}
