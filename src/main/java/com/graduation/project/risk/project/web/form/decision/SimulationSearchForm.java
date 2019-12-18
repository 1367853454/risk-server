package com.graduation.project.risk.project.web.form.decision;

import com.graduation.project.risk.common.base.entity.PageParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimulationSearchForm extends PageParams {

    @ApiModelProperty(value = "业务Code",example = "pp")
    private String businessCode;

    @ApiModelProperty(value = "流程Code",example = "ZHIMA")
    private String processCode;

}
