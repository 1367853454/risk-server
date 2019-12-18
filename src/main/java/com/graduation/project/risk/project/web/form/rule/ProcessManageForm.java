package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * request params for manage rule process
 */
@Data
public class ProcessManageForm {

    @NotNull
    @ApiModelProperty(value = "流程id",example = "4")
    private Long processId;

    private List<ProcessManagePackageForm> processManagePackageFormList;


}
