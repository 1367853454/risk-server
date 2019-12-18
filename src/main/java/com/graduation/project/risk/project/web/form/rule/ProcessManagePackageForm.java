package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * request params for manage rule process of package info
 */
@Data
public class ProcessManagePackageForm {

    @NotBlank(message = "packageCode can not be blank")
    @ApiModelProperty(value = "规则包Code",example = "ZHIMA")
    private String packageCode;


    @NotNull
    @ApiModelProperty(value = "规则包排序",example = "1")
    private Integer orderedNumber;

}

