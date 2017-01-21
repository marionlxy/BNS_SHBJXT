package com.krm.voteplateform.web.login.service;

import java.util.List;

import com.krm.voteplateform.web.login.model.LoginUser;

public interface LoginService {

	/**
	 * 用户登录
	 * 
	 * @param loginName 用户名
	 * @param passWord 密码
	 * @return
	 */
	LoginUser login(String loginName, String passWord);

	/**
	 * 根据用户名称查询可以控制的委员会列表
	 * 
	 * @param loginName
	 * @return
	 */
	List<LoginUser> selectCommByUser(String loginName);

}
