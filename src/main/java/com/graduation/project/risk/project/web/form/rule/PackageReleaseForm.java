package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class PackageReleaseForm {

    @NotNull
    @ApiModelProperty(value = "规则包id",example = "1")
    private Long packageId;

    @NotBlank
    @ApiModelProperty(value = "规则包版本号", example = "1.0.0")
    private String packageVersion;


}
