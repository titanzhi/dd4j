package com.s3d.dd4j.exception;

/**
 * 钉钉异常
 * @author sulta
 *
 */
public class DingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1966852630418527201L;
	
	private String errorCode;
	private String errorMsg;

	public DingException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public DingException(String errorMsg) {
		this.errorCode = "-1";
		this.errorMsg = errorMsg;
	}

	public DingException(Exception e) {
		super(e);
	}

	public DingException(String errorMsg, Exception e) {
		super(e);
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public String getMessage() {
		return this.errorCode + "," + this.errorMsg;
	}
}
