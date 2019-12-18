package com.graduation.project.risk.project.dal.jpa.dataobject.pp;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.CommonRuleEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pp_risk_loan_result")
@EqualsAndHashCode(callSuper = true)
public class PpRiskLoanResultDO extends BaseEntity {

    private String orderCode;

    @Enumerated(EnumType.STRING)
    private CommonRuleEnums result;

    private String resultJson;

}
