package com.graduation.project.risk.project.dal.jpa.dataobject.pp;


import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.VariableType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @Description pp业务系统变量
 **/
@Data
@Entity
@Table(name = "pp_rule_system_variable")
@EqualsAndHashCode(callSuper = true)
public class PpRuleSystemVariableDO extends BaseEntity {

    private String businessCode;

    private String variableName;

    @Enumerated(EnumType.STRING)
    private VariableType variableType;

}
