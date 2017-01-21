package com.krm.voteplateform.common.xml;

import com.krm.voteplateform.common.xml.parser.DefaultXmlParser;

/**
 * XML解析器的创建工厂类. 通过此类的{@link #createXmlParser(XmlParseInfo)}方法创建一个XML解析器对象.
 * 
 * @author JohnnyZhang
 * @version 1.1
 */
public class XmlParserFactory {

	/**
	 * 私有构造器.
	 */
	private XmlParserFactory() {

	}

	/**
	 * 根据解析信息构造一个XML解析器.
	 * 
	 * @param xmlParseInfo 一个包含解析信息的对象.
	 * @return 一个XML解析器.
	 */
	public static XmlParser createXmlParser(XmlParseInfo xmlParseInfo) {
		return new DefaultXmlParser(xmlParseInfo);
	}
}
