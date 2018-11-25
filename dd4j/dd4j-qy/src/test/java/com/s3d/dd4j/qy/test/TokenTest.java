package com.s3d.dd4j.qy.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.s3d.dd4j.exception.DingException;
import com.s3d.dd4j.model.DingAccount;
import com.s3d.dd4j.qy.token.DingTokenCreator;
import com.s3d.dd4j.setting.Ding4jSettings;
import com.s3d.dd4j.token.TokenManager;
import com.s3d.dd4j.util.Ding4jConfigUtil;

/**
 * token测试
 * @author sulta
 *
 */
public class TokenTest {

	protected TokenManager tokenManager;
	protected Ding4jSettings<DingAccount> settings;

	@Before
	public void setUp() {
		this.settings = new Ding4jSettings<DingAccount>(
				Ding4jConfigUtil.getDingAccount());
		tokenManager = new TokenManager(new DingTokenCreator(settings
				.getAccount().getId(), settings.getAccount().getSecret()),
				settings.getCacheStorager0());
	}

	@Test
	public void test() throws DingException {
		Assert.assertNotNull(tokenManager.getCache());
	}
}
