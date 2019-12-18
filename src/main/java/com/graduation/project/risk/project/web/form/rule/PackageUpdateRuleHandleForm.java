package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 规则结果变量
 */
@Data
public class PackageUpdateRuleHandleForm {

    @NotNull
    @ApiModelProperty(value = "结果对应的变量id",example = "1")
    private Long handleVariableId;

    @NotBlank(message = "handleCompare can not be blank")
    @ApiModelProperty(value = "结果比较符",example = "==")
    private String handleCompare;

    @NotBlank(message = "handleValue can not be blank")
    @ApiModelProperty(value = "结果值",example = "PASS")
    private String handleValue;


}
