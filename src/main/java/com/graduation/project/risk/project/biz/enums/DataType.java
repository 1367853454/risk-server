package com.graduation.project.risk.project.biz.enums;

import com.graduation.project.risk.project.web.vo.KeyValueVO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 变量 -- 数据类型
 **/
public enum DataType {

	_integer("integer", "integer"),

	_string("string", "string"),

	_number("number", "number"),

	_boolean("boolean", "boolean"),

	_date("date", "date"),

	;

	private String code;

	private String description;

	DataType(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public static DataType convertFrom(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		for (DataType type : values()) {
			if (StringUtils.equals(type.getCode(), code)) {
				return type;
			}
		}
		return null;
	}

	public static List<KeyValueVO> list() {

		List<KeyValueVO> list = new ArrayList<>();

		for (DataType type : values()) {
			list.add(new KeyValueVO(type.getCode(), type.getDescription()));
		}

		return list;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
