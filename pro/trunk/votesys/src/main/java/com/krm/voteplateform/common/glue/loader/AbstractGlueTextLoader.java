package com.krm.voteplateform.common.glue.loader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.krm.voteplateform.common.base.dao.ExtDao;
import com.krm.voteplateform.common.exception.VoteBussinessException;
import com.krm.voteplateform.common.glue.model.PtDynamicSource;
import com.krm.voteplateform.common.spring.utils.SpringContextHolder;
import com.krm.voteplateform.common.utils.DateUtils;

/**
 * PT_DYNAMIC_SOURCE表处理类
 * 
 * @author JohnnyZhang
 */
public abstract class AbstractGlueTextLoader implements GlueTextLoader {
	@Override
	public PtDynamicSource load(String id) {
		String sql = "SELECT id,description,update_time as updateTime,dy_type as dyType,JAVA_CODE AS javaCode,";
		sql += "SAMPLE_NAME AS sampleName FROM PT_DYNAMIC_SOURCE WHERE ID=?";
		ExtDao extDao = SpringContextHolder.getBean("extDao");
		PtDynamicSource pts = extDao.getJdbcTemplate().queryForObject(sql, getRowMapper(), id);
		return pts;
	}

	public abstract LobHandler getLobHandle();

	private RowMapper<PtDynamicSource> getRowMapper() {
		return new RowMapper<PtDynamicSource>() {
			@Override
			public PtDynamicSource mapRow(ResultSet rs, int rowsNum) throws SQLException {
				PtDynamicSource pts = new PtDynamicSource();
				pts.setSampleName(rs.getString("sampleName"));
				pts.setDescription(rs.getString("description"));
				pts.setDyType(rs.getString("dyType"));
				pts.setId(rs.getString("id"));
				pts.setJavaCode(getLobHandle().getClobAsString(rs, "javaCode"));
				pts.setUpdateTime(rs.getString("updateTime"));
				return pts;
			}
		};
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(final PtDynamicSource pds) {
		Assert.notNull(pds);
		ExtDao extDao = SpringContextHolder.getBean("extDao");
		String sql = "select count(1) as count from PT_DYNAMIC_SOURCE where id=?";
		int queryForInt = extDao.queryForInt(sql, new String[] { pds.getId() });
		if (queryForInt > 0) {
			throw new VoteBussinessException("Glue001", "插入失败，重复编码值");
		}
		sql = "insert into PT_DYNAMIC_SOURCE(ID,DESCRIPTION,JAVA_CODE,DY_TYPE,UPDATE_TIME,SAMPLE_NAME) values(?,?,?,?,?)";
		int update = extDao.getJdbcTemplate().execute(sql,
				new AbstractLobCreatingPreparedStatementCallback(getLobHandle()) {
					@Override
					protected void setValues(PreparedStatement pStat, LobCreator lobCreator) throws SQLException,
							DataAccessException {
						pStat.setString(1, pds.getId());
						pStat.setString(2, pds.getDescription());
						lobCreator.setClobAsString(pStat, 3, pds.getJavaCode());
						pStat.setString(4, pds.getDyType());
						pStat.setString(5, DateUtils.formatDateTime(new Date()));
						pStat.setString(6, pds.getSampleName());
					}
				});
		return update;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(final PtDynamicSource pds) {
		Assert.notNull(pds);
		Assert.notNull(pds.getId());
		ExtDao extDao = SpringContextHolder.getBean("extDao");
		String sql = "update PT_DYNAMIC_SOURCE set DESCRIPTION=?,JAVA_CODE=?,UPDATE_TIME=?,SAMPLE_NAME=? where ID=?";
		int update = extDao.getJdbcTemplate().execute(sql,
				new AbstractLobCreatingPreparedStatementCallback(getLobHandle()) {
					@Override
					protected void setValues(PreparedStatement pStat, LobCreator lobCreator) throws SQLException,
							DataAccessException {
						pStat.setString(1, pds.getDescription());
						lobCreator.setClobAsString(pStat, 2, pds.getJavaCode());
						pStat.setString(3, DateUtils.formatDateTime(new Date()));
						pStat.setString(4, pds.getSampleName());
						pStat.setString(5, pds.getId());
					}
				});
		return update;
	}

	@Override
	public List<PtDynamicSource> getListByType(String type) {
		ExtDao extDao = SpringContextHolder.getBean("extDao");
		String sql = "SELECT id,description,update_time as updateTime,dy_type as dyType,JAVA_CODE AS javaCode,";
		sql += "SAMPLE_NAME AS sampleName FROM PT_DYNAMIC_SOURCE WHERE dy_type=? ORDER BY updateTime asc";
		List<PtDynamicSource> query = extDao.getJdbcTemplate().query(sql, new String[] { type }, getRowMapper());
		return query;
	}
}
