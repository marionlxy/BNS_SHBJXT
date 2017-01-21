package com.krm.voteplateform.web.synproject.listener;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.voteplateform.common.utils.DateUtils;
import com.krm.voteplateform.common.utils.FileUtils;
import com.krm.voteplateform.common.utils.Reflections;
import com.krm.voteplateform.common.utils.UUIDGenerator;
import com.krm.voteplateform.common.xml.XmlElement;
import com.krm.voteplateform.common.xml.XmlParser;
import com.krm.voteplateform.common.xml.XmlParserFactory;
import com.krm.voteplateform.common.xml.parseinfo.SimpleXmlParseInfo;
import com.krm.voteplateform.web.basProject.model.BasProject;
import com.krm.voteplateform.web.menuwinconf.model.BasProAttch;
import com.krm.voteplateform.web.synproject.contants.SynProjectContants;
import com.krm.voteplateform.web.synproject.enums.NTypeEnum;
import com.krm.voteplateform.web.synproject.enums.RunningStepEnum;
import com.krm.voteplateform.web.synproject.model.BasProjectXmlBean;
import com.krm.voteplateform.web.synproject.model.SynProjectExceptionModel;
import com.krm.voteplateform.web.synproject.service.ImportDataService;
import com.krm.voteplateform.web.util.SysUploaderUtils;

/**
 * 压缩包文件内容入库及转移
 * 
 * @author JohnnyZhang
 */
@Component
public class ResolveFileListener extends AbstractSyncProjectListener {

	private String occRunFile = "init";
	/** 执行步骤标志:【0：解析文件 1：转移文件 2：入库处理】 */
	private int stepFlag = 0;

	@Autowired
	private ImportDataService importDataService;

	@Override
	public int getOrder() {
		return 4;
	}

	@Override
	public void execute(File srcFile, File destFile, String mapCode) throws Exception {
		stepFlag = 0;
		String zipFileName = destFile.getAbsolutePath() + File.separator + srcFile.getName();
		logger.info("开始进行压缩包文件{}内容入库及转移处理...", zipFileName);
		String unZipedFilePath = FileUtils.getFileNameNoSuffix(zipFileName);
		BasProjectXmlBean basProjectXmlBean = new BasProjectXmlBean();
		String projectId = UUIDGenerator.getUUID();
		Map<String, String> mapAttchName = new HashMap<String, String>();
		// 开始解析project.xml
		File waitResolveFile = new File(unZipedFilePath + File.separator + SynProjectContants.PROJECT_FILE_NAME);
		logger.info("开始解析XML文件{}", waitResolveFile.getAbsolutePath());
		occRunFile = SynProjectContants.PROJECT_FILE_NAME;
		parserProjectXml(waitResolveFile, basProjectXmlBean, projectId);
		logger.info("自动生成的项目主键为{}.", projectId);
		// 开始解析docmapping.xml
		waitResolveFile = new File(unZipedFilePath + File.separator + SynProjectContants.ATTACH_FILE_NAME);
		if (waitResolveFile.exists()) {// 文件存在
			logger.info("开始解析XML文件{}", waitResolveFile.getName());
			occRunFile = SynProjectContants.ATTACH_FILE_NAME;
			mapAttchName = parserProjectAttachXml(waitResolveFile, basProjectXmlBean);
			logger.info("开始进行附件文件改名转移处理..");
			occRunFile = "init";
			stepFlag = 1;
			waitResolveFile = new File(unZipedFilePath);
			this.transferToUpload(waitResolveFile, basProjectXmlBean, mapAttchName);
			logger.info("附件文件改名转移处理完毕");
		}
		stepFlag = 2;
		logger.info("开始将解析后的XML文件进行入库处理,入库内容为{}", JSON.toJSONString(basProjectXmlBean));
		importDataService.saveData(basProjectXmlBean);// 把解析的数据插入数据库
		// 发送入库成功消息
		sendMsgToServlet(mapCode, FileUtils.getFileNameNoSuffix(srcFile.getName()), NTypeEnum.INSERTSUCCESS.getType(),
				NTypeEnum.INSERTSUCCESS.getMsg());
		logger.info("压缩包文件{}内容入库及转移处理结束", zipFileName);
	}

