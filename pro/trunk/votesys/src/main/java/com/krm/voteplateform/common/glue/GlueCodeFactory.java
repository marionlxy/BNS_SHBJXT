package com.krm.voteplateform.common.glue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import com.krm.voteplateform.common.glue.handler.IGlueHandler;
import com.krm.voteplateform.common.glue.loader.GlueTextLoader;
import com.krm.voteplateform.common.glue.model.PtDynamicSource;

import groovy.lang.GroovyClassLoader;

public class GlueCodeFactory implements ApplicationContextAware {
	private static Logger logger = LoggerFactory.getLogger(GlueCodeFactory.class);
	/**
	 * groovy class loader
	 */
	private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

	/**
	 * glue cache timeout / second
	 */
	private long cacheTimeout = 5000;

	public void setCacheTimeout(long cacheTimeout) {
		this.cacheTimeout = cacheTimeout;
	}

	private GlueTextLoader glueTextLoader;

	/**
	 * @param glueTextLoader the glueTextLoader to set
	 */
	public void setGlueTextLoader(GlueTextLoader glueTextLoader) {
		this.glueTextLoader = glueTextLoader;
	}

	public static boolean isActive() {
		return GlueCodeFactory.glueCodeFactory.glueTextLoader != null;
	}

	public static GlueTextLoader getNowGlueTextLoader() {
		return GlueCodeFactory.glueCodeFactory.glueTextLoader;
	}

