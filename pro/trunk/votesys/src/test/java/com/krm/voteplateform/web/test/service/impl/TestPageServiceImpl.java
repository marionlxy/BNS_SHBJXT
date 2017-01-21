package com.krm.voteplateform.web.test.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.mybatis.plugins.pagination.Pagination;
import com.krm.voteplateform.web.bascode.model.BasCode;
import com.krm.voteplateform.web.test.dao.TestMapper;
import com.krm.voteplateform.web.test.service.TestPageService;

@Service("testPageService")
public class TestPageServiceImpl implements TestPageService {

	@Resource
	private TestMapper testMapper;

	@Autowired
	private VoteSqlManager voteSqlManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.voteplateform.web.test.service.impl.TestPageService#findTestPage(com.baomidou.
	 * mybatisplus.plugins.Page, java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> findTestPage(Pagination<Map<String, Object>> page, Map<String, Object> otherMap) {
		return testMapper.findTestPage(page, otherMap);
	}

	@Override
	public Object updateTest() {
		testInsert1();
		return Result.SUCCESS;
	}

	private void testInsert() {
		BasCode bc = new BasCode();
		bc.setName("aaa");
		bc.setCreateTime(new Date());
		bc.setUpdateTime(new Date());
		voteSqlManager.insert("", bc);
	}

	private void testInsert1() {
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put(MyBatisConstans.DYTABLE_KEY, "BBBB");
		paramMap.put("name", "cccc");
		voteSqlManager.insert(paramMap, BasCode.class);
	}

	@Override
	public int insertTemp1() {
		Map<String, String> param = Maps.newHashMap();
		param.put(MyBatisConstans.DYTABLE_KEY, "TEMP");
		param.put("cCode", "1111");
		return testMapper.insertTemp1(param);
	}

}
