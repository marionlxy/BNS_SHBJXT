package com.krm.voteplateform.common.beetl.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.krm.voteplateform.web.basProject.service.BasMettingService;
import com.krm.voteplateform.web.menuwinconf.service.BasMenuWinConfService;
import com.krm.voteplateform.web.menuwinconf.service.BasProAttchService;

@Component
public class BasMenuWinConfUtilsFunction {
	
	@Resource
	private BasMenuWinConfService basMenuWinConfService;
	
	@Resource
	private BasMettingService basMettingService;
	
	@Resource
	private BasProAttchService fileUploadService;
	
	/**
	 * 根据项目id和组id获取明细数据
	 * @param projectId
	 * @param groupId
	 * @return
	 */
	public List<Map<String,Object>> getExtDetDataList(String projectId, String groupId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		params.put("groupId", groupId);
		return basMenuWinConfService.getExtDetDataList(params);
	}
	
	/**
	 * 获取附件类型,根据项目id查看会议状态
	 * @param projectId
	 * @return
	 */
	public List<Map<String,Object>> getProAttchType(String projectId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(projectId != null){
			Map<String,Object> metting = basMettingService.findOrComplement(projectId);
			if(metting != null && metting.get("stateId").toString().equals("10000102")){
				params.put("msFlag", "1");
			}else{
				params.put("msFlag", "0");
			}
			return fileUploadService.getattchTypeList(params);
		}
		return null;
	}
	
	/**
	 * 获取附件列表
	 * @param projectId
	 * @return
	 */
	public List<Map<String,Object>> getProAttchList(String projectId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		return fileUploadService.getProAttchList(params);
	}
}
