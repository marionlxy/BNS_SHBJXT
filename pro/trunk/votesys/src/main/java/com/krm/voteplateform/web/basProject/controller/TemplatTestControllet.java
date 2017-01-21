package com.krm.voteplateform.web.basProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.prehandle.IRequestHandler;
import com.krm.voteplateform.common.utils.IPUtils;
import com.krm.voteplateform.common.utils.Reflect;
import com.krm.voteplateform.web.basProject.service.BasMettingService;
import com.krm.voteplateform.web.constants.SysContants;
import com.krm.voteplateform.web.function.model.BasFunConf;
import com.krm.voteplateform.web.login.model.LoginUser;
import com.krm.voteplateform.web.mettingmem.service.BasMettingMemService;
import com.krm.voteplateform.web.ptsystemsmenu.service.PtSystemsMenuService;
import com.krm.voteplateform.web.util.LogoUtils;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
public class TemplatTestControllet  extends BaseController{
	
	@Resource
	private PtSystemsMenuService ptSystemsMenuService;

	@Resource
	private BasMettingService basMettingService;

	@Resource
	private BasMettingMemService basMettingMemService;

	private static final String SPECIAL_MENU_CODE = "menu4";
	private static final String SPECIAL_MENU_CODE_CHANGE = "menu5";

	/** 存在会议时显示的会议名称Key */
	private static final String METTING_NAME = "mettingName";

	/** 更新页面使用的对象KEY */
	private static final String UPDATE_PRE_OBJECT_KEY = "updateObj";

	private static final String SPECIAL_MENU_CODE_VOTE = "menu8";// 委员端当前会议菜单
	/** 存在会议时显示的会议编号Key */
	private static final String METTING_CODE = "mettingCode";

	/** 添加会议的FucntionCode */
	private static final String FUNCTION_CODE_ADD_METTING = "fun1301";

	/**
	 * 进入各个委员会主页面
	 * 
	 * @param request
	 * @param model
	 * @param code
	 * @return
	 */
	@RequestMapping("test/toMain/{code}")
	public String toMain(HttpServletRequest request, Model model, @PathVariable String code) {
		// 1.设置委员会Code进入到Session中
		SysUserUtils.setCurrentCommissionCode(code);
		LoginUser sessionLoginUser = SysUserUtils.getSessionLoginUser();
		sessionLoginUser.setCode(code);
		// 2.设置logo图片
		model.addAttribute("logoBase64", LogoUtils.getLogo());
		// 3.查询左侧菜单
		List<Map<String, Object>> userMenus = SysUserUtils.getUserMenus(request);
		model.addAttribute("menuList", userMenus);

		Map<String, Object> oneUserInfo = SysUserUtils.getOneUserInfo(request);
		String roleCategory = (String) oneUserInfo.get("roleCategory");
		model.addAttribute("roleCategory", roleCategory);
		if (SysContants.USER_BELONG_FLAG_COMMISSION.equalsIgnoreCase(sessionLoginUser.getUserBelongFlag())) {
			if ("02".equalsIgnoreCase(roleCategory)) {// 是委员
				Map<String, Object> findCurrentMeeting = basMettingService.findCurrentMeeting();// 取得当前会议
				if (!findCurrentMeeting.isEmpty()) {// 委员端角色信息
					model.addAttribute(METTING_CODE, findCurrentMeeting.get("specMettingCode"));// 设定会议名称
					model.addAttribute(METTING_NAME, findCurrentMeeting.get("specMettingTitle"));// 设定会议名称
					model.addAttribute("mettingId", findCurrentMeeting.get("id"));
				}
			}
		}
		logger.info(JSON.toJSONString(userMenus));
		return "test/ptsystemsMain";
	}

