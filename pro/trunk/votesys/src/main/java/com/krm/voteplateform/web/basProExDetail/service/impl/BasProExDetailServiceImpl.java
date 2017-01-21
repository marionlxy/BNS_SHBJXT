package com.krm.voteplateform.web.basProExDetail.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.beetl.sql.core.VoteSqlManager;
import org.springframework.stereotype.Service;

import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.web.basProExDetail.dao.BasProExDetailMapper;
import com.krm.voteplateform.web.basProExDetail.model.BasProExDetail;
import com.krm.voteplateform.web.basProExDetail.service.BasProExDetailService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Service("basProExDetailService")
public class BasProExDetailServiceImpl implements BasProExDetailService{
	
	@Resource
	private BasProExDetailMapper basProExDetailMapper;
	
	@Resource
	private VoteSqlManager  voteSqlManager;


	//删除原有的明细再做修改
	@Override
	public void deleteBasProExDetail(String projectId, String extendGroupId) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(MyBatisConstans.DYTABLE_KEY, SysUserUtils.getCurrentCommissionCode());
		map.put("projectId", projectId);
		map.put("extendGroupId", extendGroupId);
		basProExDetailMapper.deleteBasProExDetail(map);
		
	}

}
