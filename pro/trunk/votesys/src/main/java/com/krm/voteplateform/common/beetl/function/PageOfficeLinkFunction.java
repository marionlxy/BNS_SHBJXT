package com.krm.voteplateform.common.beetl.function;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.zhuozhengsoft.pageoffice.PageOfficeLink;

/**
 * 卓正Office封装的方法
 * 
 * @author JohnnyZhang
 */
@Component
public class PageOfficeLinkFunction {

	/**
	 * 封装卓正POL协议
	 * 
	 * @param request 请求
	 * @param proId 项目ID
	 * @param fileName 文件名称
	 * @param widthAndHeight 宽和高 若为空，则为默认值width=800px;height=800px;
	 * @return
	 */
	public String openWindow(HttpServletRequest request, String proId, String fileName, String widthAndHeight) {
		if (StringUtils.isEmpty(widthAndHeight)) {
			widthAndHeight = "width=800px;height=800px;";
		}
		return PageOfficeLink.openWindow(request, "/preViewDocument/viewOffice?proId=" + proId + "&file=" + fileName,
				widthAndHeight);
	}
}
