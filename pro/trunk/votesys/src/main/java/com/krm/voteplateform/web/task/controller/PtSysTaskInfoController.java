package com.krm.voteplateform.web.task.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.web.task.model.PtSysTaskInfo;
import com.krm.voteplateform.web.task.service.PtSysTaskInfoService;
import com.krm.voteplateform.web.task.service.PtSysTaskLogService;

/**
 *
 * @author JohnnyZhang
 */
@Controller
@RequestMapping("pt/task")
public class PtSysTaskInfoController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private PtSysTaskInfoService ptSysTaskInfoService;

	@Resource
	private PtSysTaskLogService ptSysTaskLogService;

	@RequestMapping("toTaskList")
	public String toTaskList(HttpServletRequest request) {
		return "plateform/task/taskList";
	}

	private String getId(HttpServletRequest request) {
		return request.getParameter("id");
	}

	/**
	 * 取得任务列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "taskList")
	@ResponseBody
	public List<Map<String, Object>> taskList(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, Object>> taskList = ptSysTaskInfoService.getAllTaskList();
		return taskList;
	}

	/**
	 * 任务日切
	 * 
	 * @param model
	 */
	@RequestMapping(value = "taskNextdate")
	@ResponseBody
	public Result taskNextdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		//
		String id = getId(request);
		String taskDate = request.getParameter("taskDate");
		String flag = request.getParameter("flag");
		/**
		 * 判断是否运行成功，如果没有运行成功运行当天的如果运行成功了，运行下一天的
		 */
		if (!"1".equals(flag)) {
			String newsystaskdate = DateUtils.formatDate(DateUtils.getaferdate(DateUtils.parseDate(taskDate)));
			taskDate = newsystaskdate;
		}
		PtSysTaskInfo sysTaskInfo = new PtSysTaskInfo();
		sysTaskInfo.setId(id);
		sysTaskInfo.setTaskRuneDate(taskDate);
		ptSysTaskInfoService.updateByIdSelective(sysTaskInfo);
		ptSysTaskLogService.saveTaskRest(id, taskDate);
		return Result.successResult();
	}

	/**
	 * 任务重置
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "taskReset")
	@ResponseBody
	public Result taskReset(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = getId(request);
		String taskDate = request.getParameter("taskDate");
		PtSysTaskInfo sysTaskInfo = new PtSysTaskInfo();
		sysTaskInfo.setId(id);
		sysTaskInfo.setTaskRuneDate(taskDate);
		ptSysTaskInfoService.updateByIdSelective(sysTaskInfo);
		ptSysTaskLogService.saveTaskRest(id, taskDate);
		return Result.successResult();
	}

	/**
	 * 失败重做
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "taskagaindo")
	@ResponseBody
	public Result taskagaindo(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = getId(request);
		String taskDate = request.getParameter("taskDate");
		ptSysTaskLogService.updateSysTaskLogStatus("9", "", id, taskDate);
		return Result.successResult();
	}

	/**
	 * 忽略依赖
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "taskelideRelyon")
	@ResponseBody
	public Result taskelideRelyon(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = getId(request);
		String taskDate = request.getParameter("taskDate");
		ptSysTaskLogService.updateSysTaskLogStatus("3", "", id, taskDate);
		return Result.successResult();
	}

	/**
	 * 忽略成功
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "taskelideSuc")
	@ResponseBody
	public Result taskelideSuc(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = getId(request);
		String taskDate = request.getParameter("taskDate");
		ptSysTaskLogService.updateSysTaskLogStatus("7", "", id, taskDate);
		return Result.successResult();
	}

	/**
	 * 运行状态的任务删除Log日志
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delTaskLog")
	@ResponseBody
	public Result delTaskLog(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = getId(request);
		String taskDate = request.getParameter("taskDate");
		ptSysTaskLogService.deleteSysTaskLog1(id, taskDate);
		return Result.successResult();
	}

	/**
	 * 错误文件下载
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "downfile")
	public String downfile(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		FileInputStream fis = null;
		BufferedReader sr = null;
		PrintWriter output = null;
		try {
			// String id= getId(request);
			String filepath = request.getParameter("filepath");
			response.reset();
			response.setContentType("application/x-msdownload; charset=UTF-8");
			String fileName = filepath.substring(filepath.lastIndexOf("/"), filepath.length());
			response.addHeader("Content-Disposition", "attachment;filename=" + (fileName));

			File file = new File(filepath);
			fis = new FileInputStream(file);
			sr = new BufferedReader(new InputStreamReader(fis));
			output = response.getWriter();
			char[] b = new char[1024];
			int i = 0;
			while ((i = sr.read(b)) > 0) {
				output.write(b, 0, i);
			}
			output.flush();
			output.close();
			sr.close();
			fis.close();
		} catch (Exception e) {
			logger.error("下载错误日志异常：" + e.getMessage());
		} finally {
			if (output != null) {
				output.close();
			}
			if (sr != null) {
				sr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return null;
	}

}
