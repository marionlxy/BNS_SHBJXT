package com.krm.voteplateform.web.menuwinconf.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.menuwinconf.model.BasMenuWinConf;

public interface BasMenuWinConfTableMapper {
	//展示页面
	List<Map<String, Object>> showadd(Map<String, String> map);
	//查询出数据库里面的数据展示到页面上面去
	BasMenuWinConf selectCommtext(Map<String, String> map);
	//对修改的页面上面的数据进行保存
	int lastsave(Map<String, Object> map);
	//查找对应的下拉框里面的内容
	List<Map<String, Object>> showpull(Map<String, String> map);
	//分组后面的三位的处理
	List<Map<String, Object>> findSelectGroupList(Map<String, String> map);
	
	public Map<String, Object> getBtnOrder(Map<String, Object> params);

}
