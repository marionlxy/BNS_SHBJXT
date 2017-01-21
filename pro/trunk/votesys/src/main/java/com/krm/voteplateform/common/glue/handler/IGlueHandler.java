package com.krm.voteplateform.common.glue.handler;

/**
 * 实际处理逻辑接口，根据参数取得结果
 * 
 * @author JohnnyZhang
 */
public interface IGlueHandler {
	/**
	 * 处理方法
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String handler(Object...params) throws Exception;
}
