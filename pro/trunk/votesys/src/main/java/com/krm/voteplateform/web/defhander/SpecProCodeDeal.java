package com.krm.voteplateform.web.defhander;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krm.voteplateform.common.base.dao.ExtDao;
import com.krm.voteplateform.common.glue.handler.IGlueHandler;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.web.basProject.model.BasMetting;
import com.krm.voteplateform.web.basProject.model.BasProject;
import com.krm.voteplateform.web.util.SysUserUtils;

public class SpecProCodeDeal implements IGlueHandler{

private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public String handler(Object... params) throws Exception {
		logger.info("开始执行特殊处理的项目编码");
		String proCodeType=(String) params[2];
		
		String specProCode = null;
		String  year = DateUtils.getYear();// 当前年
		String tableName = SysUserUtils.getCurrentCommissionCode();
      	String tablelaxt ="_BAS_PROJECT";
        String resTableName = tableName+tablelaxt;
		logger.info("tableName="+tableName);
		logger.info("proCodeType{}"); 
		if("1".equals(proCodeType)){  //1:表示未项目，2：表示 有会议时添加 
String sql="select * from "+ resTableName + " where SPEC_PRO_CODE=( select max(SPEC_PRO_CODE)from "+ resTableName + " where DEL_FLAG='0'and PROJECT_STATE_ID='10200300')";
    	logger.info("执行sql1111"+ sql);
		ExtDao extDao = SpringContextHolder.getBean("extDao");
		List<Map<String, Object>>  resultList =	extDao.getJdbcTemplate().queryForList(sql);
		logger.info("resultList.size()="+resultList.size());
		if(null != resultList && resultList.size()>0){
			String maxProCode =resultList.get(0).get("SPEC_PRO_CODE").toString();
           logger.info("resultList.get(0)的数据值 ="+resultList.get(0));
          logger.info("maxProCode="+maxProCode);
			if(StringUtils.isNotEmpty(maxProCode)){
				String[] aa = maxProCode.split("-");
				String fixCode = aa[0];// 年份
				String lasCode = aa[1];// 顺序号
				 specProCode = year + "-";
				if(year.equals(fixCode)){
					specProCode += StringUtils.leftPad(String.valueOf((Integer.parseInt(lasCode) + 1)), 3, "0");
				}else{
					specProCode +="001";
				}
			}else{
				specProCode +="001";
			}
			}
		}else if("2".equals(proCodeType)){
				BasProject basProject = (BasProject) params[0];
				logger.info("输出basProject=" + basProject);
				BasMetting basMetting =(BasMetting)params[1];
				String projectId = basProject.getProjectId();
				logger.info("项目projectId=" + projectId);
				String id = basMetting.getId();
				logger.info("会议ID=" + id);
				String specMettingCode = basMetting.getSpecMettingCode();
				String[] meCode= specMettingCode.split("-");
					 year = meCode[0];
         		 logger.info("会议编码specMettingCode=" + specMettingCode);
         		 String sql="select * from  "+ resTableName + " where SPEC_PRO_CODE=(select max(SPEC_PRO_CODE) as SPEC_PRO_CODE from  "+ resTableName + " where CONFERENCE_ID=?)";
              logger.info("输出存在会议时添加议题的特殊项目编码sql="+ sql);
               ExtDao extDao = SpringContextHolder.getBean("extDao");
               List<Map<String, Object>> comResultList = extDao.getJdbcTemplate().queryForList(sql,id);
            logger.info("comResultList.size()="+comResultList.size());
				if(null!=comResultList && comResultList.size()>0){
					String proCode = comResultList.get(0).get("SPEC_PRO_CODE").toString();//取得此次会议的项目编号
                  logger.info("取得这次会议中的最大特殊编号proCode="+proCode);
					if(StringUtils.isNotEmpty(proCode)){
					String[] metCode = proCode.split("-");//会议编号
					String lasCode = metCode[metCode.length-1];//最后一个
					if(metCode.length==3){
						specProCode = specMettingCode + "-";
						specProCode += StringUtils.leftPad(String.valueOf((Integer.parseInt(lasCode) + 1)), 3, "0");
					}else{
						 specProCode = year + "-";
					specProCode += StringUtils.leftPad(String.valueOf((Integer.parseInt(lasCode) + 1)), 3, "0");
					}
					} 
				}else{
					specProCode= specProCode+"001";
				}
				
			}
		logger.info("输出的项目编号specProCode" + specProCode);
			return specProCode;
		}
	}
