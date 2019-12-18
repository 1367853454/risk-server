package com.graduation.project.risk.project.web.form.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class SysRoleForm {

    @ApiModelProperty(value = "角色名")
    @NotBlank(message = "name can not be blank")
    private String name;

}
