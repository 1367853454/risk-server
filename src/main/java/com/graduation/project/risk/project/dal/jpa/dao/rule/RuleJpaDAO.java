package com.graduation.project.risk.project.dal.jpa.dao.rule;

import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleDO;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleJpaDAO extends JpaRepository<RuleDO,Long> {

    List<RuleDO> findByRulePackageIdAndFlag(Long packageId, Integer flag);

    List<RuleDO> findByRulePackageIdAndStatusAndFlag(Long rulePackageId, PublishStatus publishStatus,Integer flag);

    RuleDO findById(Long id);

    RuleDO findByIdAndFlag(Long id , Integer flag);
}
