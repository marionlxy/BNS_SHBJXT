package com.krm.voteplateform.web.previewgen.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName:GenBtnService <br/>
 * Function: 生成按钮信息<br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年11月29日 上午11:56:40 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public interface GenBtnService {
		
	List<Map<String, Object>> getButtonTableInfo(String tableName, String resid);

	List<Map<String, Object>> getBtnTableWind(String tableName, String rsid);
}

