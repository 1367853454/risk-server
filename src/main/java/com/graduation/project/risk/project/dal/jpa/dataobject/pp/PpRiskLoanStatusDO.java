package com.graduation.project.risk.project.dal.jpa.dataobject.pp;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.OverdueStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "pp_risk_loan_status")
@EqualsAndHashCode(callSuper = true)
public class PpRiskLoanStatusDO extends BaseEntity {

    private String orderCode;

    private Date repaymentTime;

    private BigDecimal repaymentAmount;

    private Date overdueTime;

    @Enumerated(EnumType.STRING)
    private OverdueStatus overdueStatus;

}
