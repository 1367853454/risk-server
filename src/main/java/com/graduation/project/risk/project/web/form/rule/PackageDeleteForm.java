package com.graduation.project.risk.project.web.form.rule;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * request params for delete rule package
 */
@Data
public class PackageDeleteForm {

    @NotNull
    @ApiModelProperty(value = "规则包id",example = "1")
    private Long packageId;

}
