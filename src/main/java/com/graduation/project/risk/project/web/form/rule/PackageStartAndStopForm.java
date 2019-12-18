package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


@Data
public class PackageStartAndStopForm {

    @NotNull
    @ApiModelProperty(value = "规则id")
    private Long ruleId ;

    @NotBlank
    @ApiModelProperty(value = "操作,on,off判断用的是off",example = "off")
    private String operation;

}
