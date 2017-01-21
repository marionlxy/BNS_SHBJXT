package com.krm.voteplateform.common.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.krm.voteplateform.common.spring.utils.SpringContextHolder;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class CacheUtils {

	private static final EhCacheCacheManager cacheManager = SpringContextHolder.getBean("cacheManager");;

	/**
	 * 添加到缓存
	 * 
	 * @param cacheName 缓存名称
	 * @param key 缓存Key
	 * @param value 缓存值
	 */
	public static <T> void put(String cacheName, String key, T value) {
		if (StringUtils.isNotBlank(key)) {
			getCache(cacheName).put(key, value);
		}
	}

	public static <T> void put(String cacheName, String key, T value, int timeToIdleSeconds) {
		Ehcache cache = getEhcache(cacheName);
		Element element = new Element(key, value);
		element.setTimeToIdle(timeToIdleSeconds);
		cache.put(element);
	}

	@SuppressWarnings("unchecked")
	public static List<String> getnonExpiredKeys(String cacheName) {
		Ehcache cache = getEhcache(cacheName);
		return cache.getKeysWithExpiryCheck();
	}

	/**
	 * 删除缓存中的信息
	 * 
	 * @param cacheName 缓存名称
	 * @param key 缓存中的Key
	 */
	public static void evict(String cacheName, String key) {
		if (StringUtils.isNotBlank(key)) {
			getCache(cacheName).evict(key);
		}
	}

	public static boolean remove(String cacheName, String key) {
		Ehcache cache = getEhcache(cacheName);
		return cache.remove(key);
	}

	/**
	 * 清空某一个缓存，全部清除
	 * 
	 * @param cacheName
	 * @param key
	 */
	public static void clear(String cacheName) {
		if (getCache(cacheName) != null) {
			getCache(cacheName).clear();
		}
	}

	/**
	 * 取得缓存中的值
	 * 
	 * @param cacheName
	 * @param key
	 * @return 取得缓存总的值，若无值，则返回<code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String cacheName, String key) {
		T value = null;
		if (StringUtils.isNotBlank(key)) {
			ValueWrapper val = getCache(cacheName).get(key);
			if (val != null) {
				value = (T) val.get();
			}
		}
		return value;
	}

	/**
	 * 是否存在key中的缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @return false 不存在 true:存在
	 */
	public static boolean isCacheByKey(String cacheName, String key) {
		ValueWrapper val = getCache(cacheName).get(key);
		return val == null ? false : true;
	}

	// 通过spring得到缓存管理对象
	public static Cache getCache(String cacheName) {
		return cacheManager.getCache(cacheName);
	}

	// 底层的ehcache的缓存管理对象
	public static Ehcache getEhcache(String cacheName) {
		Ehcache cache = null;
		if (cacheManager != null) {
			cache = cacheManager.getCacheManager().getCache(cacheName);
		} else {
			throw new NullPointerException("spring的管理对象cacheManager是null");
		}
		return cache;
	}

}
