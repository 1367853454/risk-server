package com.graduation.project.risk.project.web.form.blacklist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class AdduserIntoBlacklistForm {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "name can not be blank")
    private String name;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "mobile can not be blank")
    private String mobile;

    @ApiModelProperty(value = "身份证")
    @NotBlank(message = "idcard can not be blank")
    private String idcard;

//    private Long OperatorId;

    @ApiModelProperty(value = "内部黑名单原因id")
    private Long blacklistSourceReasonId;
}
