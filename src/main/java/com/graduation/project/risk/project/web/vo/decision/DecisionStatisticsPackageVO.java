package com.graduation.project.risk.project.web.vo.decision;

import lombok.Data;

@Data
public class DecisionStatisticsPackageVO {

    private String result;

    private String packageName;

    private Integer ApplicantNumber;

    private Integer rejectCustomers;

    private Double rejectionRate;

}
