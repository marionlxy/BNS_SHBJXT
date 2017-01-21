package com.krm.voteplateform.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.krm.voteplateform.web.constants.SessionKeys;

/**
 * 认证拦截器
 * 
 * @author Administrator
 */
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getRequestURI(); // 请求路径
		String ctx = request.getContextPath();
		String path = uri.replace(ctx, "");
		HttpSession session = request.getSession();
		// 若用户登录
		if (session.getAttribute(SessionKeys.USER_KEY) != null) {
			return true;
		} else {
			response.sendRedirect(ctx + "/login");
			return false;
		}

		//
		// //if(!ignorePath.contains(path)){
		// //获得session中的登陆用户
		// SysUser sessionUser = SysUserUtils.getSessionLoginUser();
		//
		// //获得缓存中的登陆用户
		// SysUser cacheUser = SysUserUtils.getCacheLoginUser();
		//
		// if (sessionUser == null || cacheUser == null) { // 转到登陆页面
		// response.sendRedirect(ctx + "/notlogin");
		// return false;
		// } else {
		//
		// Map<String, SysResource> allRes = BeetlUtils
		// .getBeetlSharedVars(Constant.CACHE_ALL_RESOURCE);
		// String perPath = path.substring(1);
		// SysResource sysResource = allRes.get(perPath);
		// //判断如果url不在数据库中，则默认都有权限访问
		// if (sysResource == null
		// || Constant.RESOURCE_COMMON.equals(sysResource.getCommon())) {
		// return true;
		// }
		//
		// //实时的权限验证,检测用户认证是否改变，如果认证改变则重置，否则不进行任何操作
		// SysUserUtils.setUserAuth();
		//
		// //从缓存中的用户权限 map<url:resource>
		// Map<String, SysResource> userRes = SysUserUtils.getUserResources();
		// if (userRes.containsKey(perPath)) { //有权限则过
		// return true;
		// } else { //没有权限跳到未有权限
		// response.sendRedirect(ctx + "/notauth");
		// return false;
		// }
		// }
		// }
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
