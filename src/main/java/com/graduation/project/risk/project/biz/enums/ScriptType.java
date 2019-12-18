package com.graduation.project.risk.project.biz.enums;

import org.apache.commons.lang3.StringUtils;

public enum ScriptType {

    java("java", "java"),

    javascript("javascript", "javascript"),

    ;

    private String code;

    private String description;

    ScriptType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ScriptType convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (ScriptType status : values()) {
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
