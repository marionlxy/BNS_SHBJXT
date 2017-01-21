package com.krm.voteplateform.web.synproject.listener;

import java.io.File;

import org.springframework.stereotype.Component;

import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.common.utils.LogUtils;
import com.krm.voteplateform.web.synproject.contants.SynProjectContants;
import com.krm.voteplateform.web.synproject.enums.RunningStepEnum;
import com.krm.voteplateform.web.synproject.model.SynProjectExceptionModel;

/**
 * 转移文件到成功文件夹
 * 
 * @author JohnnyZhang
 */
@Component
public class MoveFileToSuccListener extends AbstractSyncProjectListener {

	@Override
	public int getOrder() {
		return 6;
	}

	@Override
	public void execute(File srcFile, File destFile, String mapCode) throws Exception {
		// 取得父文件夹目录名称,即关联系统名称
		String parentDirName = srcFile.getParentFile().getName();
		// 取得上两层的目录,即委员会CODE
		String secondParentDirName = srcFile.getParentFile().getParentFile().getName();
		String zipFileName = destFile.getAbsolutePath() + File.separator + srcFile.getName();
		File zipFile = new File(zipFileName);
		// 防止没有走到此步骤时文件已经进行了删除处理了
		if (!zipFile.exists()) {
			return;
		}
		String fileNameNoSuffix = FileUtils.getFileNameNoSuffix(zipFileName);
		logger.info("开始进行转移文件到成功目录逻辑");
		logger.info("开始进行删除解压完毕的文件夹{}...", fileNameNoSuffix);
		// 删除解压文件夹
		FileUtils.deleteFileByCmd(fileNameNoSuffix);
		logger.info("删除解压完毕的文件夹{}结束", fileNameNoSuffix);
		// 转移文件到OK文件夹
		logger.info("开始转移文件{}到文件成功目录...", zipFileName);
		String okFilePath = SynProjectContants.OHTERSYS_FILES_BAKOK_FILE + File.separator + secondParentDirName
				+ File.separator + parentDirName;
		FileUtils.moveFileToDirectory(zipFile, new File(okFilePath), true);
		logger.info("转移文件{}到文件成功目录成功结束", zipFileName);
		logger.info("开始进行转移文件到成功目录逻辑结束");
	}

	@Override
	public SynProjectExceptionModel handlerException(Exception e, File srcFile, File destFile, String mapCode) {
		// 入库成功但转移文件发生异常后，向指定的日志文件中追加日志并删除原Zip包
		String zipFileName = destFile.getAbsolutePath() + File.separator + srcFile.getName();
		String errorMsg = "[" + SynProjectContants.ERROR_CODE_1001 + "]转移文件" + zipFileName + "到成功文件夹发生异常";
		logger.error(errorMsg, e);
		LogUtils.getSynSuccMoveFailure().info(errorMsg);
		FileUtils.deleteFile(zipFileName);
		return new SynProjectExceptionModel(SynProjectContants.ERROR_CODE_1001, errorMsg);
	}

	@Override
	public RunningStepEnum getCurrentStep() {
		return RunningStepEnum.MOVEFILETOSUCC;
	}

	@Override
	protected boolean isDefaultHanlderException() {
		return false;
	}

}
