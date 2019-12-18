package com.graduation.project.risk.project.biz.enums;

import com.graduation.project.risk.project.web.vo.KeyValueVO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum VariableType {

    basis("basis", "basis"),

    derivation("derivation", "derivation"),

    ;

    private String code;

    private String description;

    VariableType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static VariableType convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (VariableType status : values()) {
            if (StringUtils.equals(status.getCode(), code)) {
                return status;
            }
        }
        return null;
    }

    public static List<KeyValueVO> list() {
        List<KeyValueVO> list = new ArrayList<>();
        for (VariableType type : values()) {
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
