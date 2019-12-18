package com.graduation.project.risk.project.biz.enums;

import org.apache.commons.lang3.StringUtils;

public enum BusinessType {

    pp("100", "pp"),

    koperasi("200", "koperasi"),

    ;

    private String code;

    private String description;

    BusinessType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static BusinessType convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (BusinessType status : values()) {
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
