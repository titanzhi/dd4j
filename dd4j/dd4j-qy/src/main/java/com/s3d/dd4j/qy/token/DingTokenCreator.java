package com.s3d.dd4j.qy.token;

import com.alibaba.fastjson.JSONObject;
import com.s3d.dd4j.exception.DingException;
import com.s3d.dd4j.http.ding.DingResponse;
import com.s3d.dd4j.model.Token;
import com.s3d.dd4j.qy.type.URLConsts;
import com.s3d.dd4j.token.TokenCreator;

/**
 * 企业TOKEN创建
 *
 * @see <a href=
 *      "https://open-doc.dingtalk.com/microapp/serverapi2/hfoogs">
 *      企业开发账号授权</a>
 * @see com.s3d.dd4j.model.Token
 */
public class DingTokenCreator extends TokenCreator {

	private final String corpid;
	private final String corpsecret;

	/**
	 *
	 * @param corpid
	 *            企业号ID
	 * @param corpsecret
	 *            企业号secret
	 */
	public DingTokenCreator(String corpid, String corpsecret) {
		this.corpid = corpid;
		this.corpsecret = corpsecret;
	}

	@Override
	public String key0() {
		return String.format("qy_token_%s", corpid);
	}

	@Override
	public Token create() throws DingException {
		String tokenUrl = String.format(URLConsts.ASSESS_TOKEN_URL, corpid,
				corpsecret);
		DingResponse response = dingExecutor.get(tokenUrl);
		JSONObject result = response.getAsJson();
		return new Token(result.getString("access_token"),
				result.getLongValue("expires_in") * 1000l);
	}
}
