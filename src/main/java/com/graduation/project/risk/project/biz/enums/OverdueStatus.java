package com.graduation.project.risk.project.biz.enums;

import com.graduation.project.risk.common.core.biz.StringUtil;
import org.apache.commons.lang3.StringUtils;

public enum  OverdueStatus {

    overdue("overdue","overdue"),
    notOverdue("notOverdue","notOverdue"),
    overduePayment("overduePayment","overduePayment");

    private String code;

    private String description;

    OverdueStatus(String code, String description){
        this.code = code;
        this.description = description;
    }

    public static OverdueStatus convertFrom(String code) {
        if (StringUtil.isBlank(code)) {
            return null;
        }
        for (OverdueStatus status : values()) {
            if (StringUtils.equals(status.getCode(),code)){
                return status;
            }
        }
        return null;
    }

    public String getCode(){
        return code;
    }

    public String getDescription(){
        return description;
    }


}
