package com.krm.voteplateform.web.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.vo.MapExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.constants.SessionKeys;
import com.krm.voteplateform.web.test.service.TestPageService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
@RequestMapping("test")
public class TestExcelController extends BaseController {

	@Resource
	private TestPageService testPageService;

	@Resource
	private BeetlGroupUtilConfiguration beetlConfig;

	@RequestMapping("index")
	public String toIndex(HttpServletRequest request, Model model) {
		// BootstrapTable bt = new BootstrapTable();
		// bt.setIconSize("sm");
		// bt.setUrl("${ctxPath!}/testPage/findTestPage");
		// List<BootstrapTableColumn> columns = Lists.newArrayList();
		// BootstrapTableColumn btc = bt.new BootstrapTableColumn();
		// btc.setField("enName");
		// btc.setTitle("英文名称");
		// btc.setSortable(true);
		// columns.add(btc);
		// btc = bt.new BootstrapTableColumn();
		// btc.setField("cnName");
		// btc.setTitle("中文名称");
		// btc.setSortable(true);
		// columns.add(btc);
		// bt.setColumns(columns);
		//
		// GroupTemplate gt = beetlConfig.getGroupTemplate();
		// Template template = gt.getTemplate("/template/listTmpl.html");
		// template.binding("bt",bt);
		// String render = template.render();
		// FileUtils.writeFile(render, "H:\\list.html");
		SysUserUtils.getSession().setAttribute(SessionKeys.COMMISSION_CODE_KEY, "CENT");
		return "test/index";
	}

	/**
	 * 带有跨行的Excel导出
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("exporExcel")
	public String exportExcel(ModelMap modelMap, HttpServletRequest request) {

		List<ExcelExportEntity> entityList = new ArrayList<ExcelExportEntity>();
		ExcelExportEntity excelentity = new ExcelExportEntity("姓名", "name");
		excelentity.setNeedMerge(true);
		entityList.add(excelentity);
		entityList.add(new ExcelExportEntity("性别", "sex"));
		excelentity = new ExcelExportEntity(null, "students");
		List<ExcelExportEntity> temp = new ArrayList<ExcelExportEntity>();
		temp.add(new ExcelExportEntity("姓名", "name"));
		temp.add(new ExcelExportEntity("性别", "sex"));
		excelentity.setList(temp);
		entityList.add(excelentity);

		List<Map<String, Object>> dataResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < 10; i++) {
			map = new HashMap<String, Object>();
			map.put("name", "1" + i);
			map.put("sex", "2" + i);

			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
			tempList.add(map);
			tempList.add(map);
			map.put("students", tempList);

			dataResult.add(map);
		}

		modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);// 属性列表
		modelMap.put(MapExcelConstants.MAP_LIST, dataResult);// 数据源
		modelMap.put(MapExcelConstants.FILE_NAME, "测试");// 文件名称
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("测试人员列表", "人员列表", ExcelType.XSSF));// 导出参数

		return MapExcelConstants.JEECG_MAP_EXCEL_VIEW;
	}

	/**
	 * 普通导出
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("exportExcelCommon")
	public String exportExcelCommon(ModelMap modelMap, HttpServletRequest request) {

		// 属性列表
		List<ExcelExportEntity> entityList = Lists.newArrayList();
		ExcelExportEntity exportEntity = new ExcelExportEntity("部门", "depart");
		entityList.add(exportEntity);
		exportEntity = new ExcelExportEntity("姓名", "name");
		entityList.add(exportEntity);
		exportEntity = new ExcelExportEntity("电话", "phone");
		entityList.add(exportEntity);

		// 数据源
		List<Map<String, Object>> dataResult = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		map.put("depart", "部门1");
		map.put("name", "姓名1");
		map.put("phone", "1232");
		dataResult.add(map);
		map = Maps.newHashMap();
		map.put("depart", "部门2");
		map.put("name", "姓名2");
		map.put("phone", "1233");
		dataResult.add(map);
		map = Maps.newHashMap();
		map.put("depart", "部门3");
		map.put("name", "姓名3");
		map.put("phone", "1234");
		dataResult.add(map);

		modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);// 属性列表
		modelMap.put(MapExcelConstants.MAP_LIST, dataResult);// 数据源
		modelMap.put(MapExcelConstants.FILE_NAME, "普通导出测试");// 文件名称
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("测试人员列表", "人员列表", ExcelType.XSSF));// 导出参数

		return MapExcelConstants.JEECG_MAP_EXCEL_VIEW;
	}
}
