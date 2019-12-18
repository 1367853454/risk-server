package com.graduation.project.risk.project.web.vo.rule;

import lombok.Data;

import java.util.Date;

@Data
public class RulePackageVO {

    private Long packageId;

    private String businessName;

    private String packageCode;

    private String packageName;

    private String packageDescribe;

    private String packageVersion;

    private String publishStatus;

    private Date updateTime;
}
