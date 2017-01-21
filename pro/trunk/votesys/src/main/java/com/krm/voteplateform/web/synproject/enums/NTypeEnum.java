package com.krm.voteplateform.web.synproject.enums;

/**
 * 通知Servlet地址追加ntype参数
 * 
 * @author JohnnyZhang
 */
public enum NTypeEnum {

	UNZIPSUCCESSCHECKOK("1", "文件解压成功且压缩包内文件合法"), UNZIPSUCCESSCHECKERROR("2", "文件解压成功但压缩包内文件不合法"), UNZIPFAILURE("3",
			"文件解压失败"), INSERTSUCCESS("4", "入库成功"), RESOLVEFILEERROR("5", "文件解析失败"), ATTCHFILESMOVEERROR("6", "附件转移异常"), INSERTFAILURE(
			"7", "信息入库失败");

	private String type;
	private String msg;

	NTypeEnum(String type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}

}
