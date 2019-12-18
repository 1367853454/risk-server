package com.graduation.project.risk.project.web.vo.decision;

import lombok.Data;

import java.util.Date;

@Data
public class DecisionSimulationVO {

    private Long processId;

    private String businessName;

    private String processName;

    private String processDescribe;

    private Date updateTime;

    private String businessCode;

    private String processCode;

    private String triggerLinkEnums;

}
