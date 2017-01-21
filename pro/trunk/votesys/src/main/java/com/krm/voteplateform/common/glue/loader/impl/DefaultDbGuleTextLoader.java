package com.krm.voteplateform.common.glue.loader.impl;

import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import com.krm.voteplateform.common.glue.loader.AbstractGlueTextLoader;

/**
 * PT_DYNAMIC_SOURCE表处理类
 * 
 * @author JohnnyZhang
 */
public class DefaultDbGuleTextLoader extends AbstractGlueTextLoader {

	@Override
	public LobHandler getLobHandle() {
		return new DefaultLobHandler();
	}
}
