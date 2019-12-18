package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * request params for delete rule package
 */
@Data
public class ProcessDeleteForm {

    @NotNull
    @ApiModelProperty(value = "流程Id",example = "1")
    private Long processId;

}
