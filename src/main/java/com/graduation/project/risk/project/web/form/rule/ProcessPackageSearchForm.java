package com.graduation.project.risk.project.web.form.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProcessPackageSearchForm {

    @ApiModelProperty(value = "业务Code",example = "现金贷")
    private String businessCode;

    @ApiModelProperty(value = "规则包Code",example = "ZHIMA")
    private String packageCode;

}
