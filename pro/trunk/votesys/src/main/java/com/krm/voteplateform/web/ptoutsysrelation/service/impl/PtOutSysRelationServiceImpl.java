package com.krm.voteplateform.web.ptoutsysrelation.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.sql.core.SQLManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.web.commission.dao.PtCommissionMapper;
import com.krm.voteplateform.web.ptoutsysrelation.dao.PtOutSysRelationMapper;
import com.krm.voteplateform.web.ptoutsysrelation.model.PtOutSysRelation;
import com.krm.voteplateform.web.ptoutsysrelation.service.PtOutSysRelationService;
import com.krm.voteplateform.web.synproject.contants.SynProjectContants;

@Service("ptOutSysRelationService")
public class PtOutSysRelationServiceImpl implements PtOutSysRelationService {

	@Resource
	private PtOutSysRelationMapper ptOutSysRelationMapper;

	@Resource
	private PtCommissionMapper ptCommissionMapper;

	@Resource
	private SQLManager sqlManager;

	@Override
	public List<Map<String, Object>> getRelationList() {
		List<Map<String, Object>> list = ptOutSysRelationMapper.getRelationList();
		return list;
	}

	@Override
	public boolean saveOutCommission(PtOutSysRelation ptOutSysRelation) {
		int insert = sqlManager.insert(ptOutSysRelation);
		if (insert > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID", ptOutSysRelation.getId());
			map.put("CODE", ptOutSysRelation.getCode());
			map.put("NOTIFY_ADDR", ptOutSysRelation.getNotifyAddr());
			map.put("RELATION_CODE", ptOutSysRelation.getRelationCode());
			map.put("RELATION_NAME", ptOutSysRelation.getRelationName());
			map.put("UPDATE_TIME", ptOutSysRelation.getUpdateTime());

			SpringContextHolder.putRelationOtherSysMap(
					ptOutSysRelation.getCode() + "_" + ptOutSysRelation.getRelationCode(), map);
			File file = null;
			for (int j = 0; j < SynProjectContants.OTHERSYS_FILE_ARRAY.length; j++) {
				file = new File(SynProjectContants.OTHERSYS_FILE_ARRAY[j] + File.separator + ptOutSysRelation.getCode()
						+ File.separator + ptOutSysRelation.getRelationCode());
				if (!file.exists()) {
					file.mkdirs();
					file.setWritable(true, false);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> getSysCommissionList() {
		List<Map<String, Object>> list = ptCommissionMapper.selectAll();
		return list;
	}

	@Override
	public Map<String, Object> getRelationMap(String id) {
		Map<String, Object> map = ptOutSysRelationMapper.getRelationMap(id);
		return map;
	}

	@Override
	public boolean saveUpdateOutCommission(PtOutSysRelation ptOutSysRelation) {
		int update = sqlManager.updateTemplateById(ptOutSysRelation);
		if (update > 0) {
			Map<String, Map<String, Object>> relationOtherSysMap = SpringContextHolder.getRelationOtherSysMap();
			Map<String, Object> map = relationOtherSysMap.get(ptOutSysRelation.getCode() + "_"
					+ ptOutSysRelation.getRelationCode());
			map.put("NOTIFY_ADDR", ptOutSysRelation.getNotifyAddr());
			map.put("UPDATE_TIME", ptOutSysRelation.getUpdateTime());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean selectVotesCode(Map<String, Object> paramMap) {
		Integer row = ptOutSysRelationMapper.selectVotesCode(paramMap);
		return row != null && row > 0 ? true : false;
	}
}
