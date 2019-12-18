package com.graduation.project.risk.project.web.form.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PackageVersionForm {

    @ApiModelProperty(value = "业务Code",example = "200")
    private String businessCode;

    @ApiModelProperty(value = "规则包Code",example = "ZHIMA")
    private String packageCode;

}
