package com.krm.voteplateform.web.synproject.contants;

import java.io.File;

import com.krm.voteplateform.web.util.PropertiesUtils;

/**
 * 存放同步项目所需定数
 * 
 * @author JohnnyZhang
 */
public abstract class SynProjectContants {

	/** 文件转移异常 */
	public static final String ERROR_CODE_1001 = "syn1001";
	/** 解压文件异常 */
	public static final String ERROR_CODE_1002 = "syn1002";
	/** 文件解析发生异常 */
	public static final String ERROR_CODE_1003 = "syn1003";
	/** 文件解析完毕转移文件异常 */
	public static final String ERROR_CODE_1004 = "syn1004";
	/** 文件解析完毕后入库异常 */
	public static final String ERROR_CODE_1005 = "syn1005";
	/** Zip内文件验证异常 */
	public static final String ERROR_CODE_1006 = "syn1006";

	/** 他系统传递文件公用路径Key */
	private static final String OTHERSYS_FILES_PUBLICPATH_KEY = "otherSys.files.publicPath";
	/** 他系统文件到达目录Key */
	private static final String OHTERSYS_FILES_REACHFOLDERNAME_KEY = "otherSys.files.reachFolderName";
	/** 他系统文件运行目录Key */
	private static final String OTHERSYS_FILES_RUNNINGFOLDERNAME_KEY = "otherSys.files.runningFolderName";
	/** 他系统文件成功运行备份目录Key */
	private static final String OTHERSYS_FILES_BAKOKFOLDERNAME_KEY = "otherSys.files.bakOkFolderName";
	/** 他系统文件失败运行备份目录Key */
	private static final String OTHERSYS_FILES_BAKERRORFOLDERNAME_KEY = "otherSys.files.bakErrorFolderName";

	/** 他系统传递文件公用路径 */
	public static final String OTHER_FILES_PUBLIC = PropertiesUtils.getValue(OTHERSYS_FILES_PUBLICPATH_KEY);
	/** 他系统文件到达目录 */
	public static final String OHTERSYS_FILES_REACH_FILE = OTHER_FILES_PUBLIC + File.separator
			+ PropertiesUtils.getValue(OHTERSYS_FILES_REACHFOLDERNAME_KEY);
	/** 他系统文件运行目录 */
	public static final String OHTERSYS_FILES_RUNNING_FILE = OTHER_FILES_PUBLIC + File.separator
			+ PropertiesUtils.getValue(OTHERSYS_FILES_RUNNINGFOLDERNAME_KEY);
	/** 他系统文件成功运行备份目录 */
	public static final String OHTERSYS_FILES_BAKOK_FILE = OTHER_FILES_PUBLIC + File.separator
			+ PropertiesUtils.getValue(OTHERSYS_FILES_BAKOKFOLDERNAME_KEY);
	/** 他系统文件失败运行备份目录Key */
	public static final String OHTERSYS_FILES_BAKERROR_FILE = OTHER_FILES_PUBLIC + File.separator
			+ PropertiesUtils.getValue(OTHERSYS_FILES_BAKERRORFOLDERNAME_KEY);

	public static final String[] OTHERSYS_FILE_ARRAY = new String[] { OHTERSYS_FILES_REACH_FILE,
			OHTERSYS_FILES_RUNNING_FILE, OHTERSYS_FILES_BAKOK_FILE, OHTERSYS_FILES_BAKERROR_FILE };

	public static final String ATTACH_FILE_NAME = "docmapping.xml";
	public static final String PROJECT_FILE_NAME = "project.xml";
	public static final String DOC_FILE_NAME = "docs";
}
