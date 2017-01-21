package com.krm.voteplateform.common.reflecthelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.common.utils.Reflect;
import com.krm.voteplateform.web.bascode.service.BasCodeService;
import com.krm.voteplateform.web.bascodegroup.model.BasCodeGroup;
import com.krm.voteplateform.web.bascodegroup.service.BasCodeGroupService;

/**
 * ClassName:BaseHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年1月18日 下午10:19:01 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class BaseHelper {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(OrgReflectHelper.class);
	
	
	private BasCodeGroupService basCodeGroupService=SpringContextHolder.getBean("basCodeGroupService");
	
	private BasCodeService basCodeService=SpringContextHolder.getBean("basCodeService");
	
	/**
	 * 
	 * selectGroupCode:(). <br/>
	 * @author lixy
	 * @param tableName 动态表名
	 * @param dataSource 数据源
	 * @return
	 * @since JDK 1.6
	 */
	public List<Map<String, Object>>  selectGroupCode(String tableName,  String dataSource) {
		List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
		Map<String, Object> param=Maps.newHashMap();
		param.put(MyBatisConstans.DYTABLE_KEY, tableName);
		if(StringUtils.isNotEmpty(dataSource)){
			BasCodeGroup baseCode = basCodeGroupService.loadById(tableName, dataSource);
			String pefPass =null;
			if(null==baseCode){
				logger.info("{}未找到有效的下拉框信息",dataSource);
				return result;
			}else{
				 pefPass = baseCode.getPefPass();
			}
			
			if(StringUtils.isEmpty(pefPass)){
				 result = basCodeService.selectByCode(tableName,dataSource);
				 //result.put("selgroups", list);
				if(logger.isDebugEnabled()){
					logger.info("查询编码字典信息:{}",JSON.toJSONString(result));
				}
			}else{
				logger.info("执行反射类{}方法",pefPass);
				int lastIndexOf = pefPass.lastIndexOf(".");
				String clsStr=pefPass.substring(0, lastIndexOf);
				String menthodStr=pefPass.substring(lastIndexOf+1);
				result = Reflect.on(clsStr).create().call(menthodStr,param)
						.unwrap();
				//result.put("selgroups", list);
				if(logger.isDebugEnabled()){
					logger.info("查询编码反射字典信息:{}",JSON.toJSONString(result));
				}
			}
		}
	     return result;
	}

}

