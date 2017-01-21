package com.krm.voteplateform.web.synproject.enums;

/**
 * 运行步骤
 * 
 * @author JohnnyZhang
 */
public enum RunningStepEnum {

	MOVEFILETORUNNING("1", "转移文件到运行目录"), UNZIPFILE("2", "解压文件"), XMLRECORDTODB("3", "文件记录入库"), MOVEFILETOSUCC("4",
			"文件转移至成功目录");

	private String step;// 运行步骤
	private String description;// 描述说明

	RunningStepEnum(String step, String description) {
		this.step = step;
		this.description = description;
	}

	public String getStep() {
		return step;
	}

	public String getDescription() {
		return description;
	}

}
