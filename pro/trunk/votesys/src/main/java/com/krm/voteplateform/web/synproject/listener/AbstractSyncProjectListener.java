package com.krm.voteplateform.web.synproject.listener;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.util.ObjectUtils;
import org.ws.httphelper.WSHttpHelper;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ResponseResult;
import org.ws.httphelper.request.handler.CallbackHandler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.common.utils.LogUtils;
import com.krm.voteplateform.web.synproject.contants.SynProjectContants;
import com.krm.voteplateform.web.synproject.enums.RunningStepEnum;
import com.krm.voteplateform.web.synproject.event.SynProjectEvent;
import com.krm.voteplateform.web.synproject.exception.SynProjectException;
import com.krm.voteplateform.web.synproject.model.SynProjectExceptionModel;
import com.krm.voteplateform.web.synproject.schedule.SynProjectJobWorker;

/**
 * 基础处理同步项目任务监听器,注意0必须为第一个执行
 * 
 * @author JohnnyZhang
 */
public abstract class AbstractSyncProjectListener implements SmartApplicationListener {

	protected Logger logger = LogUtils.getSynProject();

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		File file = (File) event.getSource();
		// 取得父文件夹目录名称,即关联系统名称
		String parentDirName = file.getParentFile().getName();
		// 取得上两层的目录,即委员会CODE
		String secondParentDirName = file.getParentFile().getParentFile().getName();
		String destFilePath = SynProjectContants.OHTERSYS_FILES_RUNNING_FILE + File.separator + secondParentDirName
				+ File.separator + parentDirName;
		File destFile = new File(destFilePath);
		String mapCode = secondParentDirName + "_" + parentDirName;
		try {
			execute(file, destFile, mapCode);
		} catch (Exception e) {
			SynProjectExceptionModel handlerException = handlerException(e, file, destFile, mapCode);
			// 若需要进行默认错误异常处理
			if (isDefaultHanlderException()) {
				defaultHandlerException(e, file, destFile, mapCode);
			}
			throw new SynProjectException(handlerException.getCode(), handlerException.getMsg(), e);
		} finally {
			// 不管成功与否，清空当前任务
			SynProjectJobWorker.getInstance().clearTask(file);
		}
	}

	/** 执行顺序，值越小越先执行，若值相同，按照字母先后进行执行 */
	public abstract int getOrder();

	/**
	 * 具体执行方法
	 * 
	 * @param srcFile 处理原文件
	 * @param destFile 处理文件转移的文件夹
	 * @param mapCode 其他系统关联表Map的Key
	 */
	public abstract void execute(File srcFile, File destFile, String mapCode) throws Exception;

	/**
	 * 异常处理
	 * 
	 * @param e 异常
	 * @param srcFile 处理原文件
	 * @param descFile 处理文件转移的文件夹
	 * @param mapCode 其他系统关联表Map的Key
	 */
	public abstract SynProjectExceptionModel handlerException(Exception e, File srcFile, File destFile, String mapCode);

	/** 取得当前运行步骤 */
	public abstract RunningStepEnum getCurrentStep();

	/**
	 * 是否需要默认的异常处理
	 * 
	 * @return
	 */
	protected boolean isDefaultHanlderException() {
		return true;
	}

	/**
	 * 默认错误处理,转移文件到异常目录
	 * 
	 * @param e
	 * @param srcFile 原文件
	 * @param destFile 运行目录文件目录
	 * @param mapCode
	 */
	protected void defaultHandlerException(Exception e, File srcFile, File destFile, String mapCode) {
		RunningStepEnum currentStep = getCurrentStep();
		logger.info("进入执行{}步发生异常,开始进行异常处理", currentStep.getDescription());
		// 取得父文件夹目录名称,即关联系统名称
		String parentDirName = srcFile.getParentFile().getName();
		// 取得上两层的目录,即委员会CODE
		String secondParentDirName = srcFile.getParentFile().getParentFile().getName();
		File tempFile = null;
		if (RunningStepEnum.MOVEFILETORUNNING.equals(currentStep)) {// 若当前处于转移文件到运行目录状态
			tempFile = srcFile;// 源文件即为原文件
		} else {
			tempFile = new File(destFile.getAbsolutePath() + File.separator + srcFile.getName());// 源文件即为转移后运行目录的文件
		}
		if (!tempFile.exists()) {
			logger.info("需转移的文件{}不存在", tempFile.getAbsolutePath());
			return;
		}
		logger.info("开始转移文件{}到文件异常目录...", tempFile.getAbsolutePath());
		String destFilePath = SynProjectContants.OHTERSYS_FILES_BAKERROR_FILE + File.separator + secondParentDirName
				+ File.separator + parentDirName;
		File errorFileFolder = new File(destFilePath);
		try {
			FileUtils.moveFileToDirectory(tempFile, errorFileFolder, true);
			logger.info("转移文件{}到文件异常目录结束", tempFile.getAbsolutePath());
			// 若当前步骤不处于【转移文件到运行目录】目录，证明其已经解压或解压完毕
			if (!RunningStepEnum.MOVEFILETORUNNING.equals(currentStep)) {
				// 删除其解压后的文件
				String fileNameNoSuffix = FileUtils.getFileNameNoSuffix(tempFile.getAbsolutePath());
				logger.info("开始删除解压后的压缩文件夹{}", fileNameNoSuffix);
				FileUtils.deleteFileByCmd(fileNameNoSuffix);
				logger.info("删除解压后的压缩文件夹{}结束", fileNameNoSuffix);
			}
		} catch (IOException e1) {
			logger.error("[" + SynProjectContants.ERROR_CODE_1001 + "]转移文件发生异常", e1);
			logger.info("开始进行删除文件{}处理..", tempFile.getAbsolutePath());
			boolean deleteFile = FileUtils.deleteFile(tempFile.getAbsolutePath());
			logger.info("删除文件{}处理结束,处理结果{}", tempFile.getAbsolutePath(), deleteFile);
			throw new SynProjectException(SynProjectContants.ERROR_CODE_1001, "转移文件发生异常", e1);
		}
	}

	/**
	 * 发送Http请求
	 * 
	 * @param mapCode
	 * @param id 他系统ID
	 * @param ntype 1.解压成功 2.解压失败 3.入库成功 4.入库失败
	 * @param msg 返回的消息
	 */
	protected void sendMsgToServlet(String mapCode, String id, String ntype, String msg) {
		logger.info("发送Http请求...");
		Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("id", id);
		parameters.put("ntype", ntype);
		parameters.put("msg", msg == null ? "" : msg);
		final Map<String, Object> map = SpringContextHolder.getRelationOtherSysMap().get(mapCode);
		if (map != null && !ObjectUtils.isEmpty(map.containsKey("NOTIFY_ADDR"))) {
			try {
				logger.info("http请求地址{}参数{}", map.get("NOTIFY_ADDR").toString(), JSON.toJSONString(parameters));
				WSHttpHelper.doAsyncPostHtml(map.get("NOTIFY_ADDR").toString(), parameters, "UTF-8",
						new CallbackHandler() {
							@Override
							public ResponseResult execute(ResponseResult result) throws WSException {
								String html = result.getBody().toString();
								logger.info("请求{}返回结果{}", map.get("NOTIFY_ADDR").toString(), html);
								result.setBody(html);
								return result;
							}
						});
			} catch (WSException e) {
				logger.error(
						"发送Http请求异常,地址:" + map.get("NOTIFY_ADDR").toString() + ",参数:" + JSON.toJSONString(parameters),
						e);
			}
		} else {
			logger.error("{}Key记录在外部系统关联表中不存在或key值所在的记录中Servlet地址为空", mapCode);
		}
	}

	@Override
	public boolean supportsEventType(final Class<? extends ApplicationEvent> eventType) {
		return eventType == SynProjectEvent.class;
	}

	@Override
	public boolean supportsSourceType(final Class<?> sourceType) {
		return sourceType == File.class;
	}

}
