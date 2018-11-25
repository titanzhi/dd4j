package com.s3d.dd4j.cache;

import com.s3d.dd4j.exception.DingException;

/**
 * Cache的创建
 *
 * @className CacheCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年5月24日
 * @since JDK 1.6
 * @see
 */
public interface CacheCreator<T extends Cacheable> {
	/**
	 * CacheKey
	 *
	 * @return
	 */
	public String key();

	/**
	 * 创建Cache
	 *
	 * @throws DingException
	 * @return 缓存对象
	 */
	public T create() throws DingException;
}
