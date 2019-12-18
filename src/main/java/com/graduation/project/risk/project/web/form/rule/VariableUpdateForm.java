package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 更新变量请求参数
 */
@Data
public class VariableUpdateForm {
    @NotNull
    @ApiModelProperty(value = "变量id",example = "1")
    private Long variableId;

    @NotBlank(message = "businessCode can not be blank")
    @ApiModelProperty(value = "业务Code",example = "100")
    private String businessCode;

    @NotBlank(message = "variableName can not be blank")
    @ApiModelProperty(value = "变量名称",example = "年龄")
    private String variableName;

    @NotBlank(message = "variableDescribe can not be blank")
    @ApiModelProperty(value = "变量藐视",example = "申请人年龄")
    private String variableDescribe;

    @NotBlank(message = "variableType can not be blank")
    @ApiModelProperty(value = "变量类型",example = "basis")
    private String variableType;

    @NotBlank(message = "dataType can not be blank")
    @ApiModelProperty(value = "数据类型",example = "String")
    private String dataType;

    @NotBlank(message = "ifCanNull can not be blank")
    @ApiModelProperty(value = "是否为空",example = "no")
    private String ifCanNull;

    @ApiModelProperty(value = "默认值",example = "0")
    private String defaultData;

    @NotBlank(message = "systemVariable can not be blank")
    @ApiModelProperty(value = "对应系统变量",example = "pAge")
    private String systemVariable;

    @ApiModelProperty(value = "脚本类型",example = "java")
    private String scriptType;

    @ApiModelProperty(value = "示意",example = "java")
    private String schematic;

    @ApiModelProperty(value = "标题",example = "java")
    private String title;

    @ApiModelProperty(value = "描述",example = "java")
    private String description;

    @ApiModelProperty(value = "脚本内容",example = "java")
    private String scriptContent;



}
