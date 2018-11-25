package com.s3d.dd4j;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;


/**
 * 账号信息
 * @author sulta
 *
 */
public class DingAccount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7367541616723453719L;
	/**
	 * 唯一的身份标识
	 */
	private String id;
	/**
	 * 调用接口的密钥
	 */
	private String secret;

	@JSONCreator
	public DingAccount(@JSONField(name = "id") String id,
			@JSONField(name = "secret") String secret) {
		this.id = id;
		this.secret = secret;
	}

	public String getId() {
		return id;
	}

	public String getSecret() {
		return secret;
	}

	@Override
	public String toString() {
		return "id=" + id + ", secret=" + secret;
	}
}
