package com.graduation.project.risk.project.web.form.home;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;

@Data
public class HomeSearchForm {

    @ApiModelProperty(value = "开始时间", example = "2019-04-08 15:23:43")
    private String startDate;

    @ApiModelProperty(value = "结束时间", example = "2019-04-09 15:23:43")
    private String stopDate;

    @ApiModelProperty(value = "用户户籍区域", example = "zhejiang")
    private String personProvince;

    //private String eventType;//事件类型

}
