package com.graduation.project.risk.project.web.vo.decision;

import lombok.Data;

@Data
public class DecisionStatisticsFunnelVO {

    private String result;

    private String ordered;

    private String packageName;

    private Double rejectCustomers;

    private Double conversionRate;

}
