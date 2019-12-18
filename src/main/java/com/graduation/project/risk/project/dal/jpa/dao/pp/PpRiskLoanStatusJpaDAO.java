package com.graduation.project.risk.project.dal.jpa.dao.pp;

import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanStatusDO;
import com.graduation.project.risk.project.biz.enums.OverdueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PpRiskLoanStatusJpaDAO extends JpaRepository<PpRiskLoanStatusDO,Long> {

    PpRiskLoanStatusDO findByOrderCodeAndOverdueStatus(String orderCode, OverdueStatus status);

    PpRiskLoanStatusDO findByOrderCode(String orderCode);

}
