package com.krm.voteplateform.common.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Sets;
import com.krm.voteplateform.web.constants.SessionKeys;

/**
 * 清除页面缓存问题
 * 
 * @author JohnnyZhang
 */
public class ClearCachInterceptor implements HandlerInterceptor {

	private Set<String> ignorePath = Sets.newHashSet("/login", "/userlogin.do", "/toLogin", "/ErrorHandler");

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String uri = request.getRequestURI(); // 请求路径
		String ctx = request.getContextPath();
		String path = uri.replace(ctx, "");
		HttpSession session = request.getSession(true);
		Object obj = session.getAttribute(SessionKeys.USER_KEY);
		if (ObjectUtils.isEmpty(obj)) {//Session失效
			if (!ignorePath.contains(path)) {
				// Ajax请求
				if (request.getHeader("x-requested-with") != null
						&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);// 设置返回状态为403
				} else {// 普通请求
					response.sendRedirect(session.getServletContext().getContextPath() + "/toLogin");
				}
				return false;
			}
		} else {
			if ("/".equals(path)) {
				response.sendRedirect(session.getServletContext().getContextPath() + "/userlogin.do");
			}
		}
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-no-cache");
		response.setHeader("expires", "0");
		return true;
	}

}
