package com.krm.voteplateform.web.menuwinconf.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.menuwinconf.model.BasMessage;

public interface BasMessageMapper {

	List<Map<String, Object>> finbasMessageAll(Map<String, String> map);

	BasMessage upBasMessageText(Map<String, String> map);
	
	/**
	 * 获取 code=msg01202提示信息
	 * @author zhangYuHai
	 * @param map
	 * @return
	 */
	Map<String, Object> findContionMessage(HashMap<String, String> map);

}


