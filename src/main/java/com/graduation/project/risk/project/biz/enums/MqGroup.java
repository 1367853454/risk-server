package com.graduation.project.risk.project.biz.enums;

import org.apache.commons.lang3.StringUtils;

public enum MqGroup {

	DEFAULT("DEFAULT", "default group"),

	;

	private String code;

	private String description;

	MqGroup(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public static MqGroup convertFrom(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		for (MqGroup group : values()) {
			if (StringUtils.equals(group.getCode(), code)) {
				return group;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
