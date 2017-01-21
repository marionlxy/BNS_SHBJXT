package com.krm.voteplateform.common.beetl.factorys;

import org.beetl.core.Tag;
import org.beetl.core.TagFactory;
import org.beetl.ext.jsp.IncludeJSPTag;
import org.springframework.stereotype.Component;

/**
 * 加载InclueJSP标签
 * @author JohnnyZhang
 */
@Component
public class JspInclueFactory implements TagFactory {

	@Override
	public Tag createTag() {
		return new IncludeJSPTag();
	}

}
