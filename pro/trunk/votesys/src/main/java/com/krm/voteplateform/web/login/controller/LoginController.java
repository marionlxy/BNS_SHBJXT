package com.krm.voteplateform.web.login.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.constants.SessionKeys;
import com.krm.voteplateform.web.constants.SysContants;
import com.krm.voteplateform.web.login.model.LoginUser;
import com.krm.voteplateform.web.login.service.LoginService;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * 登录逻辑
 * 
 * @author JohnnyZhang
 */
@Controller
public class LoginController extends BaseController {

	@Resource
	private LoginService loginService;

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param model
	 * @param loginName
	 * @param passWord
	 * @return
	 */
	@RequestMapping("userlogin.do")
	public String login(HttpServletRequest request, Model model, String loginName, String passWord) {
		logger.info("用户{}开始登录....", loginName);
		Result result = null;
		String tz = "login";// 默认跳转
		HttpSession session = request.getSession();
		LoginUser loginUser = null;
		try {
			Object attribute = session.getAttribute(SessionKeys.USER_KEY);
			if (attribute != null) {
				loginUser = (LoginUser) session.getAttribute(SessionKeys.USER_KEY);
				// 若用户名称不为空且与Session中用户名称不相同，证明为其他用户登录
				if (StringUtils.isNotEmpty(loginName) && !StringUtils.equals(loginName, loginUser.getUserName())) {
					// 清空Session数据
					session.removeAttribute(SessionKeys.USER_KEY);
					session.removeAttribute(SessionKeys.COMMISSION_CODE_KEY);
					loginUser = null;
				}
			}
			if (loginUser == null) {
				if (StringUtils.isAnyEmpty(loginName, passWord)) {
					logger.warn("登录后端校验失败");
					result = new Result(Result.WARN,
							"[" + SysContants.WARN_NULL_CODE + "]" + SysContants.WARN_NULL_MESSAGE);
					model.addAttribute("result",result);
					return tz;
				}
				Subject subject = SecurityUtils.getSubject();
				UsernamePasswordToken token = new UsernamePasswordToken(loginName, passWord);
				subject.login(token);
				// 登录成功
				loginUser = (LoginUser) subject.getPrincipal();
				session.setAttribute(SessionKeys.USER_KEY, loginUser);
			}
			if (StringUtils.equals(loginUser.getUserBelongFlag(), SysContants.USER_BELONG_FLAG_PLATEFORM_MANAGER)) {// 若为平台管理员
				tz = "plateform/index";// 跳转到平台管理初始页面
			} else {
				tz = "ptsystems/index";// 跳转到委员秘书端页面
			}
			result = Result.successResult();
		} catch (UnknownAccountException e) {
			result = Result.errorResult();
			result.setMsg(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			result = Result.errorResult();
			result.setMsg(e.getMessage());
		} catch (LockedAccountException e) {
			result = Result.errorResult();
			result.setMsg(e.getMessage());
		} catch (AuthenticationException e) {
			result = Result.errorResult();
			result.setMsg(e.getMessage());
		}
		model.addAttribute("result",result);
		return tz;
	}

	/**
	 * 用户登出处理
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.removeAttribute(SessionKeys.COMMISSION_CODE_KEY);
		session.removeAttribute(SessionKeys.USER_KEY);
		return "login";
	}

	/**
	 * 取得控制的委员会列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("ptsystems/getContrCommissionList")
	@ResponseBody
	public List<LoginUser> getContrCommissionList(HttpServletRequest request, Model model) {
		String loginName = SysUserUtils.getSessionLoginUser().getUserName();
		List<LoginUser> selectCommByUser = loginService.selectCommByUser(loginName);
		return selectCommByUser;
	}
}
