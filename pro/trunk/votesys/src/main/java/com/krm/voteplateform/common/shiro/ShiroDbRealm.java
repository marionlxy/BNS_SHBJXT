package com.krm.voteplateform.common.shiro;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.web.constants.SysContants;
import com.krm.voteplateform.web.login.model.LoginUser;
import com.krm.voteplateform.web.login.service.LoginService;

/**
 * 认证管理
 * 
 * @author JohnnyZhang
 */
public class ShiroDbRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		logger.info("Shiro登录认证启动");
		String loginName = (String) authcToken.getPrincipal();
		String passWord = new String((char[]) authcToken.getCredentials());
		LoginService loginService = SpringContextHolder.getBean("loginService");
		LoginUser login = loginService.login(loginName, passWord);
		// 账号不存在
		if (login == null) {
			throw new UnknownAccountException("账号或密码不正确");
		} else {
			if (login.isErrorFlag()) {
				throw new UnknownAccountException("对不起,您不是委员会用户,不能登录");
			}
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(login, passWord, getName());
		logger.info("Shiro登录认证结束");
		return info;
	}

	/**
	 * 权限认证
	 */
	// TODO
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		LoginUser shiroUser = (LoginUser) principals.getPrimaryPrincipal();
		Set<String> urlSet = Sets.newHashSet();
		Set<String> roleNameSet = Sets.newHashSet();
		// 若用户属于委员会系统
		if (shiroUser.getUserBelongFlag().equals(SysContants.USER_BELONG_FLAG_COMMISSION)) {

		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(urlSet);
		info.addRoles(roleNameSet);
		return info;
	}

}
