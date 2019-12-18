package com.graduation.project.risk.project.web.form.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ChangePasswordForm {

    @NotBlank(message = "account can not be blank")
    @ApiModelProperty(value = "用户名")
    private String account;

    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "oldPassword can not be blank")
    private String olePassword;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "newPassword can not be blank")
    private String newPassword;

}
