package com.krm.voteplateform.web.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.krm.voteplateform.web.constants.SysContants;

/**
 * 系统上传获取文件夹路径通用方法
 * 
 * @author JohnnyZhang
 * 
 */
public abstract class SysUploaderUtils {

	/**
	 * 取得项目中上传的相对路径，上传文件夹不存在时进行创建文件夹
	 * 
	 * @param code 委员会Code
	 * @param name 上传文件夹名称
	 * @return {上传的真实路径,上传的相对路径}
	 */
	public static String[] getProFilePath(String code, String name) {
		// 获取配置文件的Logo上传路径
		String configPath = StringUtils.replace(PropertiesUtils.getValue(SysContants.UPLOAD_PATH), "/", File.separator)
				+ File.separator;
		String reFilePath = code + File.separator + name + File.separator;// 上传的相对路径
		String xFilePath = configPath + reFilePath;// 上传的真实路径
		File file = new File(xFilePath);
		if (!file.exists()) {// 若文件不存在
			file.mkdirs();
		}
		return new String[] { xFilePath, reFilePath };
	}

	/**
	 * 获取logo文件上传路径，带/
	 * 
	 * @param code 委员会编码
	 * @return
	 */
	public static String getLogoPath(String code) {
		String replace = StringUtils.replace(PropertiesUtils.getValue(SysContants.UPLOAD_PATH), "/", File.separator);
		StringBuffer sb = new StringBuffer(replace);
		sb.append(File.separator);
		sb.append(code);
		sb.append(File.separator);
		sb.append(SysContants.LOGO_FOLDER_NAME);
		sb.append(File.separator);
		return sb.toString();
	}

	/**
	 * 获取会议项目附件上传的文件夹路径，带/
	 * 
	 * @param projectId 项目ID
	 * @return
	 */
	public static String getProAttchPath(String projectId) {
		return getProAttchPath(projectId, null);
	}

	/**
	 * 取得委员会上传附件的根路径,带/，无文件目录则进行创建
	 * 
	 * @return
	 */
	public static String getRootAttchPath() {
		return getRootAttchPath(null);
	}

	/**
	 * 获取会议项目附件上传的文件夹路径，带/ <br>
	 * 当code为null时，使用Session中的Code值
	 * 
	 * @param projectId 项目ID
	 * @param code 委员会Code
	 * @return
	 */
	public static String getProAttchPath(String projectId, String code) {
		StringBuffer sb = new StringBuffer(getRootAttchPath(code));
		sb.append(projectId);
		sb.append(File.separator);
		return sb.toString();
	}

	/**
	 * 取得委员会上传附件的根路径,带/，无文件目录则进行创建<br/>
	 * 当code为null时，使用Session中的Code值
	 * 
	 * @param code 委员会Code
	 * @return
	 */
	public static String getRootAttchPath(String code) {
		String replace = StringUtils.replace(PropertiesUtils.getValue(SysContants.UPLOAD_PROJECT_ATTCH_PATH), "/",
				File.separator);
		StringBuffer sb = new StringBuffer(replace);
		sb.append(File.separator);
		sb.append(code == null ? SysUserUtils.getCurrentCommissionCode() : code);
		sb.append(File.separator);
		sb.append(SysContants.ATTCH_FOLDER_NAME);
		sb.append(File.separator);
		return sb.toString();
	}
}
