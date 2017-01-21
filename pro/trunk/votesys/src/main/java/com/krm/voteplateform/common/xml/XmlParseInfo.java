package com.krm.voteplateform.common.xml;

import java.io.InputStream;

/**
 * XML解析信息接口. 此接口定义了一个XML解析所需要的必须解析信息.
 * 
 * @author JohnnyZhang
 * @version 1.1
 */
public interface XmlParseInfo {
	/**
	 * 返回待解析的XML流.
	 * 
	 * @return 待解析的XML流.
	 * @throws Exception 如果发生任何错误.
	 */
	public InputStream getXmlPath() throws Exception;
}
