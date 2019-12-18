package com.graduation.project.risk.project.web.form.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class UserInfoForm {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "account can not be blank")
    private String account;

}
