package com.krm.voteplateform.web.synproject.listener;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.web.synproject.contants.SynProjectContants;
import com.krm.voteplateform.web.synproject.enums.NTypeEnum;
import com.krm.voteplateform.web.synproject.enums.RunningStepEnum;
import com.krm.voteplateform.web.synproject.exception.SynProjectException;
import com.krm.voteplateform.web.synproject.model.SynProjectExceptionModel;

/**
 * 解压文件任务
 * 
 * @author JohnnyZhang
 */
@Component
public class UnZipFileListener extends AbstractSyncProjectListener {

	@Override
	public int getOrder() {
		return 2;
	}

	@Override
	public void execute(File srcFile, File destFile, String mapCode) throws Exception {
		String zipFileName = destFile.getAbsolutePath() + File.separator + srcFile.getName();
		logger.info("开始解压文件{}...", zipFileName);
		String unZipedFilePath = FileUtils.getFileNameNoSuffix(zipFileName);
		FileUtils.unZipFiles(zipFileName, unZipedFilePath);
		logger.info("解压文件{}完毕", zipFileName);
		File file = new File(unZipedFilePath);
		File[] tempList = file.listFiles();
		logger.info("进行解压文件夹{}内文件合法性校验", unZipedFilePath);
		String checkFilesInZip = this.checkFilesInZip(tempList);
		if (StringUtils.isNotEmpty(checkFilesInZip)) {
			logger.info("解压文件夹{}内文件没有通过合法性校验", checkFilesInZip);
			String msg = NTypeEnum.UNZIPSUCCESSCHECKERROR.getMsg() + "，详细信息{" + checkFilesInZip + "}";
			logger.info("发送压缩包合法性校验失败通知..");
			sendMsgToServlet(mapCode, FileUtils.getFileNameNoSuffix(srcFile.getName()),
					NTypeEnum.UNZIPSUCCESSCHECKERROR.getType(), msg);
			throw new SynProjectException(NTypeEnum.UNZIPSUCCESSCHECKERROR.getType(), msg);
		}
		logger.info("解压文件夹{}内文件通过合法性验证", unZipedFilePath);
		sendMsgToServlet(mapCode, FileUtils.getFileNameNoSuffix(srcFile.getName()),
				NTypeEnum.UNZIPSUCCESSCHECKOK.getType(), NTypeEnum.UNZIPSUCCESSCHECKOK.getMsg());
	}

	/**
	 * 验证Zip中文件合法性
	 * 
	 * @param tempList
	 * @return
	 */
	private String checkFilesInZip(File[] tempList) {
		String checkMsg = "";
		if (tempList == null || tempList.length == 0) {
			checkMsg = "压缩包中无文件";
		} else {
			boolean checkProjectXmlExist = false;
			boolean checkDocMappingXmlExist = false;
			boolean checkDocsDirExist = false;
			for (int i = 0; i < tempList.length; i++) {
				File file = tempList[i];
				String name = file.getName();
				if (name.equals(SynProjectContants.PROJECT_FILE_NAME)) {
					checkProjectXmlExist = true;
				}
				if (name.equals(SynProjectContants.ATTACH_FILE_NAME)) {
					checkDocMappingXmlExist = true;
				}
				if (name.equals(SynProjectContants.DOC_FILE_NAME) && file.isDirectory()) {
					checkDocsDirExist = true;
				}
			}
			if (!checkProjectXmlExist) {
				checkMsg = "压缩包中缺失项目基本信息文件[" + SynProjectContants.PROJECT_FILE_NAME + "]";
			} else {
				if (checkDocMappingXmlExist && !checkDocsDirExist) {
					checkMsg = "压缩包中缺失" + SynProjectContants.DOC_FILE_NAME + "文件夹";
				}
				if (!checkDocMappingXmlExist && checkDocsDirExist) {
					checkMsg = "压缩包中缺失附件映射文件夹[" + SynProjectContants.ATTACH_FILE_NAME + "]";
				}
			}
		}
		return checkMsg;
	}

	@Override
	public SynProjectExceptionModel handlerException(Exception e, File srcFile, File destFile, String mapCode) {
		String errorMsg = "";
		String zipFileName = destFile.getAbsolutePath() + File.separator + srcFile.getName();
		if (e instanceof SynProjectException) {
			errorMsg = ((SynProjectException) e).getErrorMsg();
		} else {
			errorMsg = "[" + SynProjectContants.ERROR_CODE_1002 + "]文件" + zipFileName + "解压失败";
		}
		logger.error(errorMsg, e);
		String fileNameNoSuffix = FileUtils.getFileNameNoSuffix(zipFileName);
		FileUtils.deleteFileByCmd(fileNameNoSuffix);
		if (e instanceof SynProjectException) {
			return new SynProjectExceptionModel(((SynProjectException) e).getCode(), errorMsg);
		} else {
			sendMsgToServlet(mapCode, FileUtils.getFileNameNoSuffix(srcFile.getName()),
					NTypeEnum.UNZIPFAILURE.getType(), NTypeEnum.UNZIPFAILURE.getMsg() + ",详细信息[" + e.getMessage() + "]");
			return new SynProjectExceptionModel(SynProjectContants.ERROR_CODE_1002, errorMsg);
		}
	}

	@Override
	public RunningStepEnum getCurrentStep() {
		return RunningStepEnum.UNZIPFILE;
	}
}
