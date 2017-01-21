package com.krm.voteplateform.web.constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.krm.voteplateform.web.util.SysUserUtils;

public interface SysContants {

	/** 用户所属标志 */
	public static final String USER_BELONG_FLAG = "userBelongFlag";
	/** 用户所属标志：平台管理员 */
	public static final String USER_BELONG_FLAG_PLATEFORM_MANAGER = "0";
	/** 用户所属标志：委员会系统 */
	public static final String USER_BELONG_FLAG_COMMISSION = "1";
	public static final String WARN_NULL_CODE = "0100";
	public static final String WARN_NULL_MESSAGE = "对不起，您输入的信息不完整";

	/** 可预览的Office文件类型 */
	public static final String[] PRE_VIEW_FILE_TYPE_OFIICE = new String[] { "xls", "ppt", "rtf", "wps", "doc", "vsd",
			"xlsx", "pptx", "docx", "et" };

	// ///////////////// 通用方法名称集合////////////////////////////////////////////////////
	/** 通用添加方法Key */
	public static final String COMMON_MEHTOD_ADD_KEY = "addKey";
	/** 通用更新方法Key */
	public static final String COMMON_MEHTOD_UPDATE_KEY = "updateKey";
	/** 通用更新弹出窗口操作方法名称 */
	public static final String COMMON_METHOD_UPDATE_NAME = "javascript:updateWin";
	/** 通用新增弹出窗口操作方法名称 */
	public static final String COMMON_METHOD_ADD_NAME = "javascript:addWin";

	/** 通用方法名称集合 */
	public static final Map<String, String> COMMON_METHOD_MAP = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(COMMON_MEHTOD_ADD_KEY, COMMON_METHOD_ADD_NAME);
			put(COMMON_MEHTOD_UPDATE_KEY, COMMON_METHOD_UPDATE_NAME);
		}
	};

	// //////////////// 配置文件的相关Key//////////////////////////////////////////////
	/** Logo上传路径 */
	public static final String UPLOAD_PATH = "upload.rootPath";
	/** 附件上传路径 */
	public static final String UPLOAD_PROJECT_ATTCH_PATH = "upload.project.attch.path";
	/** 项目上传附件同步类型 0:存储在本地 1:存储在其他系统 2.存储在本地并存储到其他系统 */
	public static final String UPLOAD_PROJECT_ATTCH_TYPE = "upload.project.attch.type";
	/** 项目上传附件同步实现类 */
	public static final String UPLOAD_PROJECT_ATTCH_SYNCLASS = "upload.project.attch.synClass";

	/** 上传委员会Logo文件夹名称 */
	public static final String LOGO_FOLDER_NAME = "logo";
	/** 上传委员会项目附件文件夹名称 */
	public static final String ATTCH_FOLDER_NAME = "attchfiles";
	/** 任务管理日志Key */
	public static final String TASKLOG_ROOTPATH_KEY = "tasklog.rootPath";

	// ///////////////// 跳转相关集合////////////////////////////////////////////////////
	/** 模板所在路径 */
	public static final String TEMPLTE_PATH = File.separator + "template" + File.separator;
	public static final String WEB_ROOT_PATH = SysUserUtils.getSession().getServletContext().getRealPath("/");
	public static final String PT_SYSTEMS_ROOT_PATH = "WEB-INF" + File.separator + "views" + File.separator
			+ "ptsystems" + File.separator;
	/** 模板生成上传路径 */
	public static final String TEMPLTE_TO_PATH = WEB_ROOT_PATH.endsWith(File.separator) ? WEB_ROOT_PATH
			+ PT_SYSTEMS_ROOT_PATH : WEB_ROOT_PATH + File.separator + PT_SYSTEMS_ROOT_PATH;

	// /////////////////////特殊Key/////////////////////////////////////////////////////////
	/** 特殊KEY */
	public static final String SPEC_METTING_CODE_KEY = "specMettingCode";
	public static final String SPEC_METTING_TITLE_KEY = "specMettingTitle";
	public static final String SPEC_APPROVEORG_KEY = "specApproveorg";
}
