package com.krm.voteplateform.web.menuwinconf.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.web.menuwinconf.model.BasCusConf;
import com.krm.voteplateform.web.menuwinconf.service.BasCusConfService;

@Controller
@RequestMapping("pt/bascusconf")
public class BasCusConfController extends BaseController {

	@Autowired
	private BasCusConfService basCusConfService;

	/*
	 * 设置弹出窗体大小
	 */
	@RequestMapping("saveWinSize")
	@ResponseBody
	public Result saveWinSize(String functionCode, HttpServletRequest request) {
		BasCusConf basCusConf = new BasCusConf();
		String height = request.getParameter("height");
		String width = request.getParameter("width");
		String val = height + ";" + width;
		basCusConf.setVal(val);
		basCusConf.setOtid(functionCode);
		basCusConf.setType("1");
		boolean isExist = basCusConfService.isExist(functionCode);// 判断表中是否存在窗体设置
		if (isExist) {
			// 如果存在先删除表中数据
			logger.info("表中数据存在");
			basCusConfService.delete(functionCode);
			logger.info("表中数据已经删除");
			boolean insert = basCusConfService.save(basCusConf);
			if (insert) {
				logger.info("数据添加成功");
				return Result.successResult();
			} else {
				logger.info("数据添加失败");
				return Result.errorResult();
			}
		} else {
			//不存在则直接添加数据
			boolean insert = basCusConfService.save(basCusConf);
			if (insert) {
				logger.info("数据添加成功");
				return Result.successResult();
			} else {
				logger.info("数据添加失败");
				return Result.errorResult();
			}
		}

	}

}
