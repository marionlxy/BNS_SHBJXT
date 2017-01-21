package com.krm.voteplateform.common.base.dao;

import java.util.List;

/**
 * 基础拓展Dao
 * 
 * @author Johnny
 */
public interface ExtDao {

	/**
	 * 执行SQL文
	 * 
	 * @param sqlText
	 */
	public void executeBySQLText(String sqlText);

	/**
	 * 执行插入SQL文
	 * 
	 * @param sqlText
	 */
	public void insertBySQLText(String sqlText);

	/**
	 * 执行查询Sql文
	 * 
	 * @param s SQL文
	 * @param aobj 参数列表
	 * @return
	 */
	public List<?> queryBySQLText(String s, Object aobj[]);

	/**
	 * 执行查询SQL
	 * @param s
	 * @return
	 */
	public List<?> queryBySQLText(String s);

	/**
	 * 执行更新SQL
	 * @param s 更新SQ
	 * @param aobj 参数列表
	 * @return
	 */
	public int updateBySQLText(String s, Object aobj[]);

	/**
	 * 执行更新SQL
	 * @param s 更新SQL
	 * @return
	 */
	public int updateBySQLText(String s);

	public String queryForString(String s, String s1, Object aobj[]);

	public String queryForString(String s, String s1);

	public int queryForInt(String s, Object aobj[]);

	public int queryForInt(String s);

	public long queryForLong(String s, Object aobj[]);

	public long queryForLong(String s);

	public void commit();
}
