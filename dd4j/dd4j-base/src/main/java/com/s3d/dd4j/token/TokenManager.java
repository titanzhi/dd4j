package com.s3d.dd4j.token;

import com.s3d.dd4j.cache.CacheManager;
import com.s3d.dd4j.cache.CacheStorager;
import com.s3d.dd4j.exception.DingException;
import com.s3d.dd4j.model.Token;

/**
 * 对token的缓存获取
 *
 * @className TokenManager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月12日
 * @since JDK 1.6
 * @see TokenCreator
 * @see com.s3d.dd4j.cache.CacheStorager
 */
public class TokenManager extends CacheManager<Token> {

	/**
	 *
	 * @param tokenCreator
	 *            负责微信各种token的创建
	 * @param cacheStorager
	 *            负责token的存储
	 */
	public TokenManager(TokenCreator tokenCreator,
			CacheStorager<Token> cacheStorager) {
		super(tokenCreator, cacheStorager);
	}

	/**
	 * 获取token字符串
	 *
	 * @return
	 * @throws DingException
	 */
	public String getAccessToken() throws DingException {
		return super.getCache().getAccessToken();
	}
}
