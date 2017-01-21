package com.krm.voteplateform.common.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Node;

/**
 * XML元素类. 经过解析的XML，最终将解析成此对象，此对象表示一个节点，可以获得节点的相关信息，如节点间的文本，节点名称和父节点.
 * 同时还可以获得节点的子节点，XmlElement本身是一个List容器，容器本身存储的即为此对象的子节点对象.
 * 
 * @author JohnnyZhang
 * @version 1.1
 */
public class XmlElement implements List<XmlElement> {

	// 元素的节点名称.
	private String elementName;

	// 节点与节点之间的文本，如果节点之间存在多个文本，那么多个文件将通过","分隔符来分隔.
	private String nodeText;

	// 节点的父节点，如果一个节点存在父节点，那么返回此节点的父节点对象，否则返回null.
	private XmlElement father;

	// 以List方式存储的当前节点的所有子节点集合.
	private List<XmlElement> children;

	// 与此对象关联的XML节点对象.
	private Node node;

	// 解析为此对象的XML解析器对象.
	private XmlParser xmlParser;

	// 此节点的属性集合.
	private Map<String, String> attributes;

	/**
	 * 默认的构造器.
	 */
	public XmlElement() {
		children = new ArrayList<XmlElement>();
		attributes = new HashMap<String, String>();
	}

	/**
	 * 为此元素添加一个属性.
	 * 
	 * @param key 属性的键.
	 * @param value 属性的值.
	 */
	public void addAttribute(String key, String value) {
		attributes.put(key, value);
	}

	/**
	 * 根据键值获得对应的属性值.
	 * 
	 * @param key 属性的键.
	 * @return 属性的值.
	 */
	public String getAttribute(String key) {
		return attributes.get(key);
	}

	/**
	 * 返回属性的键集合.
	 * 
	 * @return 属性的键集合.
	 */
	public Set<String> keySet() {
		return attributes.keySet();
	}

	/**
	 * 返回此元素的属性数量.
	 * 
	 * @return 此元素的属性数量.
	 */
	public int attributesSize() {
		return attributes.size();
	}

	/**
	 * 判断此元素是否包含指定的属性.
	 * 
	 * @param key 属性的键.
	 * @return 如果包含则返回true，否则返回false.
	 */
	public boolean containsKey(String key) {
		return attributes.containsKey(key);
	}

	public boolean add(XmlElement o) {
		return children.add(o);
	}

	public void add(int index, XmlElement element) {
		children.add(index, element);
	}

	public boolean addAll(Collection<? extends XmlElement> c) {
		return children.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends XmlElement> c) {
		return children.addAll(index, c);
	}

	public void clear() {
		children.clear();
	}

	public boolean contains(Object o) {
		return children.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return children.containsAll(c);
	}

	public XmlElement get(int index) {
		return children.get(index);
	}

	public int indexOf(Object o) {
		return children.indexOf(o);
	}

	public boolean isEmpty() {
		return children.isEmpty();
	}

	public Iterator<XmlElement> iterator() {
		return children.iterator();
	}

	public int lastIndexOf(Object o) {
		return children.lastIndexOf(o);
	}

	public ListIterator<XmlElement> listIterchildren() {
		return children.listIterator();
	}

	public ListIterator<XmlElement> listIterator(int index) {
		return children.listIterator(index);
	}

	public boolean remove(Object o) {
		return children.remove(o);
	}

	public XmlElement remove(int index) {
		return children.remove(index);
	}

	public boolean removeAll(Collection<?> c) {
		return children.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return children.retainAll(c);
	}

	public XmlElement set(int index, XmlElement element) {
		return children.set(index, element);
	}

	public int size() {
		return children.size();
	}

	public List<XmlElement> subList(int fromIndex, int toIndex) {
		return children.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return children.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return children.toArray(a);
	}

	public ListIterator<XmlElement> listIterator() {
		return children.listIterator();
	}

	/**
	 * 返回节点与节点之间的文件.
	 * 
	 * @return 节点与节点之间的文件.
	 */
	public String getNodeText() {
		return nodeText;
	}

	/**
	 * 设置节点与节点之间的文件.
	 * 
	 * @param nodeText 节点与节点之间的文件.
	 */
	public void setNodeText(String nodeText) {
		this.nodeText = nodeText;
	}

	/**
	 * 返回此对象的父对象.如果返回null，表示此对象是根节点.
	 * 
	 * @return 此对象的父对象.
	 */
	public XmlElement getFather() {
		return father;
	}

	/**
	 * 设置此对象的父对象.
	 * 
	 * @param father 此对象的父对象.
	 */
	public void setFather(XmlElement father) {
		this.father = father;
	}

	/**
	 * 返回与此对象关联的XML节点对象.
	 * 
	 * @return 与此对象关联的XML节点对象.
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * 设置与此对象关联的XML节点对象.
	 * 
	 * @param node 与此对象关联的XML节点对象.
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * 返回解析为此对象的XML解析器对象.
	 * 
	 * @return 解析为此对象的XML解析器对象.
	 */
	public XmlParser getXmlParser() {
		return xmlParser;
	}

	/**
	 * 设置解析为此对象的XML解析器对象.
	 * 
	 * @param xmlParser 解析为此对象的XML解析器对象.
	 */
	public void setXmlParser(XmlParser xmlParser) {
		this.xmlParser = xmlParser;
	}

	/**
	 * 返回元素的节点名称.
	 * 
	 * @return 元素的节点名称.
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * 设置元素的节点名称.
	 * 
	 * @param elementName 元素的节点名称.
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

}