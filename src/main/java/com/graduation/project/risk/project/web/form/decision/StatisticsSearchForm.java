package com.graduation.project.risk.project.web.form.decision;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StatisticsSearchForm {

    @ApiModelProperty(value = "开始时间",example = "2019-04-08 15:23:43")
    private String startDate;

    @ApiModelProperty(value = "结束时间",example = "2019-04-08 15:23:43")
    private String endDate;

//    private String eventType;//事件类型
//
//    private String organizationName;//机构名称
//
//    private String area;//区域
//
//    private String businessCode;

    @ApiModelProperty(value = "规则包Code",example = "ZHIMA")
    private String packageCode;

    @ApiModelProperty(value ="流程Code",example = "chushen")
    private String processCode;
}
