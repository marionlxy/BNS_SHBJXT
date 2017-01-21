package com.krm.voteplateform.web.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.krm.voteplateform.common.utils.FileUtils;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PDFOpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

/**
 * 卓正Office控件工具类
 * 
 * @author JohnnyZhang
 */
public class PageOfficeUtils {

	/**
	 * 设置PDF控制对象
	 * 
	 * @param request 当前请求
	 * @param pdfPath pdf实际路径
	 * @return PDFCtrl对象
	 */
	public static PDFCtrl buildPdfProps(HttpServletRequest request, String pdfPath) {
		PDFCtrl poCtrl1 = new PDFCtrl(request);
		// poCtrl1.addCustomToolButton("打印", "Print()", 6);
		poCtrl1.setAllowCopy(false);// 不允许复制
		poCtrl1.setServerPage(request.getContextPath() + "/poserver.zz");
		poCtrl1.addCustomToolButton("隐藏/显示书签", "SetBookmarks()", 0);
		poCtrl1.addCustomToolButton("-", "", 0);
		poCtrl1.addCustomToolButton("实际大小", "SetPageReal()", 16);
		poCtrl1.addCustomToolButton("适合页面", "SetPageFit()", 17);
		poCtrl1.addCustomToolButton("适合宽度", "SetPageWidth()", 18);
		poCtrl1.addCustomToolButton("-", "", 0);
		poCtrl1.addCustomToolButton("首页", "FirstPage()", 8);
		poCtrl1.addCustomToolButton("上一页", "PreviousPage()", 9);
		poCtrl1.addCustomToolButton("下一页", "NextPage()", 10);
		poCtrl1.addCustomToolButton("尾页", "LastPage()", 11);
		poCtrl1.addCustomToolButton("-", "", 0);
		poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		poCtrl1.webOpen(pdfPath, PDFOpenModeType.pdfViewOnly);
		poCtrl1.setTagId("PDFCtrl1"); // 此行必须
		return poCtrl1;
	}

	private static Map<String, OpenModeType> openModelTypeMap = new HashMap<String, OpenModeType>() {

		private static final long serialVersionUID = -6562688484237284127L;
		{
			put("doc", OpenModeType.docReadOnly);
			put("docx", OpenModeType.docReadOnly);
			put("rtf", OpenModeType.docReadOnly);
			put("wps", OpenModeType.docReadOnly);
			put("xls", OpenModeType.xlsReadOnly);
			put("xlsx", OpenModeType.xlsReadOnly);
			put("et", OpenModeType.xlsReadOnly);
			put("ppt", OpenModeType.pptReadOnly);
			put("pptx", OpenModeType.pptReadOnly);
			put("vsd", OpenModeType.vsdNormalEdit);
		}
	};

	/**
	 * 设置Office控制对象
	 * 
	 * @param request 当前请求
	 * @param Path pdf实际路径
	 * @return Office对象
	 */
	public static PageOfficeCtrl buildOfficeCtrl(HttpServletRequest request, String path) {
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath() + "/poserver.zz"); // 此行必须
		poCtrl1.setAllowCopy(false);// 禁止拷贝
		poCtrl1.setMenubar(false);// 隐藏菜单栏
		poCtrl1.setOfficeToolbars(false);// 隐藏Office工具条
		poCtrl1.setCustomToolbar(false);// 隐藏自定义工具栏
		poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
		// 打开文件
		poCtrl1.webOpen(path, openModelTypeMap.get(FileUtils.getFileSuffix(path)), "");
		poCtrl1.setTagId("PageOfficeCtrl1"); // 此行必须
		return poCtrl1;
	}
}
