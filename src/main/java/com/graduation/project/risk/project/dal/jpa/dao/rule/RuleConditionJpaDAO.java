package com.graduation.project.risk.project.dal.jpa.dao.rule;

import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleConditionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleConditionJpaDAO extends JpaRepository<RuleConditionDO,Long> {

    List<RuleConditionDO> findByRuleId(Long ruleId);
}
