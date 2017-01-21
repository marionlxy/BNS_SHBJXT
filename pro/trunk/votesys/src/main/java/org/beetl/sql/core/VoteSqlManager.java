package org.beetl.sql.core;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.kit.StringKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krm.voteplateform.common.mybatis.MyBatisConstans;

public class VoteSqlManager {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private SQLManager sqlManager;

	/**
	 * @param sqlManager the sqlManager to set
	 */
	public void setSqlManager(SQLManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	/**
	 * @return the sqlManager
	 */
	public SQLManager getSqlManager() {
		return sqlManager;
	}

	/**
	 * 插入方法，返回插入数量
	 * 
	 * @param tableNamePrefix 动态表前缀
	 * @param clazz 类
	 * @param paras 实体
	 * @return 返回插入数量
	 */
	public int insert(String tableNamePrefix, Class<?> clazz, Object paras) {
		if (StringKit.isNotBlank(tableNamePrefix)) {
			sqlManager.setTableNamePrefix(tableNamePrefix);
		}
		return sqlManager.insert(clazz, paras);
	}

	/**
	 * 插入方法，返回插入数量
	 * 
	 * @param tableNamePrefix 动态表前缀
	 * @param paras 实体类
	 * @return 插入数量
	 */
	public int insert(String tableNamePrefix, Object paras) {
		if (StringKit.isNotBlank(tableNamePrefix)) {
			sqlManager.setTableNamePrefix(tableNamePrefix);
		}
		return sqlManager.insert(paras);
	}

	/**
	 * 插入方法，返回插入数量
	 * 
	 * @param tableNamePrefix 动态表前缀
	 * @param paras 实体类
	 * @param autoAssignKey 是否自动设定主键值,若设置为true，主键值会自动设值到对象中
	 * @return 插入数量
	 */
	public int insert(String tableNamePrefix, Object paras, boolean autoAssignKey) {
		if (StringKit.isNotBlank(tableNamePrefix)) {
			sqlManager.setTableNamePrefix(tableNamePrefix);
		}
		return sqlManager.insert(paras.getClass(), paras, autoAssignKey);
	}

	/**
	 * 插入方法，返回插入数量
	 * 
	 * @param tableNamePrefix 动态表前缀
	 * @param clazz 类
	 * @param paras 实体类
	 * @param autoAssignKey 是否自动设定主键值,若设置为true，主键值会自动设值到对象中
	 * @return 插入数量
	 */
	public int insert(String tableNamePrefix, Class<?> clazz, Object paras, boolean autoAssignKey) {
		if (StringKit.isNotBlank(tableNamePrefix)) {
			sqlManager.setTableNamePrefix(tableNamePrefix);
		}
		return sqlManager.insert(clazz, paras, autoAssignKey);
	}

	/**
	 * 插入，并获取主键
	 * 
	 * @param tableNamePrefix 动态表前缀
	 * @param clazz 类型
	 * @param paras 实体类
	 * @param holder 存储主键值
	 * @return 插入数量
	 */
	public int insert(String tableNamePrefix, Class<?> clazz, Object paras, KeyHolder holder) {
		if (StringKit.isNotBlank(tableNamePrefix)) {
			sqlManager.setTableNamePrefix(tableNamePrefix);
		}
		return sqlManager.insert(clazz, paras, holder);
	}

	/**
	 * 
	 * 批量插入
	 * 
	 * @param tableNamePrefix 动态表前缀
	 * @param clazz 类型
	 * @param list 实体类列表
	 * @return 插入数量
	 */
	public int[] insertBatch(String tableNamePrefix, Class<?> clazz, List<?> list) {
		if (StringKit.isNotBlank(tableNamePrefix)) {
			sqlManager.setTableNamePrefix(tableNamePrefix);
		}
		return sqlManager.insertBatch(clazz, list);
	}

	/**
	 * 更新对象，为null的值不参与更新，如果想更新null值，请使用updateById
	 * 
	 * @param tableNamePrefix 动态表前缀
	 * @param obj
	 * @return
	 */
	public int updateTemplateById(String tableNamePrefix, Object obj) {
		if (StringKit.isNotBlank(tableNamePrefix)) {
			sqlManager.setTableNamePrefix(tableNamePrefix);
		}
		return sqlManager.updateTemplateById(obj);
	}

	/**
	 * 
	 * 批量更新(按条件全量更新)
	 * 
	 * @param tableNamePrefix 动态表前缀
	 * @param list ,包含pojo（不支持map）
	 * @return
	 */
	public int[] updateByIdBatch(String tableNamePrefix, List<?> list) {
		if (StringKit.isNotBlank(tableNamePrefix)) {
			sqlManager.setTableNamePrefix(tableNamePrefix);
		}
		return sqlManager.updateByIdBatch(list);
	}

	/**
	 * 为null的值不参与更新
	 * 
	 * @param tableNamePrefix
	 * @param list
	 * @return
	 */
	public int[] updateByIdBatchReq(String tableNamePrefix, List<?> list) {
		if (list == null || list.isEmpty()) {
			return new int[0];
		}
		int[] res = new int[list.size() - 1];
		for (int i = 0; i < list.size(); i++) {
			res[i] = updateTemplateById(tableNamePrefix, list.get(i).getClass());
		}
		return res;
	}

	/**
	 * 更新指定表(全表更新 )
	 * 
	 * @param tableNamePrefix 动态表前缀
	 * @param clazz
	 * @param param 参数
	 * @return
	 */
	public int updateAll(String tableNamePrefix, Class<?> clazz, Object param) {
		if (StringKit.isNotBlank(tableNamePrefix)) {
			sqlManager.setTableNamePrefix(tableNamePrefix);
		}
		return sqlManager.updateAll(clazz, param);
	}

	/**
	 * 
	 * delete from user where 1=1 and id= #id# 根据Id删除数据：支持联合主键
	 * 
	 * @param tableNamePrefix 动态表前缀
	 * @param clazz
	 * @param pkValue
	 * @return
	 * 
	 */
	public int deleteById(String tableNamePrefix, Class<?> clazz, Object pkValue) {
		if (StringKit.isNotBlank(tableNamePrefix)) {
			sqlManager.setTableNamePrefix(tableNamePrefix);
		}
		return sqlManager.deleteById(clazz, pkValue);
	}

	/**
	 * 插入方法
	 * 
	 * @param paramMap 属性名称:属性值
	 * @param clazz 表类
	 * @return
	 */
	public int insert(Map<String, Object> paramMap, Class<?> clazz) {
		int result = -1;
		Object bean = getObjFromMap(paramMap, clazz);
		if (bean == null) {
			logger.info("反射得到实体类为空！");
			return result;
		}
		if (paramMap.containsKey(MyBatisConstans.DYTABLE_KEY)) {
			sqlManager.setTableNamePrefix(paramMap.get(MyBatisConstans.DYTABLE_KEY).toString());
		}
		return this.insert("", bean);
	}

	/**
	 * 根据主键更新数据
	 * 
	 * @param paramMap 属性名称:属性值
	 * @param clazz 表类
	 * @return
	 */
	public int updateTemplateById(Map<String, Object> paramMap, Class<?> clazz) {
		int result = -1;
		Object bean = getObjFromMap(paramMap, clazz);
		if (bean == null) {
			logger.info("反射得到实体类为空！");
			return result;
		}
		if (paramMap.containsKey(MyBatisConstans.DYTABLE_KEY)) {
			sqlManager.setTableNamePrefix(paramMap.get(MyBatisConstans.DYTABLE_KEY).toString());
		}
		return this.updateTemplateById("", bean);
	}

	/**
	 * 复制Map到指定的实体类中
	 * 
	 * @param paramMap
	 * @param clazz
	 * @return
	 */
	public Object getObjFromMap(Map<String, Object> paramMap, Class<?> clazz) {
		Object bean = null;
		try {
			bean = clazz.newInstance();
			BeanUtils.populate(bean, paramMap);
		} catch (InstantiationException e) {
			logger.error("反射实例化时发生异常。", e);
		} catch (IllegalAccessException e) {
			logger.error("反射实例化时发生异常。", e);
		} catch (InvocationTargetException e) {
			logger.error("反射实例化时发生异常。", e);
		}
		return bean;
	}

	/**
	 * 执行查询类SQL文
	 * 
	 * @param p 原生SQL文
	 * @param clazz 返回的对象类
	 * @return
	 */
	public <T> List<T> execute(SQLReady p, Class<T> clazz) {
		return sqlManager.execute(p, clazz);
	}

	/**
	 * 执行更新类SQL文
	 * 
	 * @param p 原生SQL文
	 * @return
	 */
	public int executeUpdate(SQLReady p) {
		return sqlManager.executeUpdate(p);
	}
}
