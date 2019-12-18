package com.graduation.project.risk.project.web.form.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * request params for add rule package
 */
@Data
public class PackageAddForm {

    @NotBlank(message = "businessCode can not be blank")
    @ApiModelProperty(value = "业务Code",example = "100")
    private String businessCode;

    @NotBlank(message = "packageName can not be blank")
    @ApiModelProperty(value = "规则包名称",example = "芝麻分")
    private String packageName;

    @NotBlank(message = "packageCode can not be blank")
    @ApiModelProperty(value = "规则包Code",example = "ZHIMA")
    private String packageCode;

    @ApiModelProperty(value = "描述",example = "zhima sorce")
    private String description;


}
