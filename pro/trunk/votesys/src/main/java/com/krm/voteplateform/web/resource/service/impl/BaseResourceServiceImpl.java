package com.krm.voteplateform.web.resource.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.VoteSqlManager;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.web.login.model.LoginUser;
import com.krm.voteplateform.web.pageTemplet.dao.PtVotePageTempletMapper;
import com.krm.voteplateform.web.resource.dao.BaseResourceMapper;
import com.krm.voteplateform.web.resource.model.BasResource;
import com.krm.voteplateform.web.resource.service.BaseResourceService;
import com.krm.voteplateform.web.util.SysUserUtils;
@Service("baseResourceService")
public class BaseResourceServiceImpl implements BaseResourceService {

	@Resource
	private BaseResourceMapper resourceMapper;
	
	@Resource
	private PtVotePageTempletMapper ptVotePageTempletMapper;
	
	@Resource
	private SQLManager sqlManager;
	
	@Resource
	private VoteSqlManager voteSqlManager;

	@Override
	public List<Map<String, Object>> listResource() {
		Map<String, String> map = new HashMap<String, String>();
		String code = SysUserUtils.getCurrentCommissionCode();
		map.put(MyBatisConstans.DYTABLE_KEY, code);
		return resourceMapper.listResource(map);
	}

	@Override
	public Object getObject(String id) {
		Map<String, String> map = new HashMap<String, String>();
		String code = SysUserUtils.getCurrentCommissionCode();
		map.put("id", id);
		map.put("code", code);
		return resourceMapper.getObject(map);
		
	}