	// ----------------------------- spring support -----------------------------
	private static GlueCodeFactory glueCodeFactory;
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		GlueCodeFactory.applicationContext = applicationContext;
		GlueCodeFactory.glueCodeFactory = (GlueCodeFactory) applicationContext.getBean("glueCodeFactory");
	}

	// // load instance, singleton
	private static final ConcurrentHashMap<String, IGlueHandler> glueInstanceMap = new ConcurrentHashMap<String, IGlueHandler>();
	private static final ConcurrentHashMap<String, Long> glueCacheMap = new ConcurrentHashMap<String, Long>();

	// async clear cache (异步刷新缓存，避免缓存雪崩)
	private static LinkedBlockingQueue<String> freshCacheQuene = new LinkedBlockingQueue<String>();
	private static ExecutorService freshCacheWorder = Executors.newCachedThreadPool();

	public IGlueHandler loadInstance(String id) throws Exception {
		if (id == null || id.trim().length() == 0) {
			return null;
		}
		IGlueHandler instance = glueInstanceMap.get(id);
		if (instance == null) {
			instance = loadNewInstance(id);
			if (instance == null) {
				throw new IllegalArgumentException(">>>>>>>>>>> glue, 加载实例为空");
			}
			glueInstanceMap.put(id, instance);
			glueCacheMap.put(id, cacheTimeout == -1 ? -1 : (System.currentTimeMillis() + cacheTimeout));
			logger.info(">>>>>>>>>>>> glue, 加载实例成功,id:{}", id);
		} else {
			Long instanceTim = glueCacheMap.get(id);
			boolean ifValid = true;// 是否为有效标志
			if (instanceTim == null) {// 无缓存
				ifValid = false;// 无效缓存
			} else {
				if (instanceTim.intValue() == -1) {// 有效
					ifValid = true;
				} else if (System.currentTimeMillis() > instanceTim) {// 超过设置的缓存时间
					ifValid = false;// 无效缓存
				}
			}
			if (!ifValid) {// 若为无效缓存
				freshCacheQuene.add(id);// 加入刷新缓存队列
				// 缓存时间临时设置为-1，永久生效，避免并发情况下多次推送异步刷新队列；
				glueCacheMap.put(id, Long.valueOf(-1));
			}
		}
		return instance;
	}

	// load new instance, prototype
	private IGlueHandler loadNewInstance(String id) throws Exception {
		if (id == null || id.trim().length() == 0) {
			return null;
		}
		PtDynamicSource load = glueTextLoader.load(id);
		String codeSource = load != null ? load.getJavaCode() : null;
		if (codeSource != null && codeSource.trim().length() > 0) {
			Class<?> clazz = groovyClassLoader.parseClass(codeSource);
			if (clazz != null) {
				Object instance = clazz.newInstance();
				if (instance != null) {
					if (instance instanceof IGlueHandler) {
						this.injectService(instance);
						return (IGlueHandler) instance;
					} else {
						throw new IllegalArgumentException(
								">>>>>>>>>>> glue, 创建新实例错误, " + "不能将类[" + instance.getClass() + "] 强转为IHandler");
					}
				}
			}
		}
		throw new IllegalArgumentException(">>>>>>>>>>> glue, 新实例创建失败，实例为空");
	}

	/**
	 * 自动注入服务
	 * 
	 * @param instance
	 */
	private void injectService(Object instance) {
		if (instance == null) {
			return;
		}

		Field[] fields = instance.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			Object fieldBean = null;
			// with bean-id, bean could be found by both @Resource and @Autowired, or bean could
			// only be found by @Autowired
			if (AnnotationUtils.getAnnotation(field, Resource.class) != null) {
				try {
					fieldBean = applicationContext.getBean(field.getName());
				} catch (Exception e) {
					// ignore exception
				}
				if (fieldBean == null) {
					fieldBean = applicationContext.getBean(field.getType());
				}
			} else if (AnnotationUtils.getAnnotation(field, Autowired.class) != null) {
				fieldBean = applicationContext.getBean(field.getType());
			}

			if (fieldBean != null) {
				field.setAccessible(true);
				try {
					field.set(instance, fieldBean);
				} catch (IllegalArgumentException e) {
					logger.error("反射异常", e);
				} catch (IllegalAccessException e) {
					logger.error("反射异常", e);
				}
			}
		}
	}

	static {
		freshCacheWorder.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					String id = null;
					try {
						// take glue need refresh
						id = freshCacheQuene.take();
					} catch (Exception e) {
						logger.error("", e);
					}
					// refresh
					if (id != null && id.trim().length() > 0 && glueInstanceMap.get(id) != null) {
						IGlueHandler instance = null;
						try {
							instance = GlueCodeFactory.glueCodeFactory.loadNewInstance(id);
						} catch (Exception e) {
							logger.error("", e);
						}
						if (instance != null) {
							glueInstanceMap.put(id, instance);
							glueCacheMap.put(id, GlueCodeFactory.glueCodeFactory.cacheTimeout == -1 ? -1
									: (System.currentTimeMillis() + GlueCodeFactory.glueCodeFactory.cacheTimeout));
							logger.warn(">>>>> glue, async fresh cache by new instace success, id:{}", id);
						} else {
							glueInstanceMap.remove(id);
							glueCacheMap.remove(id);
							logger.warn(
									">>>>>> glue, async fresh cache by new instace fail, old instance removed, id:{}",
									id);
						}
					}
				}
			}
		});
	}

	// ----------------------------- util -----------------------------
	/**
	 * 根据ID取得代码后，运行实例的handler方法
	 * 
	 * @param id 动态代码表主键
	 * @param params 实际处理的参数列表
	 * @return
	 * @throws Exception
	 */
	public static String glue(String id, Object... params) throws Exception {
		IGlueHandler loadInstance = GlueCodeFactory.glueCodeFactory.loadInstance(id);
		if (loadInstance == null) {
			return null;
		}
		return loadInstance.handler(params);
	}

	/**
	 * 清空缓存
	 * 
	 * @param id
	 */
	public static void clearCache(String id) {
		GlueCodeFactory.freshCacheQuene.add(id);
	}

	/**
	 * 删除缓存
	 * 
	 * @param id
	 */
	public static void deleteCach(String id) {
		glueInstanceMap.remove(id);
		glueCacheMap.remove(id);
	}

}
