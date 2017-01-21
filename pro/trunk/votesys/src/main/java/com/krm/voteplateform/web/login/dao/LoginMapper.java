package com.krm.voteplateform.web.login.dao;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.login.model.LoginUser;

/**
 * 登录Mapper
 * 
 * @author JohnnyZhang
 */
public interface LoginMapper {

	/**
	 * 判断是否在委员会用户表中
	 * 
	 * @param map
	 * @return
	 */
	public Integer isAdmin(Map<String, String> map);

	/**
	 * 根据用户名查询管理员用户
	 * 
	 * @param loginName 登录名称
	 * @return
	 */
	public List<LoginUser> selectByUserName(String loginName);

	/**
	 * 查询是否为委员会用户
	 * 
	 * @param map
	 * @return
	 */
	public Integer isCommUser(Map<String, String> map);

	/**
	 * 根据用户名称查询委员会列表
	 * 
	 * @param map
	 * @return
	 */
	public List<LoginUser> selectCommByUser(Map<String, String> map);
}
