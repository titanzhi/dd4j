package com.s3d.dd4j.cache;

import com.s3d.dd4j.exception.DingException;

/**
 * 缓存管理类
 * @author sulta
 *
 * @param <T>
 */
public class CacheManager<T extends Cacheable> {
	protected final CacheCreator<T> cacheCreator;
	protected final CacheStorager<T> cacheStorager;

	public CacheManager(CacheCreator<T> cacheCreator,
			CacheStorager<T> cacheStorager) {
		this.cacheCreator = cacheCreator;
		this.cacheStorager = cacheStorager;
	}

	/**
	 * 获取缓存对象
	 *
	 * @return 缓存对象
	 * @throws DingException
	 */
	public T getCache() throws DingException {
		String cacheKey = cacheCreator.key();
		T cache = cacheStorager.lookup(cacheKey);
		if (cache == null) {
			cache = cacheCreator.create();
			cacheStorager.caching(cacheKey, cache);
		}
		return cache;
	}

	/**
	 * 刷新缓存对象
	 *
	 * @return 缓存对象
	 * @throws DingException
	 */
	public T refreshCache() throws DingException {
		String cacheKey = cacheCreator.key();
		T cache = cacheCreator.create();
		cacheStorager.caching(cacheKey, cache);
		return cache;
	}

	/**
	 * 移除缓存
	 *
	 * @return 被移除的缓存对象
	 */
	public T evictCache() {
		String cacheKey = cacheCreator.key();
		return cacheStorager.evict(cacheKey);
	}

	/**
	 * 清除所有的缓存(<font color="red">请慎重</font>)
	 */
	public void clearCache() {
		cacheStorager.clear();
	}
}
