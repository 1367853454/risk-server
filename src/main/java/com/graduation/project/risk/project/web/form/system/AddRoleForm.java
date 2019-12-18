package com.graduation.project.risk.project.web.form.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class AddRoleForm {

    @NotBlank(message = "name can not be blank")
    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;
}