	/**
	 * 跳转各个委员会配置菜单
	 * 
	 * @param request
	 * @param model
	 * @param menuCode 菜单编码
	 * @return
	 */
	@RequestMapping("test/toIndex/{menuCode}")
	public String toIndex(HttpServletRequest request, Model model, @PathVariable String menuCode) {
		String toUrl = "test/" + SysUserUtils.getCurrentCommissionCode().toLowerCase() + "/";
		if (StringUtils.equals(SPECIAL_MENU_CODE, menuCode)) {// 若当前菜单Code传递过来的是特殊Code
			Map<String, Object> findCurrentMeeting = basMettingService.findCurrentMeeting();// 取得当前会议
			if (findCurrentMeeting.size()>0) {// 若存在当前会议
				menuCode = SPECIAL_MENU_CODE_CHANGE;// 重新指定跳转页面 menu5
				model.addAttribute(METTING_NAME, findCurrentMeeting.get("specMettingTitle"));// 设定会议名称
				model.addAttribute("mettingId", findCurrentMeeting.get("id"));
			}
			Map<String, Object> findPrepaMeeting = basMettingService.findPrepatOrMeeting();
			if (findPrepaMeeting !=null) {// 存在预备会议
				// menuCode = SPECIAL_MENU_CODE_CHANGE;// 重新指定跳转页面
				model.addAttribute(METTING_NAME, findPrepaMeeting.get("specMettingTitle"));// 设定会议名称
				model.addAttribute("mettingId", findPrepaMeeting.get("id")); // 会议ID
			}

		}
		if (StringUtils.equals(SPECIAL_MENU_CODE_VOTE, menuCode)) { // 委员端当前会议menu8
			Map<String, Object> findCurrentMeeting = basMettingService.findCurrentMeeting();// 取得当前会议
			if (findCurrentMeeting.size()>0) {// 若存委员端在当前会议
				model.addAttribute(METTING_NAME, findCurrentMeeting.get("specMettingTitle"));// 设定会议名称
				model.addAttribute("mettingId", findCurrentMeeting.get("id"));// 会议id
				model.addAttribute(METTING_CODE, findCurrentMeeting.get("specMettingCode"));// 会议编码

				String mettingId = (String) findCurrentMeeting.get("id");
				String clientAddress = IPUtils.getClientAddress(request);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mettingId", mettingId);
				map.put("IP", clientAddress);
				// 向委员会插入中插入与会信息
				basMettingMemService.saveBasMettingMem(map);
			}
		}
		toUrl += menuCode;
		return toUrl;
	}

	/**
	 * 跳转各个弹出框页面
	 * 
	 * @param request
	 * @param model
	 * @param functionCode 按钮编号
	 * @param id 更新操作数据的Code
	 * @return 跳转到指定页面，会将functionCode及id带入其中,当ID为0时，证明为添加页面
	 */
	@RequestMapping("test/toFunctionIndex")
	public String toFunctionIndex(HttpServletRequest request, Model model, String functionCode, String projectId,
			String mettingId) {
		String toUrl = "test/" + SysUserUtils.getCurrentCommissionCode().toLowerCase() + "/" + functionCode;
		model.addAttribute("functionCode", functionCode);// 会议Id
		model.addAttribute("projectId", projectId);// 项目Id

		Map<String, Object> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("functionCode", functionCode);
		BasFunConf oneBasFunConf = ptSystemsMenuService.getOneBasFunConf(map);
		// 若配置了跳转前处理方法
		if (StringUtils.isNotEmpty(oneBasFunConf.getSkipClassMethod())) {
			// 取得跳转前配置方法
			String skipClassMethod = oneBasFunConf.getSkipClassMethod().trim();
			IRequestHandler irh = Reflect.on(skipClassMethod).create().unwrap();
			// 若前台设置的值不为0,即不为添加页面
			if (!StringUtils.equals("0", projectId)) {
				map = Maps.newHashMap();
				map.put("projectId", projectId);
				map.put("mettingId", mettingId);
				Object exec = irh.exec(map);
				model.addAttribute(UPDATE_PRE_OBJECT_KEY, exec);
			} else {
				// 对添加会议界面做特殊处理
				if (FUNCTION_CODE_ADD_METTING.equals(functionCode)) {
					map = Maps.newHashMap();
					Object exec = irh.exec(map);
					if (exec instanceof Map) {
						model.addAllAttributes((Map<String, Object>) exec);
					}
				}
			}

		}
		return toUrl;
	}

	/**
	 * 进入系统功能下的角色管理菜单
	 * 
	 * @param request
	 * @param model
	 * @param code
	 * @return
	 */
	/*@RequestMapping("test/toRoleIndex")
	public String toRoleIndex(HttpServletRequest request, Model model) {
		return "sysfunction/rolemanage/rolemanage";
	}*/

	/**
	 * 进入系统功能下的机构管理菜单
	 * 
	 * @return
	 */
	/*@RequestMapping("ptsystems/toOrgIndex")
	public String toOrgIndex() {
		return "sysfunction/orgmanage/organ_list";
	}*/
}
