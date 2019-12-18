package com.graduation.project.risk.project.dal.jpa.dao.pp;

import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PpRiskLoanInfoJpaDAO extends JpaRepository<PpRiskLoanInfoDO,Long> {

    List<PpRiskLoanInfoDO> findAllByKtpNo(String ktpNo);

    PpRiskLoanInfoDO findByOrderCode(String orderCode);

    List<PpRiskLoanInfoDO> findByOrderCodeIn(Collection orderCodeList);




}

