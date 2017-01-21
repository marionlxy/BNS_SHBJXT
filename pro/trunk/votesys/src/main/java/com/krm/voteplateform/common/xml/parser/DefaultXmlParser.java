package com.krm.voteplateform.common.xml.parser;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.krm.voteplateform.common.xml.XmlElement;
import com.krm.voteplateform.common.xml.XmlParseInfo;
import com.krm.voteplateform.common.xml.XmlParser;

/**
 * 组件提供的XML默认解析器，此解析器由XmlParserFactory创建.
 * 
 * @author JohnnyZhang
 * @version 1.1
 */
public class DefaultXmlParser implements XmlParser {

	private String separator = ",";

	// XML输入流.
	private InputStream xmlInputStream;
	// 解析XML的Document对象.
	private Document document;
	// 解析信息对象.
	private XmlParseInfo xmlParseInfo;

	/**
	 * 根据解析信息类创建一个解析器.
	 * 
	 * @param xmlParseInfo 解析信息类.
	 */
	public DefaultXmlParser(XmlParseInfo xmlParseInfo) {
		this.xmlParseInfo = xmlParseInfo;
	}

	/**
	 * 执行次解析器的解析，返回解析后的根节点映射对象.
	 * 
	 * @return 解析后的根节点映射对象.
	 * @throws Exception 如果发生任何错误.
	 */
	public XmlElement parse() throws Exception {

		// 取得共通的XML解析参数.
		this.xmlInputStream = this.xmlParseInfo.getXmlPath();
		// 初始化Document对象.
		initDocument();
		// 处理根节点对象.
		Node root = document.getFirstChild();
		XmlElement rootObject = getXmlElement(root.getNodeName());
		// 关联节点信息.
		associate(root, rootObject);
		// 遍历子节点.
		addChildren(root, rootObject);
		return rootObject;
	}

	// 初始化document对象.
	private void initDocument() throws Exception {
		// 导入待解析的XML文件.
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.document = builder.parse(xmlInputStream);
	}

	// 根据一个类名，获得XmlElement对象.
	private XmlElement getXmlElement(String name) throws Exception {
		XmlElement element = new XmlElement();
		element.setElementName(name);
		return element;
	}

	// 直接节点和对象的相关关联操作.
	private void associate(Node node, XmlElement nodeObject) throws Exception {
		// 关联节点
		nodeObject.setNode(node);
		// 关联解析器
		nodeObject.setXmlParser(this);
		// 赋值文本.
		setText(node, nodeObject);
		// 赋值属性.
		setAttribute(node, nodeObject);
	}

	// 设置对象的nodeText属性.
	private void setText(Node node, XmlElement nodeObject) throws Exception {
		NodeList nodes = node.getChildNodes();
		StringBuffer text = new StringBuffer("");
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.TEXT_NODE || nodes.item(i).getNodeType() == Node.CDATA_SECTION_NODE) {
				if (nodes.item(i).getNodeValue().matches("\\p{Space}*")) {
					continue;
				}
				text.append(nodes.item(i).getNodeValue());
				text.append(separator);
			}
		}
		String textValue = text.toString();
		if (text.length() != 0) {
			textValue = text.substring(0, text.length() - 1);
		}
		textValue = textValue.replace("\\p{Blank}+,", "");
		textValue = textValue.replace(separator + "\\p{Blank}+", "");
		nodeObject.setNodeText(textValue.trim());
	}

	// 设置对象的属性.
	private void setAttribute(Node node, XmlElement nodeObject) throws Exception {
		NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			String attriName = attrs.item(i).getNodeName();
			String fieldName = attriName.substring(attriName.indexOf(":") + 1);
			nodeObject.addAttribute(fieldName, attrs.item(i).getNodeValue());
		}
	}

	// 设置对象的子对象.
	private void addChildren(Node node, XmlElement nodeObject) throws Exception {
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			// 获得子节点
			Node n = nodes.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				XmlElement child = getXmlElement(n.getNodeName());
				nodeObject.add(child);
				associate(n, child);
				child.setFather(nodeObject);
				if (n.getChildNodes().getLength() > 0) {
					addChildren(n, child);
				}
			}
		}
	}

	/**
	 * 返回解析此XML的Document对象.
	 * 
	 * @return 解析此XML的Document对象.
	 */
	public Document getDocument() {
		return document;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
