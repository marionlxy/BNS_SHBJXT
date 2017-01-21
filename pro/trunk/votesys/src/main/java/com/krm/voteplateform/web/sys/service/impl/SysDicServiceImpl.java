package com.krm.voteplateform.web.sys.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.VoteSqlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.web.sys.dao.SysDicMapper;
import com.krm.voteplateform.web.sys.model.BasDict;
import com.krm.voteplateform.web.sys.service.SysDicService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("sysDicService")
public class SysDicServiceImpl implements SysDicService {
	Logger logger = LoggerFactory.getLogger(BasDict.class);

	@Resource
	private SysDicMapper sysDicMapper;

	@Resource
	private VoteSqlManager voteSqlManager;

	@Override
	public List<Map<String, Object>> findSysList(String category) {
		logger.info("查询编辑项目字典典数据");
		Map<String, String> map = Maps.newHashMap();
		logger.info("code转成map类型");
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("category", category);
		return sysDicMapper.findSysList(map);
	}

	@Override
	public BasDict selectDicFields(String id) {
		Map<String, String> map = Maps.newHashMap();
		map.put("id", id);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return sysDicMapper.selectDicFields(map);
	}

	@Override
	public boolean editDicFields(String id, String mapCnName) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", id);
		map.put("mapCnName", mapCnName);
		logger.info("SysUserUtils.getCurrentCommissionCode()里code值，名为tableName");
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		logger.info("进入逻辑，修改成功返回1，");
		int flag = sysDicMapper.updateFields(map);
		if (flag == 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> findSelectSysList(String resid, String functionCode) {
		logger.info("查询项目字典典下拉数据");
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());

		// 2代表未审项目菜单，3代表已审项目菜单，4代表准备会议，5代表当前会议，6代表完成会议，8代表委员端当前会议，9代表委员端历史会议
		// 2和3都与项目有关，取的是字典表中category为“02”的数据。
		if ("2".equalsIgnoreCase(resid) || "3".equalsIgnoreCase(resid)) {
			map.put("category", "02");
		} else if ("4".equalsIgnoreCase(resid) || "5".equalsIgnoreCase(resid) || "6".equalsIgnoreCase(resid) || "8".equalsIgnoreCase(resid)
				|| "9".equalsIgnoreCase(resid)) {

			// 菜单4下面的窗体内容功能编码是fun1307与项目有关，取的是字典表中category为“02”的数据。
			// 菜单4下面的窗体内容功能编码是fun1408与项目有关，取的是字典表中category为“02”的数据。
			// 窗体内容的功能编码fun1402与项目有关，取的是字典表中category为“02”的数据。
			if ("fun1307".equalsIgnoreCase(functionCode) || "fun1408".equalsIgnoreCase(functionCode)
					|| "fun1402".equalsIgnoreCase(functionCode)) {
				map.put("category", "02");
			}
			// 其余的都与会议字典有关，取的是字典表中category为“01”的数据。
			else {
				map.put("category", "01");
			}
		}
		map.put("state", "0");
		map.put("delFlag", "0");
		return sysDicMapper.findSelectSysList(map);
	}

	@Override
	public Boolean saveUpState(String basdicts) {
		Boolean flag = true;
		JSONArray ja = JSON.parseArray(basdicts);
		Iterator<Object> it = ja.iterator();
		while (it.hasNext()) {
			JSONObject ob = (JSONObject) it.next();
			BasDict basDict = new BasDict();
			if (ob.getString("id") != null) {
				basDict.setId(ob.getString("id"));
			}
			if (ob.getString("state") != null) {
				basDict.setState(ob.getString("state"));
			}
			basDict.setUpdateTime(DateUtils.getNowTimestamp());
			basDict.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
			voteSqlManager.updateTemplateById(SysUserUtils.getCurrentCommissionCode(), basDict);
		}

		return flag;

	}

}
