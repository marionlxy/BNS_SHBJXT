package com.krm.voteplateform.web.function.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.function.model.BasFunConf;

public interface PtBasFunConfMapper {
	//得到list impl层  展示功能操作里面的添加的表
	List<Map<String, Object>> showadd(Map<String, String> map);
	//save
	//void savafunction();
	//验证
	int selectSysFunctionCode(Map<String, String> map);
	//修改basFunConf
	BasFunConf selectCommtext(Map<String, String> map);
	//更改保存
	boolean savedate(Map<String, String> map);
	//查询固定的
	BasFunConf findById(Map<String, String> map);
	//合并后的插入
	void savetwodate(Map<String, Object> map);
	//获取下拉框里面的数值
	List<Map<String, Object>> showpull(Map<String, String> map);
	//传值name
	String selectName(String tempid);
	/**
	 * 
	 * findBtnBasFunConf:(查询按钮信息). <br/>
	 * @author lixy
	 * @param mp
	 * @return
	 * @since JDK 1.7
	 */
	List<Map<String, Object>> findBtnBasFunConf(Map<String, String> mp);
	/**
	 * 
	 * findBtnBasFunConf:(查询弹出窗高度信息). <br/>
	 * @author lixy
	 * @param mp
	 * @return
	 * @since JDK 1.7
	 */
	List<Map<String, Object>> findwindBasFunConf(Map<String, String> mp);
	/**
	 * 
	 * findBtnBasFunConf:(确定是弹出窗). <br/>
	 * @author lixy
	 * @param mp
	 * @return
	 * @since JDK 1.7
	 */
	List<Map<String, Object>> findBtnBasFunConfWind(Map<String, String> mp);
	
	
	/**
	 * 
	 * getOneBasFunConf:(查询指定操作功能). <br/>
	 * @author lixy
	 * @param map
	 * @return
	 * @since JDK 1.7
	 */
	BasFunConf getOneBasFunConf(Map<String, Object> map);
	

}
