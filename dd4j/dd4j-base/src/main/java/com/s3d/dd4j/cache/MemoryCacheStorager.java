package com.s3d.dd4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用内存保存缓存对象(不推荐使用)
 * @author sulta
 *
 * @param <T>
 */
public class MemoryCacheStorager<T extends Cacheable> implements
		CacheStorager<T> {

	private final Map<String, T> CONMAP;

	public MemoryCacheStorager() {
		this.CONMAP = new ConcurrentHashMap<String, T>();
	}

	@Override
	public T lookup(String cacheKey) {
		T cache = this.CONMAP.get(cacheKey);
		if (cache != null) {
			if ((cache.getCreateTime() + cache.getExpires() - CUTMS) > System
					.currentTimeMillis()) {
				return cache;
			}
		}
		return null;
	}

	@Override
	public void caching(String cacheKey, T cache) {
		this.CONMAP.put(cacheKey, cache);
	}

	@Override
	public T evict(String cacheKey) {
		return this.CONMAP.remove(cacheKey);
	}

	@Override
	public void clear() {
		this.CONMAP.clear();
	}
}
