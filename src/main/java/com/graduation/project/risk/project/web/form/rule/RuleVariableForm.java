package com.graduation.project.risk.project.web.form.rule;

import com.graduation.project.risk.common.base.entity.PageParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RuleVariableForm extends PageParams {

    @ApiModelProperty(value = "变量名称",example = "BLACK_LIST")
    private String variableName;

    @ApiModelProperty(value = "业务Code",example = "现金贷")
    private String businessCode;

    @ApiModelProperty(value = "变量类型",example = "BASIS")
    private String variableType;

    @ApiModelProperty(value = "数据类型",example = "BOOLEAN")
    private String dataType;

    @ApiModelProperty(value = "变量描述",example = "黑名单")
    private String variableDescribe;

}
