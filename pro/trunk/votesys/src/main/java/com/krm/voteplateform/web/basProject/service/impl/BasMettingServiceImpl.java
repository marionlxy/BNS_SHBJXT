package com.krm.voteplateform.web.basProject.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.beetl.sql.core.db.KeyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.BeanMapUtils;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.web.basProject.dao.BasMettingMapper;
import com.krm.voteplateform.web.basProject.model.BasMetting;
import com.krm.voteplateform.web.basProject.service.BasMettingService;
import com.krm.voteplateform.web.commission.model.PtCommission;
import com.krm.voteplateform.web.commission.service.PtCommissionService;
import com.krm.voteplateform.web.constants.SysContants;
import com.krm.voteplateform.web.menuwinconf.service.BasMessageService;
import com.krm.voteplateform.web.mettingmem.model.BasMettingMem;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("basMettingService")
public class BasMettingServiceImpl implements BasMettingService {

	private Logger logger = LoggerFactory.getLogger(BasMettingServiceImpl.class);
	@Resource
	private BasMettingMapper basMettingMapper;

	@Resource
	private SQLManager sqlManager;

	@Resource
	private VoteSqlManager voteSqlManager;

	@Resource
	private PtCommissionService ptCommissionService;
	// 消息提示表
	@Resource
	private BasMessageService basMessageService;

	@Override
	public Map<String, Object> findCurrentMeeting() {
		HashMap<String, String> map = new HashMap<String, String>();
		Map<String, Object> result = new HashMap<String, Object>();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> findCurrentMeeting = basMettingMapper.findCurrentMeeting(map);
		if (!findCurrentMeeting.isEmpty()) {
			result = findCurrentMeeting.get(0);
		}
		return result;
	}

	// 查询出所有完成会议信息
	@Override
	public List<Map<String, Object>> findCompletMetting(BasMetting basMetting) {
		logger.info("开始执行查询出所有完成会议信息");
		Map<String, Object> map = Maps.newHashMap();
		Map<String, Object> transBean2Map = BeanMapUtils.transBean2Map(basMetting);
		map.putAll(transBean2Map);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		logger.info("结束查询出所有完成会议信息");
		return basMettingMapper.findCompletMetting(map);
	}

