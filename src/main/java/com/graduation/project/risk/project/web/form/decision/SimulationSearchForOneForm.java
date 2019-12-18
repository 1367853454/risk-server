package com.graduation.project.risk.project.web.form.decision;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimulationSearchForOneForm {

    @NotNull
    @ApiModelProperty(value = "流程id",example = "1")
    private Long processId;

}
