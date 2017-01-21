package com.krm.voteplateform.common.xml.parseinfo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.krm.voteplateform.common.xml.XmlParseInfo;

/**
 * XML解析信息实现类，此类可以将一个包含XML内容的字符串直接设置为解析源..
 * 
 * @author JohnnyZhang
 * @version 1.1
 */
public class ContentXmlParseInfo implements XmlParseInfo {

	// XML内容.
	private String xmlContent;

	/**
	 * 默认的构造器.
	 */
	public ContentXmlParseInfo() {
	}

	/**
	 * 设置XML文本内容作为解析源.
	 * 
	 * @param xmlContent XML文本内容.
	 */
	public void setXmlPath(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	/**
	 * 返回待解析的XML流.
	 * 
	 * @return 待解析的XML流.
	 * @throws Exception 如果发生任何错误.
	 */
	public InputStream getXmlPath() throws Exception {
		return new ByteArrayInputStream(xmlContent.getBytes());
	}
}
