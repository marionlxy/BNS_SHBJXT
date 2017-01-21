package com.krm.voteplateform.web.downloadmodel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.downloadmodel.service.DownoadModelService;

@Controller
@RequestMapping("pt/download")
public class DownoadModelController extends BaseController {

	@Autowired
	private DownoadModelService downoadModelService;

	@RequestMapping(value = "GenModelXMl")
	public void GenModelXMl(HttpServletRequest request, HttpServletResponse response) {
		String code = getPara("code");
		this.BuildXMLDoc(response, request, code);
	}

	/**
	 * 生产xml下载文件
	 * 
	 * @param request
	 * @param response
	 * @param document
	 * @throws
	 */
	public void toBrowser(HttpServletRequest request, HttpServletResponse response, Document document, String realPath) {
		InputStream in = null;
		try {

			OutputFormat format = new OutputFormat("    ", true);
			format.setEncoding("UTF-8");// 设置编码格式
			File file = new File(realPath);
			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
				fileParent.setWritable(true, false);
			}
			file.createNewFile();
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(file), format);
			xmlWriter.write(document);
			xmlWriter.close();
			// 2.获取要下载的文件名
			String fileName = realPath.substring(realPath.lastIndexOf(File.separator) + 1);
			// 3.设置content-disposition响应头控制浏览器弹出保存框，若没有此句则浏览器会直接打开并显示文件。
			// 中文名要经过URLEncoder.encode编码，否则虽然客户端能下载但显示的名字是乱码
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			// 4.获取要下载的文件输入流
			in = new FileInputStream(realPath);
			int len = 0;
			// 5.创建数据缓冲区
			byte[] buffer = new byte[1024];
			// 6.通过response对象获取OutputStream流
			OutputStream out = response.getOutputStream();
			// 7.将FileInputStream流写入到buffer缓冲区
			while ((len = in.read(buffer)) > 0) {
				// 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			logger.error("下载XML文档,创建XML文件流发生异常", e);
		} finally {
			IOUtils.closeQuietly(in);
		}

	}

	// 生产议题模板
	public void BuildXMLDoc(HttpServletResponse response, HttpServletRequest request, String code) {
		List<Map<String, Object>> list = downoadModelService.getDicData(code);
		Element root = DocumentHelper.createElement("root");
		Document document = DocumentHelper.createDocument(root);
		// 给根节点添加属性
		root.addAttribute("type", "project").addAttribute("code", "");

		// 给根节点添加孩子节点
		Element element1 = root.addElement("record");
		element1.addAttribute("id", "");

		for (Map<String, Object> map : list) {
			if (map.get("enName").toString().startsWith("update") || map.get("enName").toString().startsWith("create")) {
				continue;
			}else if (map.get("enName").toString().equalsIgnoreCase("codeOa")) {
				continue;
			} else {
				String dataTypeName = "";
				if (map.get("dataType") == null) {
					dataTypeName = "";
				} else {
					if ("01".equalsIgnoreCase(map.get("dataType").toString())) {
						dataTypeName = "数字";
					} else if ("02".equalsIgnoreCase(map.get("dataType").toString())) {
						dataTypeName = "字符串";
					}
					if ("03".equalsIgnoreCase(map.get("dataType").toString())) {
						dataTypeName = "Id,Name";
					}
					if ("04".equalsIgnoreCase(map.get("dataType").toString())) {
						dataTypeName = "日期";
					}
					if ("05".equalsIgnoreCase(map.get("dataType").toString())) {
						dataTypeName = "金额";
					}
				}

				String isUse = "";
				if (map.get("plateFlag") == null) {
					logger.info("数据库该字段无值");
				} else {
					if ("0".equalsIgnoreCase(map.get("plateFlag").toString())) {
						isUse = "true";
					} else if ("1".equalsIgnoreCase(map.get("plateFlag").toString()) && map.get("state") != null) {
						if ("0".equalsIgnoreCase(map.get("state").toString())) {
							isUse = "true";
						} else {
							isUse = "false";
						}
					} else {
						isUse = "false";
					}
				}
				if ("03".equalsIgnoreCase(map.get("dataType").toString())) {
					String[] dates = dataTypeName.split(",");
					String enName = "";
					String cnName = "";
					for (int i = 0; i < dates.length; i++) {
						if (dates[i].equalsIgnoreCase("id")) {
							enName = map.get("enName").toString() + dates[i];
							cnName = map.get("cnName").toString() + dates[i];
							dataTypeName = "编码与名称";
						} else if (dates[i].equalsIgnoreCase("name")) {
							enName = map.get("enName").toString() + dates[i];
							cnName = map.get("cnName").toString() + "名称";
							dataTypeName = "编码与名称";
						}
						element1.addElement("param")
								.addAttribute("enName", enName)
								.addAttribute("cnName", cnName)
								.addAttribute("dataType",
										map.get("dataType") == null ? "" : map.get("dataType").toString())
								.addAttribute("dataTypeName", dataTypeName)
								.addAttribute("isUse", isUse)
								.addAttribute("maxLength",
										map.get("dataLength") == null ? "" : map.get("dataLength").toString())
								.addAttribute("value", "");
					}
				} else {
					element1.addElement("param")
							.addAttribute("enName", map.get("enName") == null ? "" : map.get("enName").toString())
							.addAttribute("cnName", map.get("cnName") == null ? "" : map.get("cnName").toString())
							.addAttribute("dataType", map.get("dataType") == null ? "" : map.get("dataType").toString())
							.addAttribute("dataTypeName", dataTypeName)
							.addAttribute("isUse", isUse)
							.addAttribute("maxLength",
									map.get("dataLength") == null ? "" : map.get("dataLength").toString())
							.addAttribute("value", "");
				}
			}
		}
		String realPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "WEB-INF"
				+ File.separator + "model" + File.separator + "project.xml";
		toBrowser(request, response, document, realPath);

	}

	// 生成附件模板
	@RequestMapping(value = "attachmentModelXMl")
	public void attachmentModelXMl(HttpServletRequest request, HttpServletResponse response) {
		Element root = DocumentHelper.createElement("root");
		Document document = DocumentHelper.createDocument(root);
		// 给根节点添加属性
		root.addAttribute("type", "mappingdoc").addAttribute("code", "");

		// 给根节点添加孩子节点
		Element element1 = root.addElement("record");
		element1.addAttribute("id", "");
		element1.addElement("mapping").addAttribute("mappingDocName", "").addAttribute("realDocName", "")
				.addAttribute("kindId", "").addAttribute("kindName", "").addAttribute("orderCode", "");
		String realPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "WEB-INF"
				+ File.separator + "model" + File.separator + "attach.xml";
		toBrowser(request, response, document, realPath);
	}

	// 生成码表模板
	@RequestMapping("codeTableModelXml")
	public void codeTableModelXml(HttpServletResponse response, HttpServletRequest request, String code)
			throws IOException {
		List<Map<String, Object>> listType = downoadModelService.getAttachType(code);
		Element root = DocumentHelper.createElement("root");
		Document document = DocumentHelper.createDocument(root);
		Element codes = root.addElement("codes");
		Element codes1 = root.addElement("codes");
		// 给根节点添加属性
		codes.addAttribute("description", "附件类型");
		codes1.addAttribute("description", "编码");
		// 给根节点添加孩子节点
		for (Map<String, Object> map : listType) {
			Element element1 = codes.addElement("code");
			element1.addAttribute("kindId", map.get("id") == null ? "" : map.get("id").toString()).addAttribute(
					"kindName", map.get("attchName") == null ? "" : map.get("attchName").toString());
		}

		List<Map<String, Object>> listCode = downoadModelService.getCodeGroup(code);
		for (Map<String, Object> map : listCode) {
			Element element1 = codes1.addElement("code");
			element1.addAttribute("codeId", map.get("id") == null ? "" : map.get("id").toString())
					.addAttribute("eCode", map.get("ecode") == null ? "" : map.get("ecode").toString())
					.addAttribute("mapName", map.get("mapName") == null ? "" : map.get("mapName").toString())
					.addAttribute("groupName",
							map.get("codeGroupName") == null ? "" : map.get("codeGroupName").toString());
		}
		String realPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "WEB-INF"
				+ File.separator + "model" + File.separator + "code.xml";
		toBrowser(request, response, document, realPath);

	}

}
