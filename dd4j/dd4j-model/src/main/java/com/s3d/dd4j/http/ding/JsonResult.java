package com.s3d.dd4j.http.ding;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 返回JSON格式
 * @author sulta
 *
 */
public class JsonResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1305218612441021745L;
	@JSONField(name = "errcode")
	private int code;
	@JSONField(name = "errmsg")
	private String desc;
	private String text;

	public JsonResult() {
		this.desc = "";
		this.text = "";
	}

	public JsonResult(int code, String desc, String text) {
		this.code = code;
		this.desc = desc;
		this.text = text;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "JsonResult [code=" + code + ", desc=" + desc + ", text=" + text
				+ "]";
	}
}
