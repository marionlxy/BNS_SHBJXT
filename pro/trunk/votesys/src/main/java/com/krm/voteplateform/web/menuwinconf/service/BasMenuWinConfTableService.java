package com.krm.voteplateform.web.menuwinconf.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.function.model.BasFunConf;
import com.krm.voteplateform.web.menuwinconf.model.BasMenuWinConf;

public interface BasMenuWinConfTableService {
	//保存增加页面上的新建的表
	boolean savawinconfTable(BasMenuWinConf basMenuWinConf,String groupid);
	//展示表里面的数据
	List<Map<String, Object>> selectAll(String functionCode);
	//修改
	BasMenuWinConf selectCommtext(String functionCode,String id);
	//更改页面之后的保存
	boolean lastsave(BasMenuWinConf basMenuWinConf);
	//保存更新
	Boolean saveUpdate(String basdicts);
	//查询下拉框里面的东西
	List<Map<String, Object>> selectpull(String functionCode);
	
	public Map<String, Object> getBtnOrder(Map<String, Object> params);
	
	
	

}
