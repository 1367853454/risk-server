package com.graduation.project.risk.project.web.form.blacklist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SourceIdForm {

    @ApiModelProperty(value = "内部黑名单id")
    private Long blacklistSourceId;
}
