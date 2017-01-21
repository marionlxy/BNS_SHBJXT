package com.krm.voteplateform.web.log.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLScript;
import org.beetl.sql.core.kit.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.abel533.sql.SqlMapper;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.mybatis.plugins.DyTableModel;
import com.krm.voteplateform.common.mybatis.plugins.pagination.Page;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.log.dao.PtLogMapper;
import com.krm.voteplateform.web.log.model.PtLog;
import com.krm.voteplateform.web.log.service.PtLogService;

/**
 * Log控制器
 * 
 * @author JohnnyZhang
 */
@Controller
@RequestMapping("pt/pltlog")
public class PtLogController extends BaseController {

	@Autowired
	private PtLogService<PtLog> ptLogService;

	@Autowired
	private PtLogMapper ptLogMapper;

	@Autowired
	private SqlMapper sqlMapper;

	@Autowired
	private SQLManager sqlManager;

	/**
	 * 保存Log
	 * 
	 * @param ptLog
	 * @param request
	 */
	@RequestMapping("savePtLog")
	@ResponseBody
	public Result savePtLog(PtLog ptLog, String sysFlag, HttpServletRequest request) {
		logger.info("开始保存log");
		ptLog.setId(UUIDGenerator.getUUID());
		ptLog.setCreateTime(new Date());
		ptLogService.saveLog(ptLog);
		logger.info("保存log结束");
		return Result.successResult();

	}

	@RequestMapping("list")
	@ResponseBody
	public List<Map<String, Object>> list(String id, HttpServletRequest request) {
		logger.info("开始查询Log日志表");
		List<Map<String, Object>> listAll = ptLogService.listAll();
		logger.info("查询Log日志表结束");
		return listAll;
	}

	// @RequestMapping("findPageInfo")
	// @ResponseBody
	// public PageInfo<Map<String, Object>> findPageInfo(String test) {
	// HashMap<String, Object> map = Maps.newHashMap();
	// // map.put("test", 1);
	// map.put("tablename", "pt_log");
	// // PageHelper.startPage(2, 4);
	// // SqlUtil.getLocalPage().setIsDyTableName(true);
	// List<Map<String, Object>> findPageInfo = ptLogService.findPageInfo(map);
	// return new PageInfo<>(findPageInfo);
	// }

	@RequestMapping("findPageInfo1")
	@ResponseBody
	public Page<Map<String, Object>> findPageInfo1(Page<Map<String, Object>> page, String test) {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("tableName", "pt");
		map.put("aa", "0100");
		List<Map<String, Object>> records = ptLogService.findPageInfo1(page, map);
		page.setRecords(records);

		// sqlManager.setTableNamePrefix("test");
		// SQLScript script = sqlManager.getScript(PtLog.class, Constants.INSERT);
		// String sql = script.getSql();

		return page;
	}

	@RequestMapping("findPageInfo2")
	@ResponseBody
	public Page<Map<String, Object>> findPageInfo2(Page<Map<String, Object>> page, String test) {
		DyTableModel dyTableModel = new DyTableModel();
		dyTableModel.tableName = "pt";
		List<Map<String, Object>> records = ptLogMapper.findPageInfo2(page, dyTableModel);
		page.setRecords(records);
		return page;
	}

	@RequestMapping("findLists")
	@ResponseBody
	public List<Map<String, Object>> findLists() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("tableName", "pt");
		map.put("aa", "0000");
		List<Map<String, Object>> findLists = ptLogMapper.findLists(map);
		return findLists;
	}

	@RequestMapping("test1")
	@ResponseBody
	public List<Map<String, Object>> test1() {
		List<Map<String, Object>> selectList = sqlMapper.selectList("select * from pt_log");
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("aa", "0000");
		selectList = sqlMapper.selectList("select * from pt_log where sys_flag=#{aa}", map);
		return selectList;
	}

	@RequestMapping("inserttest")
	@ResponseBody
	public int insertTest() {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("id", "223");
		map.put("description", "测试试试");
		ptLogMapper.insertTest(map);
		return 0;
	}

	// 测试
	@RequestMapping("inserttest1")
	@ResponseBody
	public int insertTest1() {
		HashMap<String, Object> map = Maps.newHashMap();
		ptLogService.saveLogTest(map);
		return 0;
	}
}
