package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 规则条件变量
 */
@Data
public class PackageUpdateRuleConditionForm {

    @NotNull
    @ApiModelProperty(value = "规则对应的变量id",example = "1")
    private Long variableId;

    @NotBlank(message = "compare can not be blank")
    @ApiModelProperty(value = "规则比较符",example = "<")
    private String compare;

    @NotBlank(message = "value can not be blank")
    @ApiModelProperty(value = "该条规则的值",example = "50")
    private String value;


}
