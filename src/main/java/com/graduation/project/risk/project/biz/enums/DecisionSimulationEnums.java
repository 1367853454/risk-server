package com.graduation.project.risk.project.biz.enums;

import com.graduation.project.risk.project.web.vo.KeyValueVO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum  DecisionSimulationEnums {

    PASS("PASS", "通过"),//通过
    REJECT("REJECT", "拒绝"),//拒绝
    REVIEW("REVIEW", "复议"),//复议


    ;
    private String code;

    private String description;

    DecisionSimulationEnums(String code, String description){
        this.code = code;
        this.description = description;
    }

    public static DecisionSimulationEnums convertFrom(String code){
        if (StringUtils.isBlank(code)){
            return null;
        }
        for (DecisionSimulationEnums enums : values()){
            if (StringUtils.equals(enums.getCode(),code)){
                return enums;
            }
        }
        return null;
    }

    public static List<KeyValueVO> list(){
        List<KeyValueVO> list = new ArrayList<>();
        for(DecisionSimulationEnums enums: values()){
            list.add(new KeyValueVO(enums.getCode(),enums.getDescription()));
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
