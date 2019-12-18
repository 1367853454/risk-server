package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProcessOfflineForm {

    @NotNull
    @ApiModelProperty(value = "流程Id")
    private Long processId;

}
