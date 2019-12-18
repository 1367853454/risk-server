package com.graduation.project.risk.project.web.form.decision;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 规则模拟请求参数
 */
@Data
public class RuleSimulationForm {

    @NotBlank(message = "businessCode can not be blank")
    @ApiModelProperty(value = "业务Code",example = "100")
    private String businessCode;


    @NotBlank(message = "processCode can not be blank")
    @ApiModelProperty(value = "流程Code",example = "chushen")
    private String processCode;

    @NotBlank(message = "triggerLinkEnums can not be blank")
    @ApiModelProperty(value = "触发环节",example = "firstInspection")
    private String triggerLinkEnums;

    @ApiModelProperty(value = "订单号列表")
    private List<String> orderCodeList;


}
