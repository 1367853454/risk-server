package com.graduation.project.risk.project.dal.jpa.dao.rule;

import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleProcessPackageRelDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RuleProcessPackageRelJpaDAO extends JpaRepository<RuleProcessPackageRelDO,Long> {

    List<RuleProcessPackageRelDO> findByRuleProcessIdAndFlag(Long processId, Integer flag);


    @Modifying
    @Transactional
    @Query("update RuleProcessPackageRelDO p set p.flag=?2 where p.ruleProcessId=?1")
    int updateFlag(Long processId,Integer flag);


    List<RuleProcessPackageRelDO> findByRuleProcessIdAndFlagOrderByRulePackageOrderedNumber(Long ruleProcessId,Integer flag);


}