	/**
	 * 转移压缩包中附件文件夹到文件目录
	 * 
	 * @param file
	 * @param basProjectXmlBean
	 * @param mapAttchName
	 */
	private void transferToUpload(File file, BasProjectXmlBean basProjectXmlBean, Map<String, String> mapAttchName)
			throws Exception {
		// 取得文件列表
		String tempFilePath = file.getAbsolutePath() + File.separator + SynProjectContants.DOC_FILE_NAME;
		File tempFile = new File(tempFilePath);
		if (tempFile.exists() && tempFile.isDirectory()) {
			String projectId = basProjectXmlBean.getBasProject().getProjectId();
			// 取得项目附件上传文件夹目录
			String uploadpath = SysUploaderUtils.getRootAttchPath(basProjectXmlBean.getCode()) + projectId;
			// 文件不存在进行创建
			File dest = new File(uploadpath);
			if (!dest.exists()) {
				dest.mkdirs();
				dest.setWritable(true, false);
			}
			File[] docs = tempFile.listFiles();
			if (docs == null || docs.length == 0) {
				logger.warn("{}目录下无附件", tempFile.getAbsoluteFile());
				return;
			}
			// File fileDoc = null;
			for (int i = 0; i < docs.length; i++) {// 找到所有的附件文件
				String key = docs[i].getName();
				if (mapAttchName.containsKey(key)) {
					occRunFile = SynProjectContants.DOC_FILE_NAME + "中的" + key;
					String newFileName = new StringBuffer().append(uploadpath).append(File.separator)
							.append(mapAttchName.get(key)).toString();
					boolean copyFileCover = FileUtils.copyFileCover(docs[i].getAbsolutePath(), newFileName, true);
					logger.info("原文件{}改名并转移至文件{}中，执行结果{}", docs[i].getAbsolutePath(), newFileName, copyFileCover ? "成功"
							: "失败");
				} else {
					logger.info("{}下的附件映射文件中不包含对应的key：{}", file.getAbsolutePath(), key);
				}
			}
		} else {
			logger.info("{}不存在附件目录或指定目录不是目录", tempFilePath);
		}
	}

	/**
	 * 解析项目XML文件
	 * 
	 * @param pojectXmlFile
	 * @param basProjectXmlBean
	 * @param projectId 项目主键
	 * @throws DocumentException
	 */
	public void parserProjectXml(File pojectXmlFile, BasProjectXmlBean basProjectXmlBean, String projectId)
			throws Exception {
		BasProject basProject = new BasProject();
		basProject.setProjectId(projectId);
		SimpleXmlParseInfo xmlParseInfo = new SimpleXmlParseInfo();
		xmlParseInfo.setXmlPath(pojectXmlFile);
		XmlParser createXmlParser = XmlParserFactory.createXmlParser(xmlParseInfo);
		// 根节点
		XmlElement element = createXmlParser.parse();
		String code = element.getAttribute("code");
		basProjectXmlBean.setCode(code);
		// 取得record节点
		ListIterator<XmlElement> listIterator = element.get(0).listIterator();
		while (listIterator.hasNext()) {
			XmlElement xmlElement = (XmlElement) listIterator.next();
			String attribute = xmlElement.getAttribute("value");
			if (StringUtils.isNotEmpty(attribute)) {
				String dataType = xmlElement.getAttribute("dataType");
				String value = xmlElement.getAttribute("value");
				String enName = xmlElement.getAttribute("enName");
				logger.info("设置属性{}开始..", enName);
				if ("01".equals(dataType)) {// 数字
					Reflections.setFieldValue(basProject, enName, new BigDecimal(value));
				} else if ("02".equals(dataType)) {// 字符串
					Reflections.setFieldValue(basProject, enName, value);
				} else if ("04".equals(dataType)) {// 日期
					Reflections.setFieldValue(basProject, enName, DateUtils.parseDate(value));
				} else if ("05".equals(dataType)) {// 金额
					Reflections.setFieldValue(basProject, enName, new BigDecimal(value));
				} else if ("03".equals(dataType)) {// 编码&名称
					Reflections.setFieldValue(basProject, enName, value);
				} else if ("06".equals(dataType)) {// 明细
					// 忽略不进行处理
				} else {
					// 忽略不进行处理
				}
				logger.info("设置属性{}结束", enName);
			} else {
				continue;
			}
		}
		Timestamp nowTimestamp = DateUtils.getNowTimestamp();
		basProject.setCodeOa("1");// 外部系统创建
		basProject.setDelFlag("0");// 正常
		basProject.setCreateTime(nowTimestamp);
		basProject.setUpdateTime(nowTimestamp);
		basProject.setCreateBy("sysCreate");// 系统创建
		basProject.setUpdateBy("sysCreate");// 系统创建
		basProjectXmlBean.setBasProject(basProject);
	}

