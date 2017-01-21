package com.krm.voteplateform.common.xml.parseinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.krm.voteplateform.common.xml.XmlParseInfo;

/**
 * XML解析信息实现类，此类是提供了XML文件路径字符串，XML文件对象和流的接口方式设置XML路径.
 * 
 * @author JohnnyZhang
 * @version 1.1
 */
public class SimpleXmlParseInfo implements XmlParseInfo {

	// XML文件路径.
	private String xmlUri;
	// XML文件.
	private File xmlFile;
	// XML输入流.
	private InputStream xmlInputStream;

	/**
	 * 默认的构造器.
	 */
	public SimpleXmlParseInfo() {
	}

	/**
	 * 以一个路径字符串创建一个待解析的源.
	 * 
	 * @param xmlUri 一个路径字符串.
	 */
	public void setXmlPath(String xmlUri) {
		this.xmlUri = xmlUri;
	}

	/**
	 * 以一个文件创建一个待解析的源.
	 * 
	 * @param xmlFile 一个文件.
	 */
	public void setXmlPath(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	/**
	 * 以一个流创建一个待解析的源.
	 * 
	 * @param xmlInputStream 流.
	 */
	public void setXmlPath(InputStream xmlInputStream) {
		this.xmlInputStream = xmlInputStream;
	}

	/**
	 * 返回待解析的XML流. 按照流>>文件>>路径的顺序，依次构造返回. 例如，设置了流，那么直接返回，如果仅设置了路径， 那么首先将路径构造成文件，再将文件转换为流返回.
	 * 
	 * @return 待解析的XML流.
	 * @throws Exception 如果发生任何错误.
	 */
	public InputStream getXmlPath() throws Exception {
		if (xmlInputStream != null) {
			return xmlInputStream;
		}
		if (xmlFile != null) {
			return new FileInputStream(xmlFile);
		}
		if (xmlUri != null && xmlUri.length() > 0) {
			return new FileInputStream(new File(xmlUri));
		}
		return null;
	}
}
