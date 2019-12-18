package com.graduation.project.risk.project.dal.jpa.dao.pp;

import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRiskLoanResultDO;
import com.graduation.project.risk.project.biz.enums.CommonRuleEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PpRiskLoanResultJpaDAO extends JpaRepository<PpRiskLoanResultDO,Long> {

    Integer countByCreateTimeBetween(Date beginTime, Date stopTime);


    List<PpRiskLoanResultDO> findByCreateTimeBetweenAndResult(Date beginTime, Date stopTime, CommonRuleEnums result);

}