	/**
	 * 解析项目附件XML文件
	 * 
	 * @param attachXmlFile
	 * @return
	 * @throws DocumentException
	 */
	public Map<String, String> parserProjectAttachXml(File attachXmlFile, BasProjectXmlBean basProjectXmlBean)
			throws Exception {
		Map<String, String> map = Maps.newHashMap();
		List<BasProAttch> listBasProAttch = Lists.newArrayList();
		BasProAttch basProAttch = null;

		SimpleXmlParseInfo xmlParseInfo = new SimpleXmlParseInfo();
		xmlParseInfo.setXmlPath(attachXmlFile);
		XmlParser createXmlParser = XmlParserFactory.createXmlParser(xmlParseInfo);
		// 根节点
		XmlElement element = createXmlParser.parse();
		// 取得record节点
		ListIterator<XmlElement> listIterator = element.get(0).listIterator();
		while (listIterator.hasNext()) {
			XmlElement mappingNode = (XmlElement) listIterator.next();
			Reflections.setFieldValue(basProAttch, "kindId", mappingNode.getAttribute("kindId"));
			Reflections.setFieldValue(basProAttch, "kindName", mappingNode.getAttribute("kindName"));
			Reflections.setFieldValue(basProAttch, "orderCode", mappingNode.getAttribute("orderCode"));
			Reflections.setFieldValue(basProAttch, "originalName", mappingNode.getAttribute("realDocName"));
			listBasProAttch.add(basProAttch);
			map.put(mappingNode.getAttribute("mappingDocName"), mappingNode.getAttribute("realDocName"));
		}
		basProjectXmlBean.setListBasProAttch(listBasProAttch);
		return map;
	}

	@Override
	public RunningStepEnum getCurrentStep() {
		return RunningStepEnum.XMLRECORDTODB;
	}

	@Override
	public SynProjectExceptionModel handlerException(Exception e, File srcFile, File destFile, String mapCode) {
		String zipFileName = destFile.getAbsolutePath() + File.separator + srcFile.getName();
		StringBuffer errSb = new StringBuffer("[");
		String code = "";
		NTypeEnum nTypeEnum = null;// 部分错误信息
		if (stepFlag == 0) {
			code = SynProjectContants.ERROR_CODE_1003;
			nTypeEnum = NTypeEnum.RESOLVEFILEERROR;
		} else if (stepFlag == 1) {
			code = SynProjectContants.ERROR_CODE_1004;
			nTypeEnum = NTypeEnum.ATTCHFILESMOVEERROR;
		} else {
			code = SynProjectContants.ERROR_CODE_1005;
			nTypeEnum = NTypeEnum.INSERTFAILURE;
		}
		errSb.append(code);
		errSb.append("]");
		errSb.append("压缩包文件");
		errSb.append(zipFileName);
		if (!"init".equals(occRunFile)) {
			errSb.append("下的").append(occRunFile);
		}
		errSb.append(nTypeEnum.getMsg());
		logger.error(errSb.toString(), e);
		sendMsgToServlet(mapCode, FileUtils.getFileNameNoSuffix(srcFile.getName()), nTypeEnum.getType(),
				nTypeEnum.getMsg() + ",详细信息[" + e.getMessage() + "]");
		return new SynProjectExceptionModel(code, errSb.toString());
	}

}
