package com.graduation.project.risk.project.web.form.system;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysRolePermissionForm {

//    @NotBlank(message = "name can not be blank")
//    private String name;

    @NotNull
    @ApiModelProperty(value = "权限id")
    private Long roleId;

}
