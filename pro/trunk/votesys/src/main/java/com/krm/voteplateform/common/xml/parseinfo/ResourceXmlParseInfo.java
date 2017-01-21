package com.krm.voteplateform.common.xml.parseinfo;

import java.io.InputStream;
import org.springframework.core.io.Resource;

import com.krm.voteplateform.common.xml.XmlParseInfo;

/**
 * XML解析信息实现类，通过Spring的Resource实现
 * 
 * @author JohnnyZhang
 * @version 1.1
 */
public class ResourceXmlParseInfo implements XmlParseInfo {

	private Resource resource;

	public ResourceXmlParseInfo(Resource resource) {
		this.resource = resource;
	}

	@Override
	public InputStream getXmlPath() throws Exception {
		return this.resource.getInputStream();
	}
}
