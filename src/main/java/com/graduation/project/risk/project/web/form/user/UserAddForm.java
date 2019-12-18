package com.graduation.project.risk.project.web.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class UserAddForm {

    @ApiModelProperty(value = "真实姓名")
    @NotBlank(message = "name can not be blank")
    private String name;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "account can not be blank")
    private String account;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "password can not be blank")
    private String password;

    @ApiModelProperty(value = "标记")
    private String remark;


}
