package com.krm.voteplateform.web.task.run;

import java.util.HashMap;
import java.util.Map;

public enum TaskExcuResult {
	/** 时间未满足 */
	TASKURNAFDATE("1", "时间未满足"),
	/** 依赖未满足 */
	TASKURNAFTASK("2", "依赖未满足"),
	/** 进入队列 */
	TASKURNINQUER("3", "进入队列"),
	/** 正在执行 */
	TASKURNERRINRUN("4", "正在执行"),
	/** 执行成功 */
	TASKURNERRSUC("5", "执行成功"),
	/** 执行失败 */
	TASKURNERRERR("6", "执行失败"),
	/** 忽略成功 */
	TASKURNERRHLSUC("7", "忽略成功"),
	/** 忽略失败 */
	TASKURNERRHLERR("8", "忽略失败"),
	/** 任务失败重做 */
	TASKURNERRERRRUN("9", "任务失败重做"),
	/** 忽略依赖 */
	TASKURNERRHLRNE("10", "忽略依赖"),
	/** 数据文件未到达 */
	TASKURNERRFILEDEF("11", "数据文件未到达");

	public static final Map<String, TaskExcuResult> ALL_MAP = new HashMap<String, TaskExcuResult>() {

		private static final long serialVersionUID = -7006102166403967829L;
		{
			put("1", TASKURNAFDATE);
			put("2", TASKURNAFTASK);
			put("3", TASKURNINQUER);
			put("4", TASKURNERRINRUN);
			put("5", TASKURNERRSUC);
			put("6", TASKURNERRERR);
			put("7", TASKURNERRHLSUC);
			put("8", TASKURNERRHLERR);
			put("9", TASKURNERRERRRUN);
			put("10", TASKURNERRHLRNE);
			put("11", TASKURNERRFILEDEF);
		}
	};

	private String statusDescritpion;
	private String statusValue;

	TaskExcuResult(String statusValue, String statusDescritpion) {
		this.statusValue = statusValue;
		this.statusDescritpion = statusDescritpion;
	}

	/**
	 * @return the statusDescritpion
	 */
	public String getStatusDescritpion() {
		return statusDescritpion;
	}

	/**
	 * @return the statusValue
	 */
	public String getStatusValue() {
		return statusValue;
	}

}
