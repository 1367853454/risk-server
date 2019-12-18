package com.graduation.project.risk.project.biz.enums;

import org.apache.commons.lang3.StringUtils;

public enum  CommonType {

    black("black", "black"),

    gray("gray", "gray"),

    ;

    private String code;

    private String description;

    CommonType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CommonType convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (CommonType type : values()) {
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
