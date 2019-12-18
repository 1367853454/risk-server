package com.graduation.project.risk.project.biz.enums;

import org.apache.commons.lang3.StringUtils;

public enum CommonInd {

    yes("yes", "yes"),

    no("no", "no"),

    ;

    private String code;

    private String description;

    CommonInd(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CommonInd convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (CommonInd Ind : values()) {
            if (StringUtils.equals(Ind.getCode(), code)) {
                return Ind;
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
