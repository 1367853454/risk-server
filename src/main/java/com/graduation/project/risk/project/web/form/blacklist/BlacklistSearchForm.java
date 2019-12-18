package com.graduation.project.risk.project.web.form.blacklist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlacklistSearchForm {

    @ApiModelProperty(value = "手机号",example = "15858105555")
    private String phoneNumber;

    @ApiModelProperty(value = "身份证号",example = "332501199700001")
    private String idNumber;

}
