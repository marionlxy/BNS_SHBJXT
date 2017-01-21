package com.krm.voteplateform.web.synproject.model;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.krm.voteplateform.web.basProject.model.BasProject;
import com.krm.voteplateform.web.menuwinconf.model.BasProAttch;

/**
 * 保存解析后文件内容
 * 
 * @author JohnnyZhang
 */
public class BasProjectXmlBean implements Serializable {

	private static final long serialVersionUID = -390708032681017598L;

	private BasProject basProject;
	private String code;// 委员会编码
	private String id;// 他系统传递的ID
	private List<BasProAttch> listBasProAttch = Lists.newArrayList();// 附件列表

	public BasProject getBasProject() {
		return basProject;
	}

	public void setBasProject(BasProject basProject) {
		this.basProject = basProject;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<BasProAttch> getListBasProAttch() {
		return listBasProAttch;
	}

	public void setListBasProAttch(List<BasProAttch> listBasProAttch) {
		this.listBasProAttch = listBasProAttch;
	}
}
