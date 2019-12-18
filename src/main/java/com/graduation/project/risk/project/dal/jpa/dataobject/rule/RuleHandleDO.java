package com.graduation.project.risk.project.dal.jpa.dataobject.rule;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.web.form.rule.PackageUpdateRuleHandleForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rule_handle")
@EqualsAndHashCode(callSuper = true)
public class RuleHandleDO extends BaseEntity {

    private Long ruleVariableId;

    private String compare;

    private String compareValue;

    private Long ruleId;

    public RuleHandleDO(PackageUpdateRuleHandleForm packageUpdateRuleHandleForm, Long ruleId) {
        this.ruleVariableId = packageUpdateRuleHandleForm.getHandleVariableId();
        this.compare = packageUpdateRuleHandleForm.getHandleCompare();
        this.compareValue = packageUpdateRuleHandleForm.getHandleValue();
        this.ruleId = ruleId;
    }

    public RuleHandleDO() {
    }
}
