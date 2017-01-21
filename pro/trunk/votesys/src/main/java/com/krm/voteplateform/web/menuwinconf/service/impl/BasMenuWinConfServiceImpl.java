package com.krm.voteplateform.web.menuwinconf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.Reflect;
import com.krm.voteplateform.common.utils.StringConvert;
import com.krm.voteplateform.web.bascode.dao.BasCodeMapper;
import com.krm.voteplateform.web.bascodegroup.dao.BasCodeGroupMapper;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;
import com.krm.voteplateform.web.menuwinconf.dao.BasMenuWinConfMapper;
import com.krm.voteplateform.web.menuwinconf.model.BasMenuWinConf;
import com.krm.voteplateform.web.menuwinconf.service.BasMenuWinConfService;
import com.krm.voteplateform.web.menwingrouconf.model.BasMenWinGrouConf;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * 操作相关
 */
@Service("basMenuWinConfService")
public class BasMenuWinConfServiceImpl implements BasMenuWinConfService {

	Logger logger = LoggerFactory.getLogger(BasMenuWinConfServiceImpl.class);

	@Autowired
	private BasMenuWinConfMapper basMenuWinConfMapper;
	
	@Resource
	private VoteSqlManager voteSqlManager;
	
	@Resource
	private SQLManager sqlManager;
	
	@Resource
	private BasCodeGroupMapper basCodeGroupMapper;

	@Resource
	private BasCodeMapper basCodeMapper;
	
	@Override
	public Boolean saveBasMenuWinConf(BasMenuWinConf basMenuWinConf,Map<String,Object> pmap) {
		logger.info("开始保存分组业务逻辑");
		Date dat=new Date();
		String userName = SysUserUtils.getSessionLoginUser().getUserName();
		Map<String, String> map = Maps.newHashMap();
		pmap.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> listMenuWin = basMenuWinConfMapper.listAllMenuWin(map);
		int groupId=0;
		  for (Map<String, Object> mapMax : listMenuWin)  
		    {  
			  String key="groupid";
			  String value=(String) mapMax.get(key);
			  if(!StringUtils.isEmpty(value)){
				  int result=Integer.parseInt(value.substring(0, 3));
				  if(result>=groupId){
					  groupId=result+1;
				  }
				  continue;
			  }
		    } 
		String newGroupId=StringConvert.addZeroForNum(groupId+"", 3, true)+"000";
	    basMenuWinConf.setGroupid(newGroupId);
		basMenuWinConf.setDelFlag("0");
		basMenuWinConf.setCreateBy(userName);
		basMenuWinConf.setCreateTime(dat);
		basMenuWinConf.setUpdateBy(userName);
		basMenuWinConf.setUpdateTime(dat);
		//批量修改,0,表示基本项
		if("0".equalsIgnoreCase((String) pmap.get("basicFlag"))){
					 basMenuWinConfMapper.updateBasMenuWinConfList(pmap);
				}
		int insert = voteSqlManager.insert(SysUserUtils.getCurrentCommissionCode(), basMenuWinConf);
		
		logger.info("结束保存分组业务逻辑");
		return insert>0?true:false;
		
	}

	@Override
	public void updateBasMenuWinConf(BasMenuWinConf basMenuWinConf) {
		basMenuWinConfMapper.updateBasMenuWinConf(basMenuWinConf);
	}

	@Override
	public void removeBasMenuWinConf(BasMenuWinConf basMenuWinConf) {
		basMenuWinConfMapper.deleteBasMenuWinConf(basMenuWinConf);
	}

