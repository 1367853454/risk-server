package com.graduation.project.risk.project.web.form.system;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import com.graduation.project.risk.project.biz.enums.CommonPermission;

@Data
public class MenuForm {

    @NotNull
    @ApiModelProperty(value = "父id")
    private Long parent_id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "name can not be blank")
    private String name;

    @ApiModelProperty(value = "链接")
    private String href;

    @NotNull
    @ApiModelProperty(value = "类别")
    private CommonPermission type;

    @ApiModelProperty(value = "权限")
    @NotBlank(message = "permission can not be blank")
    private String permission;
}
