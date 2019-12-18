package com.graduation.project.risk.project.biz.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 规则执行结果
 */
@Data
public class RuleFactResult implements Serializable {

    //规则执行中需要保存的数据
    private Map<String, Object> map = new HashMap<>();

    private String orderCode;


}