	@Override
	public boolean saveObject(BasResource baseResource) {
		Map<String, String> map = new HashMap<String, String>();
		String code = SysUserUtils.getCurrentCommissionCode();
		int issuccess = resourceMapper.update(baseResource,code);
		if (issuccess>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<BasResource> getTreeList() {
		Map<String, String> map = new HashMap<String, String>();
		String tableName = SysUserUtils.getCurrentCommissionCode();
		map.put("tableName", tableName);
		List<BasResource> lst = resourceMapper.getAllResources(map);
		return lst;
	}

	@Override
	public List<Map<String, Object>> loadResourceById(String cgId) {
		Map<String, String> map =new HashMap<String, String>();
		String tableName = SysUserUtils.getCurrentCommissionCode();
		map.put("tableName", tableName);
		map.put("id", cgId);
		List<Map<String, Object>> list =resourceMapper.getResourceById(map);
		Map<String, Object> map1=list.get(0);
		String tempId=(String) map1.get("tempId");
		if(tempId!=null){
			Map<String, Object> nameMap=ptVotePageTempletMapper.findPageTempletById(tempId);
			if (nameMap!=null) {
				String name=(String) nameMap.get("name");
				map1.put("tempName", name);
			}else {
				map1.put("tempName", null);
			}
		}else {
			System.out.println("没有指定页面模板！");
		}
		return list;
		
	}

	@Override
	public List<Map<String, Object>> findMode() {
		Map<String, String> map =new HashMap<String, String>();
		map.put("type", "01");
		List<Map<String, Object>> list = resourceMapper.findAllMode(map);
		return list;
	}

	@Override
	public boolean updateMenu(BasResource basResource) {
		String tempId=basResource.getTempId();
		if ("0".equalsIgnoreCase(tempId)) {
			basResource.setTempId(null);
			basResource.setTempName(null);
		}else{
			Map<String, Object> tempMap=ptVotePageTempletMapper.findPageTempletById(tempId);
			String tempName=(String) tempMap.get("fileName");
			basResource.setTempName(tempName);
		}
		Map<String, Object> map =new HashMap<String, Object>();
		String tableName =SysUserUtils.getCurrentCommissionCode();
		map.put("tableName", tableName);
		map.put("b", basResource);
		int update = resourceMapper.update2(map);
		//int update =voteSqlManager.updateTemplateById(tableName, basResource);
		//sqlManager.setTableNamePrefix(tableName);
		//int update =sqlManager.updateById(basResource);
		if (update>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public  void  recursion(List<Map<String, Object>> list,BaseResourceMapper resourceMapper){
		/*for(Map<String, Object> mapMenu:list){
			Map<String, Object> mapfind =new HashMap<String, Object>();
			mapfind.put("code", SysUserUtils.getCurrentCommissionCode());
			String levelString=(String) mapMenu.get("parentIds");
			if (levelString==null) {
				levelString=(String) mapMenu.get("parentids");
			}
			String commText=(String) mapMenu.get("commText");
			if (commText==null) {
				mapMenu.put("commText",mapMenu.get("commtext"));
			}
			int level=levelString.toString().split(",").length;
			String id=mapMenu.get("id").toString();
			mapMenu.put("level", level);
			mapfind.put("id", id);
			int count=resourceMapper.hasChilds(mapfind);//查询此id孩子的数目
			boolean hasChild;
			List<Map<String, Object>> listChilds=new ArrayList<Map<String,Object>>();
			for(Map<String, Object> mapping:list){
				if (mapMenu.get("id").toString().equalsIgnoreCase()) {//存在孩子
					hasChild=true;
					//List<Map<String, Object>> listChilds=resourceMapper.listChilds(mapfind);//列出改id下的所有孩子
					listChilds.add(mapping);
					//recursion(listChilds,resourceMapper);
					mapMenu.put("childs", listChilds);
					mapMenu.put("hasChild", hasChild);
				}else {//是叶子节点，寻找他的父亲
					hasChild=false;
					mapMenu.put("hasChild", hasChild);
					//String parentid=(String) mapMenu.get("parentid");//找到他的上级父亲
					//list.remove(mapMenu);
				}
			}
			if (mapMenu.get("hasChild").toString().equalsIgnoreCase("true")) {
				List<Map<String, Object>> listChilds1 =(List<Map<String, Object>>) mapMenu.get("childs");
				recursion(listChilds1,resourceMapper);
			}else {
				continue;
			}
		}*/
	}

	public Map<String, Object> findParent(Map<String, Object> mapMenu,BaseResourceMapper resourceMapper){
		return mapMenu;
		/*List<Map<String, Object>> listMap=new ArrayList<Map<String,Object>>();
			String levelString=(String) mapMenu.get("parentIds");
			if (levelString==null) {
				levelString=(String) mapMenu.get("parentids");
			}
			String commText=(String) mapMenu.get("commText");
			if (commText==null) {
				mapMenu.put("commText",mapMenu.get("commtext"));
			}
			int level=levelString.toString().split(",").length;
			String id=mapMenu.get("id").toString();
			mapMenu.put("level", level);
			mapMenu.put("hasChild", false);//全是叶子
			
			Map<String, Object> map=new HashMap<String, Object>();
			String parentid=(String) mapMenu.get("parentid");
			String code=SysUserUtils.getCurrentCommissionCode();
			map.put("parentid", parentid);
			map.put("code", code);
			
			List<Map<String, Object>> listChilds=new ArrayList<Map<String,Object>>();
			if (level==1) {
				continue;
			}else{
				Map<String, Object> mapParent=resourceMapper.getParenTMenu(map);//父菜单
				String levelString1=(String) mapParent.get("parentIds");
				if (levelString1==null) {
					levelString1=(String) mapParent.get("parentids");
				}
				int level2=levelString1.toString().split(",").length;
				mapParent.put("level", level2);
				mapParent.put("hasChild", true);
				List<Map<String, Object>> childs=new ArrayList<Map<String,Object>>();
				for(Map<String, Object> mapFind:listMap){
					if (mapParent.get("id").toString().equalsIgnoreCase(mapFind.get("parentid").toString())) {
						childs.add(mapFind);
					}
				}
				mapParent.put("childs", childs);
				if (level2>1) {
					listChilds.add(mapResult);
					mapParent.put("childs", listChilds);
					mapParent.put("hasChild", true);
				}else {
					/findParent(mapParent,listChilds);
				}
			}
			return mapParent;*/
		
	}
	
	public void findParent(Map<String, Object> mapMenu){
		
	}
	
	@Override
	public List<Map<String, Object>> findUserResourceByUserId(HttpServletRequest request,LoginUser loginUser) {
		Map<String, Object> map =new HashMap<String, Object>();
		String tableName =SysUserUtils.getCurrentCommissionCode();
		map.put("tableName", tableName);
		map.put("puid", loginUser.getId());
		Map<String, Object> oneUserInfo = SysUserUtils.getOneUserInfo(request);
		List<Map<String, Object>> result=new ArrayList<Map<String,Object>>();
		if(oneUserInfo != null){
			String roleCategory = (String) oneUserInfo.get("roleCategory");
			String parentid=null;
			if ("02".equalsIgnoreCase(roleCategory)) {
				map.put("type", "1");
				parentid=resourceMapper.getParentidByType(map).get("id").toString();
				map.put("parentid",parentid);
			}else if("01".equalsIgnoreCase(roleCategory)){
				map.put("type", "0");
				parentid=resourceMapper.getParentidByType(map).get("id").toString();
				map.put("parentid",parentid);
			}
			List<Map<String, Object>> list=resourceMapper.findUserResourceByUserId(map);
			
			if ("02".equalsIgnoreCase(roleCategory)) {
				result = createTableTree(list,parentid);
			}else if("01".equalsIgnoreCase(roleCategory)){
				result = createTableTree(list,parentid);
			}
		}
	
		return result;
	}
	
	public List<Map<String, Object>> createTableTree(List<Map<String, Object>> reousrceList,String parent) {
			List<Map<String, Object>> ja = new ArrayList<Map<String,Object>>();
			int nchildnum = 0; 
			for(int i=0;i<reousrceList.size();i++)
			{	
				Map<String, Object> basResource = reousrceList.get(i);
				String parentid = basResource.get("parentid").toString();
				String id = basResource.get("id").toString();
				if(parent.equals(parentid))
				{
//					Map<String, Object> jo = new HashMap<String, Object>();
//					jo.put("commText", basResource.getCommText());
					int level=basResource.get("parentIds").toString().split(",").length;
					basResource.put("level",level );
//					nTableIndex++;
					List<Map<String, Object>> ja1 = createTableTree(reousrceList,id);
					if(ja1.size()>0)
					{
						basResource.put("hasChild", true);
						basResource.put("childs", ja1);
					}else{
						basResource.put("hasChild", false);
					}
					ja.add(basResource);
				}
			}
			return ja;

		/*Map<String, Object> map =new HashMap<String, Object>();
		String tableName =SysUserUtils.getCurrentCommissionCode();
		map.put("tableName", tableName);
		map.put("puid", loginUser.getId());
		List<Map<String, Object>> list=resourceMapper.findUserResourceByUserId(map);
		BaseResourceServiceImpl baseResourceServiceImpl=new BaseResourceServiceImpl();
		baseResourceServiceImpl.recursion(list,resourceMapper);
		List<Map<String, Object>> listResult=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> mapResult:list){
			int level=mapResult.get("parentIds").toString().split(",").length;
			if (level==1) {
				mapResult.put("level", level);
				mapResult.put("hasChild", false);
				listResult.add(mapResult);
				continue;
			}else{
				Map<String, Object> mapChilds=new HashMap<String, Object>();
				List<Map<String, Object>> listChilds=new ArrayList<Map<String,Object>>();
				Map<String, Object> mapChild=new HashMap<String, Object>();
				String parentid=(String) mapResult.get("parentid");
				String code=SysUserUtils.getCurrentCommissionCode();
				map.put("parentid", parentid);
				map.put("code", code);
				Map<String, Object> mapParent=resourceMapper.getParenTMenu(mapChild);
				mapParent.put("level", level);
				mapParent.put("hasChild", true);
				
				for(Map<String, Object> mapping:list){
					if (mapParent.get("id").toString().equalsIgnoreCase(mapping.get("parentid").toString())) {
				 		listChilds.add(mapping);
					}else {
						continue;
					}
				}
				mapParent.put("childs", listChilds);
				int levelParent=mapParent.get("parentIds").toString().split(",").length;
				if (levelParent==1) {
					listResult.add(mapParent);
				}else{
					findParent(mapParent);
				}
			}
		}
		return listResult;*/
	}



}
