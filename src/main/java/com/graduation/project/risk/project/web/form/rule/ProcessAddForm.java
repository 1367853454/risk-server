package com.graduation.project.risk.project.web.form.rule;

import com.graduation.project.risk.project.biz.enums.TriggerLinkEnums;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * request params for add rule process
 */
@Data
public class ProcessAddForm {

    @NotBlank(message = "businessCode can not be blank")
    @ApiModelProperty(value = "业务Code",example = "100")
    private String businessCode;


    @NotBlank(message = "processName can not be blank")
    @ApiModelProperty(value = "流程名称",example = "初审")
    private String processName;


    @NotBlank(message = "processCode can not be blank")
    @ApiModelProperty(value = "流程Code",example = "chushen")
    private String processCode;

    @NotNull
    @ApiModelProperty(value = "触发环节",example = "firstInspection")
    private TriggerLinkEnums triggerLink;

    @ApiModelProperty(value = "描述",example = "初审规则流程")
    private String describe;








}
