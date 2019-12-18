package com.graduation.project.risk.project.web.vo.rule;

import lombok.Data;

import java.util.Date;

@Data
public class RuleProcessVO {

    private Long processId;

    private String businessName;

    private String processCode;

    private String processName;

    private String processDescribe;

    private String triggerLink; //触发环节

    private Date updateTime;

}
