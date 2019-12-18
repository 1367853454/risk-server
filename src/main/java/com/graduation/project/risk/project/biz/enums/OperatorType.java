package com.graduation.project.risk.project.biz.enums;

import org.apache.commons.lang3.StringUtils;

public enum  OperatorType {

    entry("entry", "entry"),

    remove("remove", "remove"),

    ;

    private String code;

    private String description;

    OperatorType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OperatorType convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (OperatorType type : values()) {
            if (StringUtils.equals(type.getCode(), code)) {
                return type;
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
