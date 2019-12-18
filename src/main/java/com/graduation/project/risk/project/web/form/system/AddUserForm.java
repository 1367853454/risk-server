package com.graduation.project.risk.project.web.form.system;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@Data
public class AddUserForm {

    @ApiModelProperty(value = "真实姓名",example = "张三")
    private String name;//真实姓名

    @ApiModelProperty(value = "用户名",example = "哒哒哒")
    @NotBlank
    private String account;//用户名

    @ApiModelProperty(value = "手机号",example = "15858101221")
    private Long mobile;

    @ApiModelProperty(value = "角色id",example = "[1,2]")
    private List<Long> role;

}
