package com.krm.voteplateform.common.base.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.krm.voteplateform.common.base.dao.ExtDao;

public class ExtDaoImpl implements ExtDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public ExtDaoImpl() {
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void executeBySQLText(String sqlText) {
		jdbcTemplate.execute(sqlText);
	}

	public void insertBySQLText(String sqlText) {
		jdbcTemplate.execute(sqlText);
	}

	public List<?> queryBySQLText(String sqlText, Object args[]) {
		return jdbcTemplate.queryForList(sqlText, args);
	}

	public List<?> queryBySQLText(String sqlText) {
		return jdbcTemplate.queryForList(sqlText);
	}

	public int updateBySQLText(String sqlText, Object args[]) {
		return jdbcTemplate.update(sqlText, args);
	}

	public int updateBySQLText(String sqlText) {
		return jdbcTemplate.update(sqlText);
	}

	public String queryForString(String sqlText, String str, Object args[]) {
		return ((Map) jdbcTemplate.queryForList(sqlText, args).get(0)).get(str).toString();
	}

	public String queryForString(String sqlText, String str) {
		List<?> resultlist = jdbcTemplate.queryForList(sqlText);
		if ((resultlist != null) && (resultlist.size() > 0))
			return ((Map) resultlist.get(0)).get(str).toString();
		else
			return "";
	}

	public int queryForInt(String sqlText, Object args[]) {
		Number number = jdbcTemplate.queryForObject(sqlText, args, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public int queryForInt(String sqlText) {
		Number number = jdbcTemplate.queryForObject(sqlText, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public long queryForLong(String sqlText, Object args[]) {
		Number number = jdbcTemplate.queryForObject(sqlText, args, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	public long queryForLong(String sqlText) {
		Number number = jdbcTemplate.queryForObject(sqlText, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	public void commit() {
		try {
			jdbcTemplate.getDataSource().getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
