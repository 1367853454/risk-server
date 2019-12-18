package com.graduation.project.risk.project.web.form.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * request params for update rule package
 */
@Data
public class PackageUpdateForm {

    @NotBlank(message = "packageCode can not be blank")
    @ApiModelProperty(value = "规则包Code",example = "ZHIMA")
    private String packageCode;

    @NotBlank(message = "packageVersion can not be blank")
    @ApiModelProperty(value = "规则包版本",example = "1.0.1")
    private String packageVersion;

    private List<PackageUpdateRuleForm> packageUpdateRuleForms;



}
