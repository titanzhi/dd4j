package com.s3d.dd4j.token;

import com.s3d.dd4j.cache.CacheCreator;
import com.s3d.dd4j.http.ding.DingRequestExecutor;
import com.s3d.dd4j.model.Token;

/**
 * Token的创建
 * @author sulta
 *
 */
public abstract class TokenCreator implements CacheCreator<Token> {

	/**
	 * 缓存KEY前缀
	 */
	public final static String CACHEKEY_PREFIX = "ding4j_";

	protected final DingRequestExecutor dingExecutor;

	public TokenCreator() {
		this.dingExecutor = new DingRequestExecutor();
	}

	/**
	 * 缓存key:附加key前缀
	 *
	 * @return
	 */
	@Override
	public String key() {
		return String.format("%s%s", CACHEKEY_PREFIX, key0());
	}

	/**
	 * 返回缓存KEY的名称:建议接口类型命名 如 mp_token_{appid}
	 *
	 * @return
	 */
	public abstract String key0();
}
