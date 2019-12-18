package com.graduation.project.risk.project.web.form.system;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EditRolePermissiomForm {

    @NotNull
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @NotNull
    @ApiModelProperty(value = "权限id")
    private List<Long> PermissionId;

}
