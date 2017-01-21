package com.krm.voteplateform.common.xml;

/**
 * XML解析器接口. 实现此类的相关解析方法，来完成一次XML的解析，返回解析后的根节点映射对象.
 * 
 * @author JohnnyZhang
 * @version 1.1
 */
public interface XmlParser {
	/**
	 * 执行次解析器的解析，返回解析后的根节点映射对象.
	 * 
	 * @return 解析后的根节点映射对象.
	 * @throws Exception 如果发生任何错误.
	 */
	public XmlElement parse() throws Exception;

	public void setSeparator(String sep);

	public String getSeparator();
}
