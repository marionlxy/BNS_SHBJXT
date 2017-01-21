package com.krm.voteplateform.web.previewgen.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krm.voteplateform.common.model.BootstrapTable;

/**
 * ClassName:GenConfServcie <br/>
 * Function: 生成业务类. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年11月21日 下午5:48:23 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public interface GenFileServcie {
	
	 Map<String, Object> getStaticInfo(String templateFileName, String toFileName);

	 /**
	  * 
	  * 得到信息
	  * @author lixy
	  * @return
	  * @since JDK 1.7
	  */
	BootstrapTable getTemplateTableInfo(String tableName,String rsid);
	

	/**
	 * 
	 * getBasResource:(查询资源表). <br/>
	 * @author lixy
	 * @return
	 * @since JDK 1.7
	 */
	 Set<Map<String, Object>> getBasResource(String tableName);
	
	/**
	 * 
	 * getSearchTableInfo:搜索查询框. <br/>
	 *
	 * @author lixy
	 * @param tableName
	 * @return
	 * @since JDK 1.7
	 */
	 List<Map<String,Object>>  getSearchTableInfo(String tableName,String rsid);


}

