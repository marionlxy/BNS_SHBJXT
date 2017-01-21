package com.krm.voteplateform.web.test.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;
import com.krm.voteplateform.web.test.model.Tm;
import com.krm.voteplateform.web.test.service.TestPageService;

/**
 * 分页实例
 * 
 * @author JohnnyZhang
 */
@Controller
@RequestMapping("testPage")
public class TestPageController extends BaseController {

	@Resource
	private TestPageService testPageService;

	/**
	 * 带动态表名
	 * 
	 * @param page
	 * @param test
	 * @return
	 */
	@RequestMapping("findTestPage")
	@ResponseBody
	//public Pagination<Map<String, Object>> findTestPage(HttpServletRequest request, @RequestParam("aa") String category,
	public Pagination<Map<String, Object>> findTestPage(HttpServletRequest request, Tm tm,
			Integer pageNumber, Integer pageSize) {
		// DyTableModel dyTableModel = new DyTableModel();
		// dyTableModel.tableName = "CENT";

		Pagination<Map<String, Object>> page = new Pagination<Map<String, Object>>(pageNumber, pageSize);
		
		Map<String, Object> otherMap = Maps.newHashMap();
		otherMap.put(MyBatisConstans.DYTABLE_KEY, "CENT");
//		otherMap.put("aa", category);//第一种方式
		otherMap.put("aa", tm.getCategory());
		List<Map<String, Object>> records = testPageService.findTestPage(page, otherMap);
		page.setRows(records);
		return page;
	}
	

}
