package com.graduation.project.risk.project.biz.enums;

import com.graduation.project.risk.project.web.vo.KeyValueVO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum CommonStatus {

    normal("normal", "normal"),

    deleted("deleted","deleted"),

    stop("stop", "stop"),

    ;

    private String code;

    private String description;

    CommonStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CommonStatus convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (CommonStatus status : values()) {
            if (StringUtils.equals(status.getCode(), code)) {
                return status;
            }
        }
        return null;
    }

    public static List<KeyValueVO> list(){

        List<KeyValueVO> list = new ArrayList<>();

        for (CommonStatus status : values()){
            list.add(new KeyValueVO(status.getCode(), status.getDescription()));
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
