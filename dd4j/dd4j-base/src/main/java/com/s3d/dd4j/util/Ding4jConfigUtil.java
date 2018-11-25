package com.s3d.dd4j.util;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;
import com.s3d.dd4j.DingAccount;


public class Ding4jConfigUtil {
	public final static String CLASSPATH_PREFIX = "classpath:";
	private static ResourceBundle dingBundle;
	static {
		try {
			dingBundle = ResourceBundle.getBundle("ding4j");
		} catch (MissingResourceException e) {
			;
		}
	}

	private final static String DING4j_PREFIX = "ding4j";

	private static String wrapKeyName(String key) {
		if (!key.startsWith(DING4j_PREFIX)) {
			return String.format("%s.%s", DING4j_PREFIX, key);
		}
		return key;
	}

	/**
	 * 获取ding4j.properties文件中的key值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		String wrapKey = wrapKeyName(key);
		return System.getProperty(wrapKey, dingBundle.getString(wrapKey));
	}

	/**
	 * key不存在时则返回传入的默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(String key, String defaultValue) {
		String value = defaultValue;
		try {
			value = getValue(key);
			if (StringUtil.isBlank(value)) {
				value = defaultValue;
			}
		} catch (MissingResourceException e) {
			;
		} catch (NullPointerException e) {
			;
		}
		return value;
	}

	/**
	 * 判断属性是否存在[classpath:]如果存在则拼接项目路径后返回 一般用于文件的绝对路径获取
	 * 
	 * @param key
	 * @return
	 */
	public static String getClassPathValue(String key) {
		return getValue(key).replaceFirst(CLASSPATH_PREFIX, "");
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getClassPathValue(String key, String defaultValue) {
		String value = getValue(key, defaultValue);
		if(value.startsWith(CLASSPATH_PREFIX)){
			value = value.replaceFirst(CLASSPATH_PREFIX,
					"");
			
			URL uri = Ding4jConfigUtil.class.getClassLoader().getResource(value);
			value = uri.getPath();
		}
		return value;
	}

	public static DingAccount getDingAccount() {
		DingAccount account = null;
		try {
			account = JSON
					.parseObject(getValue("account"), DingAccount.class);
		} catch (NullPointerException e) {
			System.err
					.println("'ding4j.account' key not found in ding4j.properties.");
		} catch (MissingResourceException e) {
			System.err
					.println("'ding4j.account' key not found in ding4j.properties.");
		}
		return account;
	}
}
