package com.krm.voteplateform.common.prehandle;

import java.util.Map;

/**
 * 前处理
 * 
 * @author JohnnyZhang
 */
public interface IRequestHandler {

	public Object exec(Map<String, Object> param);
}
