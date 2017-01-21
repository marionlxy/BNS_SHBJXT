package com.krm.voteplateform.web.previewgen.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.Collections3;
import com.krm.voteplateform.web.function.dao.PtBasFunConfMapper;
import com.krm.voteplateform.web.function.service.impl.PtBasFunConfServiceImpl;
import com.krm.voteplateform.web.menulistconf.dao.MenuListConfMapper;
import com.krm.voteplateform.web.menuwinconf.dao.BasCusConfMapper;
import com.krm.voteplateform.web.previewgen.service.GenBtnService;
import com.krm.voteplateform.web.resource.dao.BaseResourceMapper;

/**
 * ClassName:GenBtnServiceImpl <br/>
 * Function:生成按钮信息 <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年11月29日 上午11:57:18 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Service("genBtnService")
public class GenBtnServiceImpl implements GenBtnService{
	
	Logger logger = LoggerFactory.getLogger(GenBtnServiceImpl.class);
	
	@Resource
	private PtBasFunConfMapper ptBasFunConfMapper;
	
	@Resource
	private BaseResourceMapper resourceMapper;
	
	@Resource
	private VoteSqlManager voteSqlManager;
	
	@Resource
	private MenuListConfMapper menuListConfMapper;
	


	
	@Override
	public List<Map<String, Object>> getButtonTableInfo(String tableName, String resid) {
		Map<String, String> mp = Maps.newHashMap();
		//String tableName = SysUserUtils.getCurrentCommissionCode();
		mp.put(MyBatisConstans.DYTABLE_KEY, tableName);
		mp.put("mlcid", resid);
		//弹出窗信息和按钮信息
		List<Map<String, Object>> btnlst=ptBasFunConfMapper.findBtnBasFunConf(mp);
		List<Map<String, Object>> resultlst=new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : btnlst) {
			String weightheight=(String) map.get("val");
			if(StringUtils.isNotEmpty(weightheight)){
				//处理弹出窗宽高信息
				//Map<String, Object> result=new HashMap<String, Object>();
				String[] weiheight = weightheight.split(";");
				map.put("height", weiheight[0]);
				map.put("weight", weiheight[1]);
				logger.info("操作功能弹出窗大小宽{}%高{}%",weiheight[0],weiheight[1]);
			}
			map.put(MyBatisConstans.DYTABLE_KEY, tableName.toLowerCase());
			resultlst.add(map);
		}
		
		if(logger.isDebugEnabled()){
			logger.info("查询到按钮信息{}",JSON.toJSONString(resultlst));
		}
		return 	resultlst;
	}




	@Override
	public List<Map<String, Object>> getBtnTableWind(String tableName, String rsid) {
		Map<String, String> mp = Maps.newHashMap();
		//String tableName = SysUserUtils.getCurrentCommissionCode();
		mp.put(MyBatisConstans.DYTABLE_KEY, tableName);
		mp.put("mlcid", rsid);
		//有没有窗体配置(有多条function)
		Set<Map<String,String>> st=new HashSet<Map<String,String>>();
		List<Map<String, Object>> windlst=ptBasFunConfMapper.findBtnBasFunConfWind(mp);
		logger.info("弹出窗体{}",JSON.toJSONString(windlst));
		return windlst;
	}

}

