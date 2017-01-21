/**
 * Project Name:votesys
 * File Name:TempUtil.java
 * Package Name:com.krm.voteplateform.web.previewgen.util
 * Date:2016年12月3日下午4:58:35
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.krm.voteplateform.web.previewgen.util;

import java.util.Map;

/**
 * ClassName:TempUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年12月3日 下午4:58:35 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class TempUtil {
		
	public static void TemplateCommon(Map<String, String> resultmp) {
		resultmp.put("ctxPath", "${ctxPath!}");//项目根路径
		resultmp.put("mettingName", "${mettingName!}");//会议名称
		resultmp.put("mettingCode", "${mettingCode!}");//编码
		resultmp.put("mettingId", "${mettingId!}");//会议Id
	}
}

