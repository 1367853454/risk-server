package com.graduation.project.risk.project.web.form.decision;

import com.graduation.project.risk.common.base.entity.PageParams;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderSearchForm extends PageParams {

    @NotNull
    @ApiModelProperty(value = "开始时间",example = "2019-04-08 15:23:43")
    private String startDate;

    @NotNull
    @ApiModelProperty(value = "结束时间",example = "2019-04-08 15:23:43")
    private String endDate;

    @ApiModelProperty(value = "数据类型",example = "PASS")
    private String dataType;

}
