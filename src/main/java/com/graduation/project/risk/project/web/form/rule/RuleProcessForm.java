package com.graduation.project.risk.project.web.form.rule;

import com.graduation.project.risk.common.base.entity.PageParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RuleProcessForm extends PageParams {

    @ApiModelProperty(value = "流程名称",example = "电商1")
    private String processName;

    @ApiModelProperty(value = "业务Code",example = "pp")
    private String businessCode;

}
