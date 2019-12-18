package com.graduation.project.risk.project.biz.enums;

import com.graduation.project.risk.project.web.vo.KeyValueVO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum TriggerLinkEnums {

    firstInspection("firstInspection", "初审"),
    review("review", "复审"),
    finalInstance("finalInstance", "终审"),


    ;

    private String code;

    private String description;

    TriggerLinkEnums(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static TriggerLinkEnums convertFrom(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (TriggerLinkEnums Ind : values()) {
            if (StringUtils.equals(Ind.getCode(), code)) {
                return Ind;
            }
        }
        return null;
    }
    public static List<KeyValueVO> list(){

        List<KeyValueVO> list = new ArrayList<>();

        for(TriggerLinkEnums triggerLinkEnums : values()){
            list.add(new KeyValueVO(triggerLinkEnums.getDescription(),triggerLinkEnums.getCode()));
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
