package com.krm.voteplateform.web.util;

import java.io.File;
import java.util.Map;

import org.springframework.util.ObjectUtils;

import com.google.common.collect.Maps;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.common.utils.Img2Base64Utils;
import com.krm.voteplateform.web.commission.service.PtCommissionService;

public class LogoUtils {

	/**
	 * 取得logo
	 * 
	 * @return 若委员会无图片或图片已删除，使用默认图像
	 */
	public static String getLogo() {
		String result= "";
		PtCommissionService service = SpringContextHolder.getBean("ptCommissionService");
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", SysUserUtils.getCurrentCommissionCode());
		Map<String, Object> commisssion = service.getCommisssionByCode(map).get(0);
		Object object = commisssion.get("logo");
		String realPath = SysUserUtils.getSession().getServletContext().getRealPath("/static/images/pretermit.jpg");
		if (!ObjectUtils.isEmpty(object)) {
			String logoPath = SysUploaderUtils.getLogoPath(SysUserUtils.getCurrentCommissionCode());
			File file = new File(logoPath + object.toString());
			if (file.exists()) {
				realPath = logoPath + object.toString();
			}
		}
		result = "data:image/jpg;base64," + Img2Base64Utils.imageToBase64(realPath);
		return result;
	}
}
