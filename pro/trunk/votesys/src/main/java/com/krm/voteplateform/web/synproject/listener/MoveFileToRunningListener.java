package com.krm.voteplateform.web.synproject.listener;

import java.io.File;

import org.springframework.stereotype.Component;

import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.web.synproject.contants.SynProjectContants;
import com.krm.voteplateform.web.synproject.enums.RunningStepEnum;
import com.krm.voteplateform.web.synproject.model.SynProjectExceptionModel;

/**
 * 转移文件任务
 * 
 * @author JohnnyZhang
 */
@Component
public class MoveFileToRunningListener extends AbstractSyncProjectListener {

	/**
	 * 执行顺序
	 */
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void execute(File srcFile, File destFile, String mapCode) throws Exception {
		logger.info("开始转移文件{}到运行目录...", srcFile.getAbsolutePath());
		FileUtils.deleteFile(destFile.getAbsolutePath() + File.separator + srcFile.getName());
		FileUtils.moveFileToDirectory(srcFile, destFile, true);
		logger.info("转移文件{}到运行目录完成", srcFile.getAbsolutePath());
	}

	@Override
	public SynProjectExceptionModel handlerException(Exception e, File srcFile, File destFile, String mapCode) {
		String errMsg = "[" + SynProjectContants.ERROR_CODE_1001 + "]转移文件" + srcFile.getAbsolutePath() + "到运行目录发生异常";
		logger.error(errMsg, e);
		return new SynProjectExceptionModel(SynProjectContants.ERROR_CODE_1001, errMsg);
	}

	@Override
	public RunningStepEnum getCurrentStep() {
		return RunningStepEnum.MOVEFILETORUNNING;
	}
}
