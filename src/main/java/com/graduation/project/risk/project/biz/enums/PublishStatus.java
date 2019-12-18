package com.graduation.project.risk.project.biz.enums;

import org.apache.commons.lang3.StringUtils;

public enum PublishStatus {

    published("published", "published"),

    unpublished("unpublished", "unpublished"),


    ;

    private String code;

    private String description;

    PublishStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static PublishStatus convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (PublishStatus status : values()) {
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
