package com.krm.voteplateform.common.mybatis.plugins.pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.plugins.pagination.IDialect;
import com.krm.voteplateform.common.exception.MybatisPlusException;
import com.krm.voteplateform.common.mybatis.MyBatisConstans;
import com.krm.voteplateform.common.mybatis.plugins.DyTableModel;

/**
 *
 * 分页拦截器，用于拦截需要进行分页查询的操作，然后对其进行分页处理。<br/>
 * 利用拦截器实现Mybatis分页的原理：<br/>
 * 要利用JDBC对数据库进行操作就必须要有一个对应的Statement对象，Mybatis在执行Sql语句前就会产生一个包含Sql语句的Statement对象，而且对应的Sql语句
 * 是在Statement之前产生的，所以我们就可以在它生成Statement之前对用来生成Statement的Sql语句下手。在Mybatis中Statement语句是通过RoutingStatementHandler对象的
 * prepare方法生成的。所以利用拦截器实现Mybatis分页的一个思路就是拦截StatementHandler接口的prepare方法，然后在拦截器方法中把Sql语句改成对应的分页查询Sql语句，之后再调用
 * StatementHandler对象的prepare方法，即调用invocation.proceed()。
 * 对于分页而言，在拦截器里面我们还需要做的一个操作就是统计满足当前条件的记录一共有多少，这是通过获取到了原始的Sql语句后，把它改为对应的统计语句再利用Mybatis封装好的参数和设
 * 置参数的功能把Sql语句中的参数进行替换，之后再执行查询记录数的Sql语句进行总记录数的统计。
 * <p>
 * 动态表分页：只支持参数为HashMap和继承DyTableModel的值,暂时支持替换表名的公共部分，key为tableName
 * 
 * @author JohnnyZhang
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class DyTablePaginationInterceptor implements Interceptor {

	/* 溢出总页数，设置第一页 */
	private boolean overflowCurrent = false;

	/* 方言类型 */
	private String dialectType;

	/* 方言实现类 */
	private String dialectClazz;

	public Object intercept(Invocation invocation) throws Throwable {

		Object target = invocation.getTarget();
		if (target instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) target;
			MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
			RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");

			BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
			String originalSql = (String) boundSql.getSql();
			if (StringUtils.startsWithIgnoreCase(originalSql, "insert")
					|| StringUtils.startsWithIgnoreCase(originalSql, "update")
					|| StringUtils.startsWithIgnoreCase(originalSql, "create")
					|| StringUtils.startsWithIgnoreCase(originalSql, "delete")) {
				return invocation.proceed();
			}
			List<ParameterMapping> parameterMappings2 = new ArrayList<ParameterMapping>();// 重新规划参数列表
			Object parameterObject = boundSql.getParameterObject();// 取得参数值

			// 取得原参数属性
			List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
			if (parameterMappings.size() > 0) {// 若含有参数列表
				for (ParameterMapping parameterMapping : parameterMappings) {
					// 若参数属性名称不以动态表名前缀
					if (!parameterMapping.getProperty().startsWith(MyBatisConstans.DYTABLE_KEY)) {
						parameterMappings2.add(parameterMapping);// 重新添加参数到其中
					}
				}
			}
			if (parameterObject instanceof Map) {// 若参数值类型为Map
				Map<String, Object> map = (Map) parameterObject;// 强转成Map
				if (map.containsKey(MyBatisConstans.DYTABLE_KEY)) {
					originalSql = originalSql.replace(MyBatisConstans.DY,
							map.get(MyBatisConstans.DYTABLE_KEY).toString() + MyBatisConstans.DYTABLEPJ);// 重新生成带实际表名的SQL文
					map.remove(MyBatisConstans.DYTABLE_KEY);// map中删除此Key
				}
			} else if (parameterObject instanceof DyTableModel) {
				DyTableModel dtm = (DyTableModel) parameterObject;
				if (StringUtils.isNotEmpty(dtm.tableName)) {
					originalSql = originalSql.replace(MyBatisConstans.DY, dtm.tableName + MyBatisConstans.DYTABLEPJ);// 重新生成带实际表名的SQL文
				}
			}
			/* 不需要分页的场合 */
			if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
				metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
				metaStatementHandler.setValue("delegate.boundSql.parameterMappings", parameterMappings2);
				metaStatementHandler.setValue("delegate.boundSql.parameterObject", parameterObject);
				return invocation.proceed();
			}

			/* 定义数据库方言 */
			IDialect dialect = null;
			if (StringUtils.isNotEmpty(dialectType)) {
				dialect = DialectFactory.getDialectByDbtype(dialectType);
			} else {
				if (StringUtils.isNotEmpty(dialectClazz)) {
					try {
						Class<?> clazz = Class.forName(dialectClazz);
						if (IDialect.class.isAssignableFrom(clazz)) {
							dialect = (IDialect) clazz.newInstance();
						}
					} catch (ClassNotFoundException e) {
						throw new MybatisPlusException("Class :" + dialectClazz + " is not found");
					}
				}
			}

			/* 未配置方言则抛出异常 */
			if (dialect == null) {
				throw new MybatisPlusException(
						"The value of the dialect property in mybatis configuration.xml is not defined.");
			}

			/*
			 * <p> 禁用内存分页 </p> <p> 内存分页会查询所有结果出来处理（这个很吓人的），如果结果变化频繁这个数据还会不准。 </p>
			 */
			metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
			metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

			/**
			 * <p>
			 * 分页逻辑
			 * </p>
			 * <p>
			 * 查询总记录数 count
			 * </p>
			 */
			if (rowBounds instanceof Pagination) {
				MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
						.getValue("delegate.mappedStatement");
				Connection connection = (Connection) invocation.getArgs()[0];
				Pagination page = (Pagination) rowBounds;

				boundSql = new BoundSql(mappedStatement.getConfiguration(), originalSql, parameterMappings2,
						parameterObject);

				boolean orderBy = true;
				if (page.isSearchCount()) {
					/*
					 * COUNT 查询，去掉 ORDER BY 优化执行 SQL
					 */
					StringBuffer countSql = new StringBuffer("SELECT COUNT(1) AS TOTAL ");
					String tempSql = originalSql.toUpperCase();
					int formIndex = -1;
					if (page.isOptimizeCount()) {
						formIndex = tempSql.indexOf("FROM");
					}
					int orderByIndex = tempSql.lastIndexOf("ORDER BY");
					if (orderByIndex > -1) {
						orderBy = false;
						tempSql = originalSql.substring(0, orderByIndex);
					}
					if (page.isOptimizeCount() && formIndex > -1) {
						countSql.append(tempSql.substring(formIndex));
					} else {
						countSql.append("FROM (").append(tempSql).append(") A");
					}
					page = this.count(countSql.toString(), connection, mappedStatement, boundSql, page);
				}

				if (page.getTotal() > 0) {
					/* 执行 SQL */
					StringBuffer buildSql = new StringBuffer(originalSql);
					if (orderBy && null != page.getOrderByField()) {
						buildSql.append(" ORDER BY ").append(page.getOrderByField());
						buildSql.append(page.isAsc() ? " ASC " : " DESC ");
					}
					originalSql = dialect.buildPaginationSql(buildSql.toString(), page.getOffsetCurrent(),
							page.getSize());
				}
			}

			/**
			 * 查询 SQL 设置
			 */
			metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
			metaStatementHandler.setValue("delegate.boundSql.parameterMappings", parameterMappings2);
			metaStatementHandler.setValue("delegate.boundSql.parameterObject", parameterObject);
		}

		return invocation.proceed();
	}

	/**
	 * 查询总记录条数
	 * 
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param page
	 */
	public Pagination count(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql,
			Pagination page) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(sql);
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings(),
					boundSql.getParameterObject());
			ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,
					boundSql.getParameterObject(), countBS);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int total = 0;
			if (rs.next()) {
				total = rs.getInt(1);
			}
			page.setTotal(total);
			/*
			 * 溢出总页数，设置第一页
			 */
			if (overflowCurrent && (page.getCurrent() > page.getPages())) {
				page = new Pagination(1, page.getSize());
				page.setTotal(total);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return page;
	}

	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	public void setProperties(Properties prop) {
		String dialectType = prop.getProperty("dialectType");
		String dialectClazz = prop.getProperty("dialectClazz");
		if (StringUtils.isNotEmpty(dialectType)) {
			this.dialectType = dialectType;
		}
		if (StringUtils.isNotEmpty(dialectClazz)) {
			this.dialectClazz = dialectClazz;
		}
	}

	public void setDialectType(String dialectType) {
		this.dialectType = dialectType;
	}

	public void setDialectClazz(String dialectClazz) {
		this.dialectClazz = dialectClazz;
	}

	public void setOverflowCurrent(boolean overflowCurrent) {
		this.overflowCurrent = overflowCurrent;
	}

}
