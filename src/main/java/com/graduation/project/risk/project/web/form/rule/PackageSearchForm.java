package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class PackageSearchForm {

    @NotBlank
    @ApiModelProperty(value = "规则包Code")
    private String packageCode;

    @ApiModelProperty(value = "规则包版本")
    @NotBlank
    private String version;

}
