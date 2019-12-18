package com.graduation.project.risk.project.web.form.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RuleDeleteForm {

    @ApiModelProperty(value = "规则id")
    private Long ruleId;

}
