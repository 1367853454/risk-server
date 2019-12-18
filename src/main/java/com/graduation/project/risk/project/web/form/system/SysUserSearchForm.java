package com.graduation.project.risk.project.web.form.system;

import com.graduation.project.risk.common.base.entity.PageParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserSearchForm extends PageParams {

    @ApiModelProperty(value = "用户真实姓名",example = "admin")
    private String name;

    @ApiModelProperty(value = "用户状态",example = "one of stop、normal、deleted or all")
    private String status;

}