	@Override
	public Map<String, Object> findPrepatOrMeeting() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> findPrepatOrMeeting = basMettingMapper.findPrepatOrMeeting(map);
		return findPrepatOrMeeting.size() > 0 ? findPrepatOrMeeting.get(0) : null;
	}

	// 指定为当前会议
	@Override
	public Boolean updateToCurrent(BasMetting basMetting) {
		logger.info("开始执行指定为当前会议");
		Boolean flag = false;
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basMetting.setUpdateTime(nowTimestamp);
		basMetting.setStateId("10000101");// 会议状态：10000100:预备会议，10000101：当前会议，10000102：完成会议
		basMetting.setStateName("当前会议");
		basMetting.setDelFlag("0");
		basMetting.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
		try {
			sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());
			int insert = sqlManager.updateTemplateById(basMetting);
			flag = insert > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 指定为预备会议
	@Override
	public Boolean updateToPreparatory(BasMetting basMetting) {
		logger.info("开始执行指定为预备会议");
		Boolean flag = false;
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basMetting.setUpdateTime(nowTimestamp);
		basMetting.setStateId("10000100");// 会议状态：10000100:预备会议，10000101：当前会议，10000102：完成会议
		basMetting.setStateName("预备会议");
		basMetting.setDelFlag("0");
		basMetting.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
		try {
			sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());
			int insert = sqlManager.updateTemplateById(basMetting);
			flag = insert > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Boolean updateToComplete(BasMetting basMetting) {
		logger.info("开始执行指定为完成会议");
		Boolean flag = false;
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basMetting.setUpdateTime(nowTimestamp);
		basMetting.setStateId("10000102");// 会议状态：10000100:预备会议，10000101：当前会议，10000102：完成会议
		basMetting.setStateName("完成会议");
		basMetting.setDelFlag("0");
		basMetting.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
		try {
			sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());
			int insert = sqlManager.updateTemplateById(basMetting);
			flag = insert > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public BasMetting findeMettingDetail(Map<String, Object> param) {
		logger.info("获取当前委员的Code" + SysUserUtils.getCurrentCommissionCode());
		param.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		logger.info("结束详情信息反显数据{}" + param);
		return basMettingMapper.selectBasMettingDetil(param);
	}

	@Override
	public Map<String, Object> findOrComplement(String projectId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("projectId", projectId);
		List<Map<String, Object>> findOrComplement = basMettingMapper.findOrComplement(map);
		return findOrComplement.size() > 0 ? findOrComplement.get(0) : null;
	}

	@Override
	public BasMetting viewCompleMetting(Map<String, Object> param) {
		logger.info("获取当前委员的Code" + SysUserUtils.getCurrentCommissionCode());
		param.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		logger.info("结束息反完成会议详情信显数据{}" + param);
		return basMettingMapper.selectCompleMetting(param);
	}

	@Override
	public List<Map<String, Object>> getConfirmList(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMettingMapper.getConfirmList(params);
	}

	@Override
	public boolean empower(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		int count = voteSqlManager.updateTemplateById(params, BasMettingMem.class);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int deleteCommitteeMember(Map<String, Object> params) {
		return voteSqlManager.deleteById(SysUserUtils.getCurrentCommissionCode(), BasMettingMem.class,
				params.get("pkid"));
	}

	@Override
	public List<Map<String, Object>> getAbsentDeptList(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMettingMapper.getAbsentDeptList(params);
	}

	@Override
	public List<Map<String, Object>> countVote(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMettingMapper.countVote(params);
	}

	@Override
	public List<Map<String, Object>> selectVoted(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMettingMapper.selectVoted(params);
	}

	@Override
	public Map<String, Object> selectFomula(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMettingMapper.selectFomula(params);
	}

	@Override
	public Map<String, Object> countOneMind(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMettingMapper.countOneMind(params);
	}

	@Override
	public Map<String, Object> specReflectMetting() {
		// 初始化结果Map
		Map<String, Object> resultMap = Maps.newHashMap();
		// 1.会议编码编辑
		Map<String, Object> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		Map<String, Object> findSpecMetting = basMettingMapper.findSpecMetting(map);
		
		String fixCode= null;
		String lasCode=null;
		String rusulCode=null;
		if(findSpecMetting!=null&&!findSpecMetting.isEmpty()&&!StringUtils.isEmpty(rusulCode)){
			rusulCode= (String) findSpecMetting.get("specMettingCode");
			String[] aa = rusulCode.split("-");
			 fixCode = aa[0];// 年份
			 lasCode = aa[1];// 顺序号
		}
		String year = DateUtils.getYear();// 当前年
		String rCode = year + "-";
		if (year.equals(fixCode)) { // 进行补位处理
			rCode += StringUtils.leftPad(String.valueOf((Integer.parseInt(lasCode) + 1)), 3, "0");
		} else {
			rCode += "001";
		}
		resultMap.put(SysContants.SPEC_METTING_CODE_KEY, rCode);
		// 2.编辑会议名称，即将当前委员会名称传入前台进行处理
		PtCommission curPtCommission = SysUserUtils.getCurPtCommission();
		resultMap.put(SysContants.SPEC_METTING_TITLE_KEY, curPtCommission == null ? "" : curPtCommission.getSysTitle());
		// 3.编辑审批行
		Map<String, Object> findOneSysConfByType = basMettingMapper.findOneSysConfByType("1");// 取得系统中的银行名称
		resultMap.put(SysContants.SPEC_APPROVEORG_KEY, findOneSysConfByType.get("sysVal").toString());
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> selectRecentlyMettingList() {
		HashMap<String, String> map = Maps.newHashMap();
		// 获取tableName的前置
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		// 查询方法
		List<Map<String, Object>> list = basMettingMapper.selectRecentlyMettingList(map);
		return list;
	}

	@Override
	public List<Map<String, Object>> selectVotedDetails(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMettingMapper.selectVotedDetails(params);
	}

	// 添加创建预备会议信息
	@Override
	public Boolean saveMenu4MettingList(BasMetting basMetting) {
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		KeyHolder holder = new KeyHolder();
		basMetting.setCreateTime(nowTimestamp);
		basMetting.setCreateBy(SysUserUtils.getSessionLoginUser().getId());
		basMetting.setCreateName(SysUserUtils.getSessionLoginUser().getUserName());
		basMetting.setDelFlag("0");
		basMetting.setStateId("10000100");
		basMetting.setStateName("预备会议");
		int insert = voteSqlManager.insert(SysUserUtils.getCurrentCommissionCode(), BasMetting.class, basMetting,
				holder);
		return insert > 0 ? true : false;
	}

	@Override
	public BasMetting findParamsMetting(String mettingId) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("id", mettingId);
		return basMettingMapper.findParamsMetting(map);
	}

}
