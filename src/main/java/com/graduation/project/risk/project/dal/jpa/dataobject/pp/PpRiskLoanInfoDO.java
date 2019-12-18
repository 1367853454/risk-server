package com.graduation.project.risk.project.dal.jpa.dataobject.pp;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "pp_risk_loan_info")
@EqualsAndHashCode(callSuper = true)
public class PpRiskLoanInfoDO extends BaseEntity {

    private String orderCode;

    private String businessCode;

    private String businessName;

    private Date applicationTime;

    private String ktpNo;

    private String mobile;

    private String maritalStatus;

    private String gender;

    private String salaryDay;

    private String email;

    private String deviceId;

    private String applicationAmount;

    private String education;

    private String personProvince;

    private String personCity;

    private String personDistrict;

    private String companyName;

    private String jobTitle;

    private String jobLevel;

    private String jobType;

    private String monthlySalary;

    private String lowestMonthlySalary;

    private String highestMonthlySalary;

    private Date startOfEmployment;

    private String occupationProvince;

    private String occupationCity;

    private String occupationDistrict;

    private String fullName;

    private String religion;

    private String lng;

    private String lat;

    private String termOfLoan;

}
