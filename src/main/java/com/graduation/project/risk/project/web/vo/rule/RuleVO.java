package com.graduation.project.risk.project.web.vo.rule;

import lombok.Data;

@Data
public class RuleVO {

    private Long ruleId;

    private String ruleName;

    private String ruleDescribe;

    private String ruleTips;

    private String executeCondition;

    private String variableName;

    private String conditionCompare;

    private String conditionCompareValue;

    private String resultVariableName;

    private String resultCompare;

    private String resultCompareValue;

    private String status;

}
