package com.graduation.project.risk.project.dal.jpa.dataobject.rule;

import com.graduation.project.risk.project.web.form.rule.PackageUpdateRuleConditionForm;
import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rule_condition")
@EqualsAndHashCode(callSuper = true)
public class RuleConditionDO extends BaseEntity {

    private Long ruleVariableId;

    private String compare;

    private String compareValue;

    private Long ruleId;

    public RuleConditionDO(PackageUpdateRuleConditionForm packageUpdateRuleConditionForm, Long ruleId) {
        this.ruleVariableId = packageUpdateRuleConditionForm.getVariableId();
        this.compare = packageUpdateRuleConditionForm.getCompare();
        this.compareValue = packageUpdateRuleConditionForm.getValue();
        this.ruleId = ruleId;
    }

    public RuleConditionDO() {
    }
}
