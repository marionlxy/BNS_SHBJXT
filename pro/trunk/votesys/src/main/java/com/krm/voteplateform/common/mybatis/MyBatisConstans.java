package com.krm.voteplateform.common.mybatis;

/**
 * MyBatis自定义定数
 * 
 * @author JohnnyZhang
 */
public interface MyBatisConstans {

	/** 动态表名前缀 */
	public static final String DYTABLE_KEY = "tableName";

	/** 动态表名拼接符号 */
	public static final String DYTABLEPJ = "_";

	public static final String DYTABLE_MARK = "?";

	public static final String DY = DYTABLE_MARK + DYTABLEPJ;

}
