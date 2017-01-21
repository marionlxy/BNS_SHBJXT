package com.krm.voteplateform.web.login.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.beetl.sql.core.SQLManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.utils.EncryptUtils;
import com.krm.voteplateform.web.constants.SysContants;
import com.krm.voteplateform.web.login.dao.LoginMapper;
import com.krm.voteplateform.web.login.model.LoginUser;
import com.krm.voteplateform.web.login.service.LoginService;

/**
 * 登录逻辑
 * 
 * @author JohnnyZhang
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private SQLManager sqlManager;

	@Resource
	private LoginMapper loginMapper;

	@RequestMapping("login")
	public String toLogin(HttpServletRequest request) {
		return "login";
	}

	public List<LoginUser> selectCommByUser(String loginName) {
		Map<String, String> map = Maps.newHashMap();
		map.put("loginName", loginName);
		List<LoginUser> selectCommByUser = loginMapper.selectCommByUser(map);
		return selectCommByUser;
	}

	/**
	 * 用户登录逻辑
	 * 
	 * @param loginName 登录名
	 * @param passWord 登录密码
	 * @return
	 */
	@Override
	public LoginUser login(String loginName, String passWord) {
		logger.info("用户{}开始登录....", loginName);
		LoginUser lu = null;
		String ps = EncryptUtils.MD5_HEX(passWord);
		Map<String, String> map = Maps.newHashMap();
		map.put("loginName", loginName);
		map.put("passWord", ps);
		Integer admin = loginMapper.isAdmin(map);
		if (admin == 0) {// 用户不是平台管理员
			// 查询是否为委员会用户
			Integer count = loginMapper.isCommUser(map);
			// 是委员会用户，查询所能控制的委员会列表。包含未启用状态的委员会
			if (count > 0) {
				List<LoginUser> selectCommByUser = loginMapper.selectCommByUser(map);
				if (selectCommByUser != null && !selectCommByUser.isEmpty()) {
					lu = selectCommByUser.get(0);// 从中取得第一项数据
					// 将查出的多余属性清空,当用户点击时，再次进行赋值操作
					lu.setSysTitle("");
					lu.setViewFlag("");
					lu.setCode("");
					lu.setUserBelongFlag(SysContants.USER_BELONG_FLAG_COMMISSION);
				} else {
					lu = new LoginUser();
					// 是委员会用户，但是未加入到任何委员会中
					lu.setErrorFlag(true);
				}
			}
		} else {
			// 根据用户名取得用户列表
			List<LoginUser> list = loginMapper.selectByUserName(loginName);
			if (list != null && list.size() > 0) {
				logger.info("平台管理员{}登录成功", loginName);
				lu = list.get(0);// 取得用户
				lu.setUserBelongFlag(SysContants.USER_BELONG_FLAG_PLATEFORM_MANAGER);
			}
		}
		return lu;
	}
}
