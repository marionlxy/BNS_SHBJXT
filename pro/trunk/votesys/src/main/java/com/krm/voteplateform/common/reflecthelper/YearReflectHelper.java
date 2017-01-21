package com.krm.voteplateform.common.reflecthelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.common.utils.UUIDGenerator;

/**
 * ClassName:YearReflectHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年1月19日 下午5:36:15 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class YearReflectHelper {
	//得到年s
	public List<Map<String, Object>> YearReflection(Map<String, Object> param) {
		List<Map<String, Object>> 	resultlist=new ArrayList<Map<String, Object>>();
		String year = DateUtils.getYear();
		for (int i = -7; i < 3; i++) {
			Map<String, Object> map=Maps.newHashMap();
			map.put("id", UUIDGenerator.getUUID());
			int value = Integer.parseInt(year)+i;
			map.put("ecode", value);
			map.put("mapName", value);
			resultlist.add(map);
		}
		return resultlist;
	}
}

