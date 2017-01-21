package com.krm.voteplateform.common.beetl.factorys;

import org.beetl.core.Tag;
import org.beetl.core.TagFactory;
import org.springframework.stereotype.Component;

import com.krm.voteplateform.common.beetl.tags.ZhuoZhengTag;

/**
 * 封装卓正自定义标签工厂类
 * 
 * @author JohnnyZhang
 */
@Component
public class ZhuoZhengFactory implements TagFactory {

	@Override
	public Tag createTag() {
		return new ZhuoZhengTag();
	}

}
