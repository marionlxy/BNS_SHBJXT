package com.krm.voteplateform.web.synproject.event;

import java.io.File;

import org.springframework.context.ApplicationEvent;

/**
 * 同步项目注册的Event事件
 * 
 * @author JohnnyZhang
 */
public class SynProjectEvent extends ApplicationEvent {

	private static final long serialVersionUID = -4354282688552153451L;

	public SynProjectEvent(File file) {
		super(file);
	}

}
