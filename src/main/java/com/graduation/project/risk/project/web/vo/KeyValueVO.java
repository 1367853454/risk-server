package com.graduation.project.risk.project.web.vo;

public class KeyValueVO {

	private String key;

	private String value;

	public KeyValueVO() {
	}

	public KeyValueVO(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
