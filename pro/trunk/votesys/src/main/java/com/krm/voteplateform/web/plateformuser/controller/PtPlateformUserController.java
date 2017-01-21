package com.krm.voteplateform.web.plateformuser.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.web.plateformuser.service.PtPlateformUserService;

@Controller
@RequestMapping("pt/plateformuser")
public class PtPlateformUserController extends BaseController{
	
	@Autowired
	private PtPlateformUserService ptPlateformUserService;
	
	@RequestMapping("toPtUserList")
	@ResponseBody
	public List<Map<String, Object>> toPtUserList(){
		List<Map<String, Object>> list=ptPlateformUserService.toPtUserList();
		return list;
	}
	
	@RequestMapping("toPtUserListByCondition")
	@ResponseBody
	public List<Map<String, Object>> toPtUserListByCondition(HttpServletRequest request){
		String organId=getPara("organId");
		String userName=getPara("userName");
		try {
			userName = new String((userName).getBytes("ISO-8859-1"),"UTF-8");
			userName = java.net.URLDecoder.decode(userName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> list=ptPlateformUserService.toPtUserListByCondition(organId,userName);
		return list;
	}

}
