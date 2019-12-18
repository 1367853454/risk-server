package com.graduation.project.risk.project.biz.enums;

import org.apache.commons.lang3.StringUtils;

public enum IfCanNull {

    yes("yes", "yes"),

    no("no", "no"),

    ;

    private String code;

    private String description;

    IfCanNull(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static IfCanNull convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (IfCanNull status : values()) {
            if (StringUtils.equals(status.getCode(), code)) {
                return status;
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
