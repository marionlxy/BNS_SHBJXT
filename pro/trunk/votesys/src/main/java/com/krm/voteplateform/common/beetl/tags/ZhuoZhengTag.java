package com.krm.voteplateform.common.beetl.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * 封装卓正自定义标签
 * 
 * @author JohnnyZhang
 */
public class ZhuoZhengTag extends Tag {

	private Logger logger = LoggerFactory.getLogger(ZhuoZhengTag.class);

	private Map<String, String> map = new HashMap<String, String>() {
		private static final long serialVersionUID = 7611928743216371435L;
		{
			put("office", "PO_");// Office文档
			put("pdf", "PO_PDF");// PDF文件
			put("htmlsign", "PO_HS");
			put("filemaker", "PO_FM");
		}
	};

	@Override
	public void render() {
		@SuppressWarnings("rawtypes")
		Map attrs = (Map) args[1];
		String id = (String) attrs.get("id");
		String type = (String) attrs.get("type");
		Object attribute = null;
		if (StringUtils.isNotEmpty(type)) {
			if (map.containsKey(type.toLowerCase())) {
				attribute = SysUserUtils.getCurRequest().getAttribute(map.get(type.toLowerCase()) + id);
			}
		}
		if (attribute != null) {
			try {
				this.ctx.byteWriter.writeObject(attribute);
			} catch (IOException e) {
				logger.error("调用自定义封装卓正Office标签发生异常", e);
			}
		}
	}

}
