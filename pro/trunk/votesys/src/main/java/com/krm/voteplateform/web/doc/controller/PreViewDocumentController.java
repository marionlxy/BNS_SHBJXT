package com.krm.voteplateform.web.doc.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.common.utils.Img2Base64Utils;
import com.krm.voteplateform.web.constants.SysContants;
import com.krm.voteplateform.web.util.PageOfficeUtils;
import com.krm.voteplateform.web.util.SysUploaderUtils;

/**
 * 预览文档用
 * 
 * @author JohnnyZhang
 */
@Controller
@RequestMapping("preViewDocument")
public class PreViewDocumentController extends BaseController {

	/**
	 * 预览文档
	 * 
	 * @param request
	 * @param proId 项目ID
	 * @param file 文件名称
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("viewOffice")
	public String viewOffice(HttpServletRequest request, Model model, String proId, String file)
			throws UnsupportedEncodingException {
		file = new String(request.getParameter("file").getBytes("ISO8859-1"), "UTF-8");
		// 取得文件结尾
		String fileSuffix = FileUtils.getFileSuffix(file);
		// 重新设置真实文件路径
		String realFile = SysUploaderUtils.getProAttchPath(proId) + file;
		// 判定是否为可预览的office文件
		boolean contains = ArrayUtils.contains(SysContants.PRE_VIEW_FILE_TYPE_OFIICE, fileSuffix);
		String param1 = "";
		if (contains) {
			PageOfficeUtils.buildOfficeCtrl(request, realFile);
			param1 = "office/showoffice";
		} else if ("pdf".equalsIgnoreCase(fileSuffix)) {
			PageOfficeUtils.buildPdfProps(request, realFile);
			param1 = "office/showpdf";
		} else {
			String imageToBase64 = Img2Base64Utils.imageToBase64(realFile);
			StringBuffer sb = new StringBuffer();
			if ("jpg".equalsIgnoreCase(fileSuffix)) {
				sb.append("data:image/jpg;base64,");
			} else if ("gif".equalsIgnoreCase(fileSuffix)) {
				sb.append("data:image/gif;base64,");
			} else if ("png".equalsIgnoreCase(fileSuffix)) {
				sb.append("data:image/png;base64,");
			} else if ("jpeg".equalsIgnoreCase(fileSuffix)) {
				sb.append("data:image/jpeg;base64,");
			} else {
				sb.append("data:image/jpg;base64,");
			}
			sb.append(imageToBase64);
			model.addAttribute("base64Str", sb.toString());
			param1 = "office/showpic";
		}
		return param1;
	}
}
