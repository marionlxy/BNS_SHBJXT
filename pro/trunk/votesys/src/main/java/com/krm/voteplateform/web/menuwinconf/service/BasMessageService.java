package com.krm.voteplateform.web.menuwinconf.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.menuwinconf.model.BasMessage;

public interface BasMessageService {

	List<Map<String, Object>> finbasMessage(String massCodes);

	BasMessage upBasMessageText(String id);

	Boolean savUpBasMessage(BasMessage basMessage);
	
	/**
	 *	获取 code：提示信息
	 * @param  msgfuncodes
	 * @return
	 */
	
	Result findMessageByMegfuncode(String msgfuncodes);

}
