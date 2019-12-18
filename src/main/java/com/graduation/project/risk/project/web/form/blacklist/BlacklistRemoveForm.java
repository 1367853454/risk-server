package com.graduation.project.risk.project.web.form.blacklist;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class BlacklistRemoveForm {

    @NotNull
    @ApiModelProperty(value = "身份证黑名单id",example = "1")
    private Long blacklistIdcardId;

    @NotNull
    @ApiModelProperty(value = "黑名单中对应的手机号",example = "8878888")
    private String blacklistMobile;

    @NotBlank(message = "removeReason can not be blank")
    @ApiModelProperty(value = "移除原因",example = "the idcard is good")
    private String removeReason;

}
