package com.krm.voteplateform.web.test.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;

@Controller
@RequestMapping("testFile")
public class TestFileController extends BaseController {

	@RequestMapping("uploadOneFiles")
	@ResponseBody
	public Result uploadOneFile(HttpServletRequest request, @RequestParam("file")MultipartFile filePic) throws Exception {
		String uploadDir = request.getServletContext().getRealPath("/") + "upload";
		byte[] bytes = filePic.getBytes();
		File dirPath = new File(uploadDir + File.separator + "test");// 上传路径
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		File uploadedFile = new File(dirPath + File.separator + filePic.getOriginalFilename());
		FileCopyUtils.copy(bytes, uploadedFile);
		return Result.successResult();
	}
}
