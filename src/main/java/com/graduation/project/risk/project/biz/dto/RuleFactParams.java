package com.graduation.project.risk.project.biz.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 规则传参
 */
@Data
public class RuleFactParams implements Serializable {

    //fact集合
    private List<Object> factObjectList = new ArrayList<>();
    //全局对象集合
    private Map<String, Object> globalMap = new HashMap<>();
    //是否全部执行（默认全部）
    private boolean executeAll = true;
    //根据名称过滤要执行的规则
    private String ruleName;


    public void addFact(Object object) {
        this.factObjectList.add(object);
    }

    public void setGlobal(String key, Object value) {
        this.globalMap.put(key, value);
    }


}
