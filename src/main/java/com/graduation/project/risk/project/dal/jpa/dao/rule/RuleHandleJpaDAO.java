package com.graduation.project.risk.project.dal.jpa.dao.rule;

import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleHandleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleHandleJpaDAO extends JpaRepository<RuleHandleDO,Long> {

    List<RuleHandleDO> findByRuleId(Long ruleId);
}
