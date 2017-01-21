package com.krm.voteplateform.web.menuwinconf.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.web.menuwinconf.model.BasProAttch;
import com.krm.voteplateform.web.menuwinconf.service.BasProAttchService;
import com.krm.voteplateform.web.util.SysUploaderUtils;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
@RequestMapping("files")
public class BasProAttchController extends BaseController {

	@Resource
	private BasProAttchService fileUploadService;

	/**
	 * 批量上传附件
	 * 
	 * @param basProAttch
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public Result updatePic(@ModelAttribute BasProAttch basProAttch, @RequestParam Map<String, Object> params) {
		List<BasProAttch> list = basProAttch.getAttchList();
		if (list != null) {
			String name = SysUserUtils.getSessionLoginUser().getUserName();
			Timestamp c = DateUtils.getNowTimestamp();
			for (Iterator<BasProAttch> iterator = list.iterator(); iterator.hasNext();) {
				BasProAttch obj = (BasProAttch) iterator.next();
				obj.setCreateBy(name);
				obj.setUpdateBy(name);
				obj.setCreateTime(c);
				obj.setUpdateTime(c);
				MultipartFile multipartFile = obj.getFile();
				if (multipartFile != null) {
					obj.setOriginalName(multipartFile.getOriginalFilename());
				} else {
					iterator.remove();
				}
			}
			int result = fileUploadService.bachInsertFile(list);
			if (result == list.size()) {
				for (BasProAttch obj : list) {
					try {
						MultipartFile multipartFile = obj.getFile();
						String filePath = SysUploaderUtils.getProAttchPath(obj.getProjectId()) + obj.getOriginalName();
						FileUtils.createFile(filePath);
						File file = new File(filePath);
						multipartFile.transferTo(file);
					} catch (IOException e) {
						logger.error("批量上传附件发生异常", e);
					}
				}
				return Result.successResult();
			}
		}
		return Result.errorResult();
	}

	/**
	 * 校验文件名字
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("valiFileName")
	@ResponseBody
	public Map<String, String> valiGroupName(@RequestParam Map<String, Object> params) {
		String name = params.get("param").toString();
		String fileName = name.substring(name.lastIndexOf(File.separator) + 1, name.length());
		params.put("fileName", fileName);
		logger.info("开始验证文件{}信息", params);
		boolean flag = fileUploadService.checkFileName(params);
		Map<String, String> result = new HashMap<String, String>();
		if (flag) {
			result.put("info", "此文件可用");
			result.put("status", "y");
		} else {
			result.put("info", "此项目已经上传过该文件");
			result.put("status", "n");
		}
		logger.info("结束验证文件{}信息", name);
		return result;
	}

	/**
	 * 删除附件
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("deleteAttch")
	@ResponseBody
	public Map<String, String> deleteAttch(@RequestParam Map<String, Object> params) {
		Map<String, String> result = new HashMap<String, String>();
		BasProAttch attch = fileUploadService.getOneAttchType(params);
		int count = fileUploadService.deleteOneAttchType(params);
		if (count > 0) {
			String filePath = SysUploaderUtils.getProAttchPath(attch.getProjectId()) + attch.getOriginalName();
			FileUtils.deleteFile(filePath);
			result.put("info", "删除成功");
		} else {
			result.put("info", "删除失败");
		}
		return result;
	}
}
