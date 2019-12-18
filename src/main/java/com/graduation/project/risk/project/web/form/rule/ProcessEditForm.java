package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class ProcessEditForm {

    @NotNull
    @ApiModelProperty(value = "流程id",example = "1")
    private Long processId;

    @NotBlank
    @ApiModelProperty(value = "业务名称")
    private String businessName;

    @NotBlank
    @ApiModelProperty(value = "业务Code")
    private String businessCode;

    @NotBlank
    @ApiModelProperty(value = "流程名称")
    private String processName;

    @NotBlank
    @ApiModelProperty(value = "流程Code")
    private String processCode;

    @NotEmpty
    @ApiModelProperty(value = "触发环节")
    private String triggerLink;

    @NotBlank
    @ApiModelProperty(value = "流程描述")
    private String processDescribe;

}
