package com.graduation.project.risk.project.biz.enums;

import org.apache.commons.lang3.StringUtils;


public enum CommonPermission {

    menu("menu", "menu"),

    button("button", "button"),

    ;

    private String code;

    private String description;

    CommonPermission(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CommonPermission convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (CommonPermission status : values()) {
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
