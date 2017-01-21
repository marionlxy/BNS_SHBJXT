package com.krm.voteplateform.common.reflecthelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.common.utils.Reflect;
import com.krm.voteplateform.web.bascode.service.BasCodeService;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;
import com.krm.voteplateform.web.bascodegroup.service.BasCodeGroupService;
import com.krm.voteplateform.web.ptcommissionorg.service.PtCommissionOrgService;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * ClassName:OrgReflectHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 机构反射助手. <br/>
 * Date:     2017年1月18日 下午4:12:55 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */

public class OrgReflectHelper {

	private PtCommissionOrgService ptCommissionOrgService=SpringContextHolder.getBean("ptCommissionOrgService");
	
	//反射查询机构查询信息
	public List<Map<String, Object>> OrgReflection(Map<String, Object> param) {
//		HttpServletRequest curRequest = SysUserUtils.getCurRequest();
		Map<String, Object> mpparam = Maps.newHashMap();
		mpparam.put("code", param.get(MyBatisConstans.DYTABLE_KEY));
		List<Map<String, Object>> 	resultlist=ptCommissionOrgService.getCodeListOrg(mpparam);
		return resultlist;
	}
	
}

