package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProcessPackageInfoForm {

    @NotNull
    @ApiModelProperty(value = "流程id",example = "4")
    private Long processId;

}
