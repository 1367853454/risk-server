package com.graduation.project.risk.project.web.form.pp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PpSystemVariableInterfaceForm {

    @ApiModelProperty(value = "身份证")
    private String ktpNo;

    @ApiModelProperty(value = "设备唯一标识号")
    private String deviceId;

    @ApiModelProperty(value = "紧急联系人手机号")
    private List<String> comtactsMobile;

    @ApiModelProperty(value = "订单所填邮箱")
    private String email;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

}
