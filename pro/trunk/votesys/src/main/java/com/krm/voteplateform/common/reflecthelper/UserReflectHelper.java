package com.krm.voteplateform.common.reflecthelper;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.web.ptcommissionorg.service.PtCommissionOrgService;
import com.krm.voteplateform.web.ptcommissionuser.service.PtBasUserService;

/**
 * ClassName:UserReflectHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年1月18日 下午10:31:21 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class UserReflectHelper {
	
	private PtBasUserService ptBasUserService=SpringContextHolder.getBean("ptBasUserService");
	//反射用户查询主持人信息
	public List<Map<String, Object>> HostUserReflection(Map<String, Object> param) {
//		HttpServletRequest curRequest = SysUserUtils.getCurRequest();
		Map<String, Object> mpparam = Maps.newHashMap();
		mpparam.put("code", param.get(MyBatisConstans.DYTABLE_KEY));
		//mpparam.put("code", param.get(MyBatisConstans.DYTABLE_KEY));
		mpparam.put("roleCategory", "02");//委员端角色
		List<Map<String, Object>> 	resultlist=ptBasUserService.findHostUserIds(mpparam);
		return resultlist;
	}

}

