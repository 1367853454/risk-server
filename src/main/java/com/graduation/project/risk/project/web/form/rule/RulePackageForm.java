package com.graduation.project.risk.project.web.form.rule;

import com.graduation.project.risk.common.base.entity.PageParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RulePackageForm extends PageParams {

    @ApiModelProperty(value = "业务Code",example = "现金贷")
    private String businessCode;

    @ApiModelProperty(value = "规则包Code",example = "ZHIMA")
    private String packageCode;

    @ApiModelProperty(value = "规则包名称",example = "规则包1")
    private String packageName;

}
