
package com.krm.voteplateform.web.previewgen.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.VoteSqlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.model.BootstrapTable;
import com.krm.voteplateform.common.model.BootstrapTable.BootstrapTableColumn;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.common.utils.Reflect;
import com.krm.voteplateform.common.utils.Reflections;
import com.krm.voteplateform.common.utils.StringConvert;
import com.krm.voteplateform.web.bascode.dao.BasCodeMapper;
import com.krm.voteplateform.web.bascodegroup.dao.BasCodeGroupMapper;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;
import com.krm.voteplateform.web.menulistconf.dao.MenuListConfMapper;
import com.krm.voteplateform.web.previewgen.service.GenFileServcie;
import com.krm.voteplateform.web.resource.dao.BaseResourceMapper;
import com.krm.voteplateform.web.util.SysUserUtils;

/**
 * ClassName:GenPreviewServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年11月21日 下午5:52:11 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Service("genPreviewServcie")
public class GenFileServiceImpl implements GenFileServcie {
	Logger logger = LoggerFactory.getLogger(GenFileServiceImpl.class);
	@Resource
	private BaseResourceMapper resourceMapper;
	
	@Resource
	private VoteSqlManager voteSqlManager;
	
	@Resource
	private MenuListConfMapper menuListConfMapper;
	
	@Resource
	private BasCodeGroupMapper basCodeGroupMapper;

	@Resource
	private BasCodeMapper basCodeMapper;
	
	@Override
	public Map<String, Object> getStaticInfo(String templateFileName, String toFileName) {
		Map<String, Object> map = new HashMap<String, Object>();
		//模板存放路径
		map.put("templateFileName", templateFileName);
		//得到code
		String tableName=SysUserUtils.getCurrentCommissionCode();
		map.put("dbToPath", "/resources/" + tableName.toLowerCase() + "/");//暂时没用
		//模板生成路径
		map.put("toPath", SysUserUtils.getSession().getServletContext().getRealPath("/") + "WEB-INF/views/ptsystems/" + tableName.toLowerCase()+ "/temp/");
		map.put("toFileName", toFileName);
		//检验生成静态文件目录不存在时自动创建目录
		File file = new File((String) map.get("toPath"));
		if(!file.exists()){
			file.mkdirs();
		}
		//删除同名文件
		FileUtils.deleteFile(toFileName);
		logger.info("生成静态文件时的路径参数:" + map);
		return map;
	}


	@Override
	public BootstrapTable getTemplateTableInfo(String tableName,String rsid) {
		Map<String, String> mp = Maps.newHashMap();
		 //String tableName = SysUserUtils.getCurrentCommissionCode();
		mp.put(MyBatisConstans.DYTABLE_KEY, tableName);
		mp.put("resid", rsid);
		//Company company = super.find(companyId);
		List<Map<String, Object>> menuUseFulAll = menuListConfMapper.getMenuUseFulAll(mp);
		
		List<BootstrapTableColumn> columns = Lists.newArrayList();
		 BootstrapTable bootstrapTable = new BootstrapTable();
		 for (Map<String, Object> menumap : menuUseFulAll) {
				BootstrapTableColumn bootstrapTableColumn = bootstrapTable.new BootstrapTableColumn();
				//编码及名称
				String dataType = (String) menumap.get("dataType");
				if("03".equalsIgnoreCase(dataType)){
					String enName=(String) menumap.get("enName");
					String midName=StringConvert.underlineToCamelhump(enName);
					String finallyName=midName+"Name";
					bootstrapTableColumn.setField(finallyName);
					if(logger.isDebugEnabled()){
						logger.info("编码及名称03:"+enName+"===>"+midName+"===>"+finallyName);
					}
				}else if("06".equalsIgnoreCase(dataType)){
					//啥也不做
					continue;
				}else{
					bootstrapTableColumn.setField(StringConvert.underlineToCamelhump((String) menumap.get("enName")));
				}
				bootstrapTableColumn.setTitle((String) menumap.get("mapCnName"));
				bootstrapTableColumn.setSortable(true);
				columns.add(bootstrapTableColumn);
			}
		//封装列
		bootstrapTable.setColumns(columns);
		return bootstrapTable;
	}
	
	
	
	@Override
	public  Set<Map<String, Object>> getBasResource(String tableName) {
		Map<String, String> mp = Maps.newHashMap();
		 mp.put(MyBatisConstans.DYTABLE_KEY, tableName);
		 List<Map<String, Object>> olst=resourceMapper.listMenuResource(mp);
		//只要取一个
		   Set<Map<String, Object>> menuSet=new LinkedHashSet<Map<String, Object>>();
		   for (Iterator<Map<String, Object>> iterator = olst.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();
					menuSet.add(map);
				}
		return menuSet;
	}



	@Override
	public  List<Map<String,Object>>  getSearchTableInfo(String tableName,String rsid) {
		Map<String, String> mp = Maps.newHashMap();
		 //String tableName = SysUserUtils.getCurrentCommissionCode();
		mp.put(MyBatisConstans.DYTABLE_KEY, tableName);
		mp.put("resid", rsid);
		//Company company = super.find(companyId);
		List<Map<String, Object>> menuUseFulAll = menuListConfMapper.getMenuUseFulAll(mp);
		List<Map<String,Object>> lstmap=new ArrayList<Map<String,Object>>();
		 for (Map<String, Object> menumap : menuUseFulAll) {
				String enName=(String) menumap.get("enName");
				String finallyName=StringConvert.underlineToCamelhump(enName);
				//String finallyName=null;
				String mapCnName=(String) menumap.get("mapCnName");
				String dataSource=(String) menumap.get("datasource");
				//String dataType = (String) menumap.get("dataType");
			
				
				//是否可搜索0:可 1:不可
				//搜索框类型0:文本框 1:下拉框 2:日期范围框 3:数字范围框 4:文本弹出框
				String searchFlag=StringUtils.isEmpty((String) menumap.get("searchFlag"))?"1":(String) menumap.get("searchFlag") ;
				String searchType= StringUtils.isEmpty((String) menumap.get("searchType"))?"0":(String) menumap.get("searchType");
				if("0".equalsIgnoreCase(searchFlag)){
				   Map<String, Object> result = Maps.newHashMap();
					result.put("searchType", searchType);
					if("0".equals(searchType)){
						//0:文本框
						result.put("en", finallyName);
						result.put("cn", mapCnName);
					}else if("1".equals(searchType)){
						//1:下拉框
						result.put("en", finallyName);
						result.put("cn", mapCnName);
						Map<String, Object> selectGroupCode = selectGroupCode(tableName,dataSource);
						result.putAll(selectGroupCode);
					}else if("2".equals(searchType)){
						//时间框
						//页面上处理
						result.put("en", finallyName);
						result.put("cn", mapCnName);
					}else{
						
					}
					lstmap.add(result);
				}
				
			}
		return lstmap;
	}

	public Map<String, Object>  selectGroupCode(String tableName,  String dataSource) {
		Map<String, Object> result=Maps.newHashMap();
		Map<String, Object> param=Maps.newHashMap();
		param.put(MyBatisConstans.DYTABLE_KEY, tableName.toUpperCase());
		if(StringUtils.isNotEmpty(dataSource)){
			BasCodeGroup baseCode = basCodeGroupMapper.findById(tableName, dataSource);
			String pefPass =null;
			if(null==baseCode){
				logger.info("{}未找到有效的下拉框信息",dataSource);
				return result;
			}else{
				 pefPass = baseCode.getPefPass();
			}
			
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
	
	public List<Map<String, Object>> reflection() {
		List<Map<String, Object>> list = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", "1");
		map.put("mapName", "name1");
		list.add(map);
		map = Maps.newHashMap();
		map.put("id", "2");
		map.put("mapName", "name2");
		list.add(map);
		return list;
	}
	
}