	@Override
	public BasMenuWinConf loadById(String id) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("id", id);
		return basMenuWinConfMapper.findById(map);
	}

	@Override
	public boolean selectName(Map<String, Object> map) {
		//String tableName=SysUserUtils.getCurrentCommissionCode();
		map.put(MyBatisConstans.DYTABLE_KEY,SysUserUtils.getCurrentCommissionCode());
		List<Map<String,Object>> resultMap = basMenuWinConfMapper.findByIdByName(map);
		if(resultMap==null || resultMap.size()<1)
		{
		  return false;  
		} 
		return true;
	}

	
	@Override
	public List<Map<String, Object>> getMenuWinAll(String functionCode) {
		Map<String, String> map = new HashMap<String, String>();
		String code = SysUserUtils.getCurrentCommissionCode();
		map.put(MyBatisConstans.DYTABLE_KEY, code);
		map.put("functionCode", functionCode);
		List<Map<String, Object>> list =basMenuWinConfMapper.listMenuWin(map);
		for(Map<String,Object> map2:list){
			Map<String, String> map3 = new HashMap<String, String>();
			map3.put(MyBatisConstans.DYTABLE_KEY, code);
			String datasource = (String) map2.get("datasource");
			map3.put("datasource", datasource);
			String dataSourceName = basMenuWinConfMapper.getDataSourceNameById(map3);
			map2.put("datasource", dataSourceName);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> findSelectGroupList(String functionCode) {
		logger.info("开始查询分组下拉框");
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("type","0");
		map.put("useFlag","0");
		map.put("delFlag","0");
		map.put("functionCode", functionCode);
		List<Map<String, Object>> findSelectGroupList =basMenuWinConfMapper.findSelectGroupList(map);
		logger.info("结束查询分组下拉框");
		return findSelectGroupList;
	}

	@Override
	public Boolean saveBasMenuGatherConf(BasMenuWinConf basMenuWinConf) {
		logger.info("开始保存采集框业务逻辑");
		Date dat=new Date();
		String userName = SysUserUtils.getSessionLoginUser().getUserName();
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("delFlag", "0");
		String subgroup = StringUtils.substring(basMenuWinConf.getGroupid(), 0,3);
		String searchText = new StringBuilder(subgroup).append("%").toString();
		map.put("groupid", searchText);
		List<Map<String, Object>> lstGroup = basMenuWinConfMapper.findSelectGroupList(map);
		int groupId=0;//这是后三位
		  for (Map<String, Object> mapMax : lstGroup)  
		    {  
			  String key="groupid";
			  String value=(String) mapMax.get(key);
			  if(!StringUtils.isEmpty(value)){
				  int result=Integer.parseInt(value.substring(3, 6));
				  if(result>=groupId){
					  groupId=result+1;
				  }
				  continue;
			  }
		    } 
		String newGroupId=subgroup+StringConvert.addZeroForNum(groupId+"", 3, true);
	    basMenuWinConf.setGroupid(newGroupId);
		basMenuWinConf.setDelFlag("0");
		basMenuWinConf.setCreateBy(userName);
		basMenuWinConf.setCreateTime(dat);
		basMenuWinConf.setUpdateBy(userName);
		basMenuWinConf.setUpdateTime(dat);
		int insert = voteSqlManager.insert(SysUserUtils.getCurrentCommissionCode(), basMenuWinConf);
		logger.info("结束保存采集框业务逻辑");
		return insert>0?true:false;
	}
	
	@Override
	public void deleteMById(String id) {
		String code = SysUserUtils.getCurrentCommissionCode();
		BasMenuWinConf basMenuWinConf = null;
		String[] ids = StringUtils.split(id,",");
		for(int i=0;i<ids.length;i++){
			//basMenuWinConf = new BasMenuWinConf();
			//basMenuWinConf.setId(ids[i]);
			//basMenuListConf.setDelFlag("1");
			Map<String, String> map = new HashMap<String, String>();
			map.put(MyBatisConstans.DYTABLE_KEY, code);
			//List<Map<String, Object>> list = basMenuWinConfMapper.findSelectGroupList(map);
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put(MyBatisConstans.DYTABLE_KEY, code);
			map1.put("id", ids[i]);
			basMenuWinConf = basMenuWinConfMapper.findById(map1);
			if(basMenuWinConf != null){
				String type = basMenuWinConf.getType();
				int type1 = Integer.parseInt(type);
				if (type1==0) {
					String groupId = basMenuWinConf.getGroupid();
					String group =groupId.substring(0, 3);
					map.put("group", group+"%");
					basMenuWinConfMapper.deleteGroupId(map);
				}else {
					voteSqlManager.deleteById(code, BasMenuWinConf.class, basMenuWinConf.getId());
				}
			}
		}
		
	}

	@Override
	public Boolean saveBasMenuDetailConf(BasMenuWinConf basMenuWinConf, List<BasMenWinGrouConf> lst,String[] chkArrs) {
		logger.info("开始保存明细项业务逻辑");
		// TODO Auto-generated method stub
		//String uuid1 = UUIDGenerator.getUUID();
		//basMenuWinConf.setId(uuid1);
		basMenuWinConf.setDelFlag("0");
		Date dat1=new Date();
		basMenuWinConf.setCreateTime(dat1);
		String userName = SysUserUtils.getSessionLoginUser().getUserName();
		basMenuWinConf.setCreateBy(userName);
		basMenuWinConf.setUpdateTime(dat1);
		basMenuWinConf.setUpdateBy(userName);
		String currentCommissionCode = SysUserUtils.getCurrentCommissionCode();
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("delFlag", "0");
		String subgroup = StringUtils.substring(basMenuWinConf.getGroupid(), 0,3);
		String searchText = new StringBuilder(subgroup).append("%").toString();
		map.put("groupid", searchText);
		List<Map<String, Object>> lstGroup = basMenuWinConfMapper.findSelectGroupList(map);
		int groupId=0;//这是后三位
		  for (Map<String, Object> mapMax : lstGroup)  
		    {  
			  String key="groupid";
			  String value=(String) mapMax.get(key);
			  if(!StringUtils.isEmpty(value)){
				  int result=Integer.parseInt(value.substring(3, 6));
				  if(result>=groupId){
					  groupId=result+1;
				  }
				  continue;
			  }
		    } 
		String newGroupId=subgroup+StringConvert.addZeroForNum(groupId+"", 3, true);
	    basMenuWinConf.setGroupid(newGroupId);
	    
	    boolean flag=false;
	    String uuid1=null;
		try {
			//KeyHolder holder = new KeyHolder();
			//voteSqlManager.insert(currentCommissionCode, basMenuWinConf, true);
			sqlManager.setTableNamePrefix(currentCommissionCode);
			sqlManager.insert(BasMenuWinConf.class, basMenuWinConf);
			//voteSqlManager.insert(currentCommissionCode, BasMenuWinConf.class, basMenuWinConf, holder);
			uuid1 = basMenuWinConf.getId();
			//uuid1=basMenuWinConf.getId();
			//voteSqlManager.insert(currentCommissionCode, basMenuWinConf);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    List<BasMenWinGrouConf> newlst=new ArrayList<BasMenWinGrouConf>();
		//项目扩展明细表主键(有多个需特殊处理)
		//String[] chkArrs = request.getParameterValues("chk");
	
		for (BasMenWinGrouConf basMenWinGrouConf : lst) {
			//菜单窗体拓展明细分组配置表
			//String uuid2 = UUIDGenerator.getUUID();
			//basMenWinGrouConf.setId(uuid2);
			basMenWinGrouConf.setBmwcId(uuid1);
			basMenWinGrouConf.setDelFlag("0");
			//Date dat1=new Date();
			basMenWinGrouConf.setCreateTime(dat1);
			//String userName = SysUserUtils.getSessionLoginUser().getUserName();
			basMenWinGrouConf.setCreateBy(userName);
			basMenWinGrouConf.setUpdateTime(dat1);
			basMenWinGrouConf.setUpdateBy(userName);
			newlst.add(basMenWinGrouConf);
		}
		if(flag){
			voteSqlManager.insertBatch(currentCommissionCode, BasMenWinGrouConf.class, newlst);
		}
		//basMenWinGrouConf.setBxgId(null);//明细扩展对象的值）拓展明细分组主键
		logger.info("结束保存采集框业务逻辑");
		return flag;
	}
	
	@Override
	public Map<String, Object> findGroupByID(String id) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		Map<String, Object> list = basMenuWinConfMapper.findGroupByID(map);
		return list;
	}

	@Override
	public Boolean saveWindGatherUpdate(BasMenuWinConf basMenuWinConf) {
		
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("delFlag", "0");
		String subgroup = StringUtils.substring(basMenuWinConf.getGroupid(), 0,3);
		String searchText = new StringBuilder(subgroup).append("%").toString();
		map.put("groupid", searchText);
		List<Map<String, Object>> lstGroup = basMenuWinConfMapper.findSelectGroupList(map);
		int groupId=0;//这是后三位
		  for (Map<String, Object> mapMax : lstGroup)  
		    {  
			  String key="groupid";
			  String value=(String) mapMax.get(key);
			  if(!StringUtils.isEmpty(value)){
				  int result=Integer.parseInt(value.substring(3, 6));
				  if(result>=groupId){
					  groupId=result+1;
				  }
				  continue;
			  }
		    } 
		String newGroupId=subgroup+StringConvert.addZeroForNum(groupId+"", 3, true);
		basMenuWinConf.setGroupid(newGroupId);
		//int update = voteSqlManager.updateTemplateById(SysUserUtils.getCurrentCommissionCode(), basMenuWinConf);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map2.put("a", basMenuWinConf);
		int update = basMenuWinConfMapper.updateWinGather(map2);
		if (update>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Map<String, Object> findWindGatherById(String id) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		Map<String, Object> list = basMenuWinConfMapper.findWindGatherById(map);
		return list;
	}

	@Override
	public Boolean saveGroupUpdate(BasMenuWinConf basMenuWinConf,Map<String,Object> pmap) {
		//必须先执行
		if("0".equalsIgnoreCase( basMenuWinConf.getBasicFlag())){
			pmap.put("basicFlag", "1");
			pmap.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
			 basMenuWinConfMapper.updateBasMenuWinConfList(pmap);
		}
		int update = voteSqlManager.updateTemplateById(SysUserUtils.getCurrentCommissionCode(), basMenuWinConf);
		if (update>0) {
			
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Map<String, Object> getWinDetailById(String id) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		Map<String, Object> map2 = basMenuWinConfMapper.getWinDetailById(map);
		return map2;
	}

	@Override
	public List<Map<String, Object>> getBasMenWinGroupConf(String bmwcId) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("bmwcId", bmwcId);
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		List<Map<String, Object>> basMenWinGrouConf =basMenuWinConfMapper.getBasMenWinGroupConf(map);
		return basMenWinGrouConf;
	}

	@Override
	public Boolean saveBasMenuDetailConfUpdate(BasMenuWinConf basMenuWinConf, List<BasMenWinGrouConf> lst,
			String[] chkArrs) {
		
		Date dat1=new Date();
		String userName = SysUserUtils.getSessionLoginUser().getUserName();
		basMenuWinConf.setUpdateTime(dat1);
		basMenuWinConf.setUpdateBy(userName);
		String currentCommissionCode = SysUserUtils.getCurrentCommissionCode();
		Map<String, String> map = Maps.newHashMap();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		if (basMenuWinConf.getGroupid()==null) {
			logger.info("groupid未发生改变，不需做处理");
		}else {
		String subgroup = StringUtils.substring(basMenuWinConf.getGroupid(), 0,3);
		String searchText = new StringBuilder(subgroup).append("%").toString();
		map.put("groupid", searchText);
		List<Map<String, Object>> lstGroup = basMenuWinConfMapper.findSelectGroupList(map);
		int groupId=0;//这是后三位
		  for (Map<String, Object> mapMax : lstGroup)  
		    {  
			  String key="groupid";
			  String value=(String) mapMax.get(key);
			  if(!StringUtils.isEmpty(value)){
				  int result=Integer.parseInt(value.substring(3, 6));
				  if(result>=groupId){
					  groupId=result+1;
				  }
				  continue;
			  }
		    } 
		String newGroupId=subgroup+StringConvert.addZeroForNum(groupId+"", 3, true);
	    basMenuWinConf.setGroupid(newGroupId);
		}
	    boolean flag=false; 
		try {
			voteSqlManager.updateTemplateById(currentCommissionCode, basMenuWinConf);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    List<BasMenWinGrouConf> newlst=new ArrayList<BasMenWinGrouConf>();
		//项目扩展明细表主键(有多个需特殊处理)
		//String[] chkArrs = request.getParameterValues("chk");
	
		for (BasMenWinGrouConf basMenWinGrouConf : lst) {
			//菜单窗体拓展明细分组配置表
			basMenWinGrouConf.setUpdateTime(dat1);
			basMenWinGrouConf.setUpdateBy(userName);
			newlst.add(basMenWinGrouConf);
		}
		if(flag){
			//voteSqlManager.insertBatch(currentCommissionCode, BasMenWinGrouConf.class, newlst);
			//voteSqlManager.updateByIdBatch(currentCommissionCode, newlst);
			for(BasMenWinGrouConf basMenWinGrouConf:newlst){
				voteSqlManager.updateTemplateById(currentCommissionCode, basMenWinGrouConf);
			}
		}
		logger.info("结束保存采集框业务逻辑");
		return flag;
	}

	/**
	 * 获取所有分组及相关采集框信息
	 */
	@Override
	public List<Map<String, Object>> getWinConfList(Map<String, Object> params) {
		if(!params.containsKey("tableName")){
			params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		}
		return basMenuWinConfMapper.getWinConfList(params);
	}
	
	/**
	 * 获取下拉框数据
	 */
	public Map<String, Object>  selectGroupCode(String tableName,  String dataSource) {
		Map<String, Object> result=Maps.newHashMap();
		Map<String, Object> param=Maps.newHashMap();
		param.put(MyBatisConstans.DYTABLE_KEY, tableName.toUpperCase());
		if(StringUtils.isNotEmpty(dataSource)){
			BasCodeGroup baseCode = basCodeGroupMapper.findById(tableName, dataSource);
			String pefPass = baseCode.getPefPass();
			if(StringUtils.isEmpty(pefPass)){
				List<Map<String, Object>> selectCodes = basCodeMapper.selectByCode(tableName,dataSource);
				result.put("selgroups", selectCodes);
				if(logger.isDebugEnabled()){
					logger.info("查询编码字典信息:{}",JSON.toJSONString(selectCodes));
				}
			}else{
				logger.info("执行反射类{}方法",pefPass);
				int lastIndexOf = pefPass.lastIndexOf(".");
				String clsStr=pefPass.substring(0, lastIndexOf);
				String menthodStr=pefPass.substring(lastIndexOf+1);
				List<Map<String, Object>> list = Reflect.on(clsStr).create().call(menthodStr,param)
						.unwrap();
				result.put("selgroups", list);
				if(logger.isDebugEnabled()){
					logger.info("查询编码反射字典信息:{}",JSON.toJSONString(list));
				}
			}
		}
	     return result;
	}

	/**
	 * 获取明细列表
	 */
	@Override
	public List<Map<String, Object>> getExtDetList(Map<String, Object> params) {
		if(!params.containsKey("tableName")){
			params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		}
		return basMenuWinConfMapper.getExtDetList(params);
	}

	/**
	 * 获取模板名称
	 */
	@Override
	public Map<String, Object> getTempaletName(Map<String, Object> params) {
		if(!params.containsKey("tableName")){
			params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		}
		return basMenuWinConfMapper.getTempaletName(params);
	}

	/**
	 * //获取扩展明细数据
	 */
	@Override
	public List<Map<String, Object>> getExtDetDataList(Map<String, Object> params) {
		if(!params.containsKey("tableName")){
			params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		}
		return basMenuWinConfMapper.getExtDetDataList(params);
	}

	@Override
	public List<Map<String, Object>> getOrder(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMenuWinConfMapper.getOrder(params);
	}

	@Override
	public Map<String, Object> getGroupOrder(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMenuWinConfMapper.getGroupOrder(params);
	}

	@Override
	public int updateOrdersByGroup(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMenuWinConfMapper.updateOrdersByGroup(params);
	}

	@Override
	public int changeGroupOrder(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMenuWinConfMapper.changeGroupOrder(params);
	}

	@Override
	public Map<String, Object> selectWinSize(Map<String, Object> params) {
		params.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		return basMenuWinConfMapper.selectWinSize(params);
	}

	public static void main(String[] args) {
		String pefPass="com.hz.ss.menthod";
		int lastIndexOf = pefPass.lastIndexOf(".");
		String clsStr=pefPass.substring(0, lastIndexOf);
		String menthodStr=pefPass.substring(lastIndexOf+1);
		System.out.println(clsStr+"---"+menthodStr);
	}
	
}
