package com.s3d.dd4j.exception;

import com.s3d.dd4j.util.DingErrorUtil;
import com.s3d.dd4j.util.StringUtil;

/**
 * 调用钉钉接口抛出的异常
 * @author sulta
 *
 */
public class DingException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3097489735090873356L;
	private String code;
	private String desc;

	public DingException(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public DingException(String desc) {
		this.code = "-1";
		this.desc = desc;
	}

	public DingException(Throwable e) {
		super(e);
	}

	public DingException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getErrorCode() {
		return code;
	}

	public String getErrorDesc() {
		return desc;
	}

	public String getErrorText() {
		return DingErrorUtil.getText(code);
	}

	@Override
	public String getMessage() {
		if (StringUtil.isNotBlank(code)) {
			StringBuilder buf = new StringBuilder();
			buf.append(code).append(" >> ").append(desc);
			String text = getErrorText();
			if (StringUtil.isNotBlank(text)) {
				buf.append(" >> ").append(text);
			}
			return buf.toString();
		} else {
			return super.getMessage();
		}
	}
}
