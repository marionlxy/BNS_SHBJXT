package com.krm.voteplateform.web.glue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.exception.VoteBussinessException;
import com.krm.voteplateform.common.glue.GlueCodeFactory;
import com.krm.voteplateform.common.glue.model.PtDynamicSource;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * 动态代码控制器
 * 
 * @author JohnnyZhang
 */
@Controller
@RequestMapping("pt/glueCode")
public class GlueCodeController extends BaseController {

	private String getParentDic() {
		return "plateform/glueCode/";
	}

	/**
	 * 跳转主页面
	 * 
	 * @return
	 */
	@RequestMapping("toIndex")
	public String toGlueCodeIndex() {
		return getParentDic() + "glueCode_list";
	}

	/**
	 * 跳转编辑页
	 * 
	 * @param request
	 * @param modelMap
	 * @param id
	 * @return
	 */
	@RequestMapping("toGlueCodeEdit")
	public String toGlueCodeEdit(HttpServletRequest request, ModelMap modelMap, String id) {
		if (StringUtils.isNotEmpty(id) && !"-1".equals(id)) {
			PtDynamicSource load = GlueCodeFactory.getNowGlueTextLoader().load(id);
			modelMap.addAttribute("updateObj", load);
		}
		return getParentDic() + "glueCode_edit";
	}

	/**
	 * 跳转详情页
	 * 
	 * @param request
	 * @param modelMap
	 * @param id
	 * @return
	 */
	@RequestMapping("toGlueCodeView")
	public String toGlueCodeView(HttpServletRequest request, ModelMap modelMap, String id) {
		PtDynamicSource load = GlueCodeFactory.getNowGlueTextLoader().load(id);
		modelMap.addAttribute("updateObj", load);
		return getParentDic() + "glueCode_view";
	}

	/**
	 * 根据类型取得动态代码
	 * 
	 * @param request
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping("getGlueListByType/{type}")
	@ResponseBody
	public List<PtDynamicSource> getGlueListByType(HttpServletRequest request, Model model, @PathVariable String type) {
		List<PtDynamicSource> listByType = GlueCodeFactory.getNowGlueTextLoader().getListByType(type);
		return listByType;
	}

	/**
	 * 插入
	 * 
	 * @param request
	 * @param model
	 * @param ptDynamicSource
	 * @return
	 */
	@RequestMapping("insertGlueCode")
	@ResponseBody
	public Result insertGlueCode(HttpServletRequest request, Model model, PtDynamicSource ptDynamicSource) {
		logger.info("用户{}开始插入动态代码....", SysUserUtils.getSessionLoginUser().getId());
		Result result = Result.errorResult();
		try {
			int insert = GlueCodeFactory.getNowGlueTextLoader().insert(ptDynamicSource);
			if (insert > 0) {
				result = Result.successResult();
				logger.info("插入动态代码成功");
			}
		} catch (VoteBussinessException e) {
			result.setMsg(e.getMessage());
			logger.error("插入动态代码失败", e);
		} catch (IllegalArgumentException e) {
			result.setMsg("参数不可为空");
			logger.warn("", e);
		} catch (Exception e) {
			logger.error("插入动态代码失败", e);
		}
		return result;
	}

	/**
	 * 更新
	 * 
	 * @param request
	 * @param model
	 * @param ptDynamicSource
	 * @return
	 */
	@RequestMapping("updateGlueCode")
	@ResponseBody
	public Result updateGlueCode(HttpServletRequest request, Model model, PtDynamicSource ptDynamicSource) {
		try {
			logger.info("用户{}开始更新动态代码表....", SysUserUtils.getSessionLoginUser().getId());
			int update = GlueCodeFactory.getNowGlueTextLoader().update(ptDynamicSource);
			logger.info("用户{}更新动态代码表成功，更新数量{}", SysUserUtils.getSessionLoginUser().getId(), update);
			logger.info("通知更新器去更新代码实体缓存...");
			GlueCodeFactory.clearCache(ptDynamicSource.getId());
			return Result.successResult();
		} catch (Exception e) {
			logger.error("更新动态代码表异常", e);
			return Result.errorResult();
		}
	}

}
