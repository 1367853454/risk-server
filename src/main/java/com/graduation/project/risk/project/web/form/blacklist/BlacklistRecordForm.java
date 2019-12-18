package com.graduation.project.risk.project.web.form.blacklist;

import com.graduation.project.risk.common.base.entity.PageParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlacklistRecordForm extends PageParams {

    @ApiModelProperty(value = "手机号码",example = "15858105555")
    private String phoneNumber;

    @ApiModelProperty(value = "身份证",example = "332501199700001")
    private String idNumber;

    @ApiModelProperty(value = "操作员id",example = "1")
    private String operatorId;

    @ApiModelProperty(value = "操作人",example = "admin")
    private String operator;

    @ApiModelProperty(value = "操作",example = "entry")
    private String behavior;

    @ApiModelProperty(value = "开始时间",example = "2019-04-04 10:12:07")
    private String beginTime;

    @ApiModelProperty(value = "结束时间",example = "2019-04-04 16:47:21")
    private String endTime;



}
