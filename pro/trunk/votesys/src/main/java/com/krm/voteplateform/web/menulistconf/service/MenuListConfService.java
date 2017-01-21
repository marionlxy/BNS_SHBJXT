package com.krm.voteplateform.web.menulistconf.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.menulistconf.model.BasMenuListConf;
import com.krm.voteplateform.web.sys.model.BasDict;

public interface MenuListConfService {

	List<Map<String, Object>> listMenuConf(String resid);

	List<Map<String, Object>> getAllField();

	List<String> getListType();

	List<Map<String, Object>> getDataSource();

	boolean saveMenuConf(BasMenuListConf basMenuListConf);

	Map<String, Object> getMenuForm(String id);

	boolean saveMeUpdate(BasMenuListConf basMenuListConf);

	void deleteById(String ids);

	boolean isExistDicid(String repid,String resid);
/**
 * 导出信息的表头
 * @return
 */
	List<Map<String,Object>> findMenuListConf(String resid);

}
