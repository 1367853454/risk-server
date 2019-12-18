package com.graduation.project.risk.project.web.form.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 规则条件变量
 */
@Data
public class PackageUpdateRuleForm {

    @NotBlank(message = "ruleName can not be blank")
    @ApiModelProperty(value = "规则名称",example = "年龄")
    private String ruleName;

    @ApiModelProperty(value = "规则描述",example = "个人的年龄")
    private String ruleDescribe;

    @ApiModelProperty(value = "规则提示",example = "年龄哦")
    private String ruleTips;

    @NotBlank(message = "executeCondition can not be blank")
    @ApiModelProperty(value = "是否要求全部满足，allrule,onerule",example = "allrule")
    private String executeCondition;

    @NotBlank(message = "status can not be blank")
    @ApiModelProperty(value = "规则包状态",example = "published")
    private String status;

    private List<PackageUpdateRuleConditionForm> packageUpdateRuleConditionForms;

    private List<PackageUpdateRuleHandleForm> packageUpdateRuleHandleForms;


}
