package com.graduation.project.risk.project.web.vo.decision;

import lombok.Data;

import java.util.List;

@Data
public class DecisionStatisticsVO {

    private String packageName;

    private String result;

    private String ruleName;

    private String updateTime;

    private Integer rejectCustomers;

    private Double rejectionRate;

    private Double percentageOfRejection;

}
