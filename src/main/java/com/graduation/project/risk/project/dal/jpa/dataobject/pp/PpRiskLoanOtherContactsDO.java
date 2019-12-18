package com.graduation.project.risk.project.dal.jpa.dataobject.pp;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pp_risk_loan_other_contacts")
@EqualsAndHashCode(callSuper = true)
public class PpRiskLoanOtherContactsDO extends BaseEntity {

    private String orderCode;

    private String contactName1;

    private String contactPhone1;

    private String contactRelation1;

    private String contactName2;

    private String contactPhone2;

    private String contactRelation2;

    private String contactName3;

    private String contactPhone3;

    private String contactRelation3;

    private String contactName4;

    private String contactPhone4;

    private String contactRelation4;

    private String contactName5;

    private String contactPhone5;

    private String contactRelation5;


}
