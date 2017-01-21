package com.krm.voteplateform.web.sys.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.web.sys.dao.SysDicExpMapper;
import com.krm.voteplateform.web.sys.model.BasDict;
import com.krm.voteplateform.web.sys.model.BasExDetDic;
import com.krm.voteplateform.web.sys.model.BasExpandGroup;
import com.krm.voteplateform.web.sys.service.SysDicExpService;
import com.krm.voteplateform.web.util.SysUserUtils;


@Service("sysDicExpService")
public class SysDicExpServiceImpl implements SysDicExpService{
	Logger logger = LoggerFactory.getLogger(BasExpandGroup.class);
	
	@Resource
	private SysDicExpMapper sysDicExpMapper;
	@Resource
	private SQLManager sqlManager;
	@Resource
	private VoteSqlManager voteSqlManager;

	@Override
	public List<Map<String, Object>> findDicExpList() {
		Map<String, String> map = Maps.newHashMap();
		//map.put("category", category);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return sysDicExpMapper.findDicExpList(map);
	}

	@Override
	public List<Map<String, Object>> findDicExpDetial(String id) {
		Map<String, String> map = Maps.newHashMap();
		map.put("id", id);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return sysDicExpMapper.findDicExpDetial(map);
	}

	@Override
	public Boolean saveExpGroup(BasExpandGroup basExpandGroup) {
		Map<String, String> map = Maps.newHashMap();
		Boolean flag=false;
		basExpandGroup.setId(UUIDGenerator.getUUID());
		basExpandGroup.setDelFlag("0");
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basExpandGroup.setCreateTime(nowTimestamp);
		String userName = SysUserUtils.getSessionLoginUser().getUserName();
		basExpandGroup.setCreateBy(userName);
		basExpandGroup.setUpdateTime(nowTimestamp);
		basExpandGroup.setUpdateBy(userName);
		try {
			logger.info("获取编码，拼成tabled的前缀！！！！！");
			sqlManager.setTableNamePrefix( SysUserUtils.getCurrentCommissionCode());
			int insert = sqlManager.insert(BasExpandGroup.class, basExpandGroup);
			logger.info("插入分组{}",insert>0?"成功":"失败");
			if(insert>0){
			String groupId = basExpandGroup.getId();
			map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());	
			List<Map<String,Object>> list = sysDicExpMapper.selectBasDict(map);
			List<BasExDetDic> ll = Lists.newArrayList();
			BasExDetDic be = null;
			for(int i=0;i<list.size();i++){
				be = new BasExDetDic();
				Map<String, Object> tempMap = list.get(i);
				be.setId(UUIDGenerator.getUUID());
				be.setGroupId(groupId);
				
				if(!ObjectUtils.isEmpty(tempMap.get("dicId"))){
					be.setDicId(tempMap.get("dicId").toString());
					}
				if(!ObjectUtils.isEmpty(tempMap.get("enName"))){
					
					be.setEnName(tempMap.get("enName").toString());
				}
				
				if(!ObjectUtils.isEmpty(tempMap.get("cnName"))){
					
					be.setCnName(tempMap.get("cnName").toString());
				}
				
				if(!ObjectUtils.isEmpty(tempMap.get("dataType"))){
					be.setDataType(tempMap.get("dataType").toString());
				}
				
				if(!ObjectUtils.isEmpty(tempMap.get("dataLength"))){
					be.setDataLength(new java.math.BigDecimal(tempMap.get("dataLength").toString()).intValue());
				}
				
				if(!ObjectUtils.isEmpty(tempMap.get("precision"))){
				be.setPrecision(new java.math.BigDecimal(tempMap.get("precision").toString()).intValue());
				}
				if(!ObjectUtils.isEmpty(tempMap.get("mapCnName"))){
					be.setMapCnName(tempMap.get("mapCnName").toString());
				}
				
				if(!ObjectUtils.isEmpty(tempMap.get("mapPrecision"))){
				be.setMapPrecision(new java.math.BigDecimal(tempMap.get("mapPrecision").toString()).intValue());
				}
				if(!ObjectUtils.isEmpty(tempMap.get("nullFlag"))){	
				be.setNullFlag(new java.math.BigDecimal(tempMap.get("nullFlag").toString()).intValue());
				}	
				if(!ObjectUtils.isEmpty(tempMap.get("plateFlag"))){
				be.setPlateFlag(new java.math.BigDecimal(tempMap.get("plateFlag").toString()).intValue());
				}
				if(!ObjectUtils.isEmpty(tempMap.get("state"))){
				be.setState(new java.math.BigDecimal(tempMap.get("state").toString()).intValue());
				}
				if(!ObjectUtils.isEmpty(tempMap.get("sort"))){
				be.setSort(new java.math.BigDecimal(tempMap.get("sort").toString()).intValue());
				}
				if(!ObjectUtils.isEmpty(tempMap.get("delFlag"))){
					
					be.setDelFlag(tempMap.get("delFlag").toString());
				}
				if(!ObjectUtils.isEmpty(tempMap.get("createBy"))){
					
				be.setCreateBy(tempMap.get("createBy").toString());
				}
				be.setCreateTime(nowTimestamp);
				be.setUpdateTime(nowTimestamp);
				if(!ObjectUtils.isEmpty(tempMap.get("updateBy"))){
					be.setUpdateBy(tempMap.get("updateBy").toString());
				}
				ll.add(be);
			}
			sqlManager.setTableNamePrefix( SysUserUtils.getCurrentCommissionCode());
			int[] resultList = sqlManager.insertBatch(BasExDetDic.class, ll);
				flag = resultList.length>0?true:false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteExpGroup(BasExpandGroup basExpandGroup) {
		boolean flag=false;
		sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());	
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basExpandGroup.setState(1);
		basExpandGroup.setDelFlag("1");
		basExpandGroup.setUpdateTime(nowTimestamp);
		basExpandGroup.setUpdateBy(SysUserUtils.getSessionLoginUser().getUserName());
		try{
			int insert = sqlManager.updateTemplateById(basExpandGroup);
			flag=insert>0?true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}
			return flag;
	}

	@Override
	public BasExpandGroup updateExpGroup(String id) {
		Map<String, String> map = Maps.newHashMap();
		logger.info("code转成map类型");
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("id", id);
		return sysDicExpMapper.updateExpGroup(map);
	}

	@Override
	public Boolean saveUpExpGroup(BasExpandGroup basExpandGroup) {
		Boolean flag=false;
		basExpandGroup.setDelFlag("0");
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basExpandGroup.setUpdateTime(nowTimestamp);
		basExpandGroup.setUpdateBy(SysUserUtils.getSessionLoginUser().getUserName());
		try {
			sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());
			int insert = sqlManager.updateTemplateById(basExpandGroup);
			flag=insert>0?true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public BasExDetDic findDicGroupFild(String gropId,String expDicId) {
		Map<String, String> map = Maps.newHashMap();
		logger.info("code转成map类型");
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("gropId", gropId);
		map.put("id", expDicId);
		logger.info("code转成map类型");
		 return  sysDicExpMapper.findDicGroupFild(map);
		
	}

	@Override
	public Boolean editDicGroupFild(String groupId, String mapCnName, String id) {
		Boolean flag = false;
		Map<String, String> map = Maps.newHashMap();
		logger.info("code转成map类型");
		map.put("gropId", groupId);
		map.put("id", id);
		map.put("mapCnName", mapCnName);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		try {
			flag = sysDicExpMapper.editDicGroupFild(map);
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("结束修改映射名称{}" + flag);
		return flag;
	}

	@Override
	public List<Map<String, Object>> findSelectDicExpList() {
		Map<String, String> map = Maps.newHashMap();
		//map.put("category", category);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("state", "0");
		map.put("delFlag", "0");
		return sysDicExpMapper.findSelectDicExpList(map);
	}

	@Override
	public List<Map<String, Object>> findExpGroupList(String id) {
		Map<String, String> map = Maps.newHashMap();
		//map.put("category", category);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("group_id", id);
		map.put("state", "0");
		map.put("delFlag", "0");
		return sysDicExpMapper.findExpGroupList(map);
	}

	@Override
	public Boolean saveUpStateExps(String basdicts) {
		Boolean flag=true;
		 JSONArray ja=JSON.parseArray(basdicts);
		 Iterator<Object> it = ja.iterator();
		 List<BasExDetDic> list= new ArrayList<BasExDetDic>();
		 while (it.hasNext()) {
             JSONObject ob = (JSONObject) it.next();
             BasExDetDic basExDetDic = null;
             basExDetDic=new BasExDetDic();
             if(ob.getString("id")!=null){
            	 basExDetDic.setId(ob.getString("id"));
             }
             if(ob.getString("state")!=null){
            	 basExDetDic.setState(ob.getInteger("state"));
             }
             Date date = new Date();
             basExDetDic.setUpdateTime(date);
             basExDetDic.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
             
             if(basExDetDic!=null){
                 list.add(basExDetDic);
             }
             sqlManager.setTableNamePrefix(SysUserUtils.getCurrentCommissionCode());
             sqlManager.updateTemplateById(basExDetDic);
         }
		
		return flag;
		
	}
	
	@Override
	public Map<String, Object> getBasExpandGroupById(String bxgId) {
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("bxgId", bxgId);
		Map<String, Object> map2 =sysDicExpMapper.getBasExpandGroupById(map);
		return map2;
	}

	@Override
	public List<Map<String, Object>> findGroupList(String id) {
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("group_id", id);
		map.put("state", "0");
		map.put("delFlag", "0");
		return sysDicExpMapper.findGroupList(map);
	}

	@Override
	public Boolean deleteByGroupId(String groupId) {
		Boolean flag = true;
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("groupId", groupId);
		flag = sysDicExpMapper.updateByGroupId(map);
		return flag;
	}

	//是否存在相同扩展组名
	@Override
	public Integer isExist(String groupName) {
		
		Map<String, String> map = Maps.newHashMap();
		map.put("groupName", groupName);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return sysDicExpMapper.isExistGroupName(map);
	}

	
	
}


	