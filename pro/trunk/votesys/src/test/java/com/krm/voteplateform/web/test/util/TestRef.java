package com.krm.voteplateform.web.test.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.utils.Reflect;

public class TestRef {

	public List<Map<String, Object>> aa() {
		List<Map<String, Object>> list = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", "1");
		map.put("name", "name1");
		list.add(map);
		map = Maps.newHashMap();
		map.put("id", "2");
		map.put("name", "name2");
		list.add(map);
		return list;
	}

	public static void main(String[] args) {
//		List<Map<String, Object>> list = Reflect.on("com.krm.voteplateform.web.test.util.TestRef").create().call("aa")
//				.unwrap();
//		String string = JSON.toJSON(list).toString();
//		System.out.println(string);
	}
}
