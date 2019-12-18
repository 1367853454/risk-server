package com.graduation.project.risk.project.dal.jpa.dao.pp;

import com.graduation.project.risk.project.dal.jpa.dataobject.pp.PpRuleSystemVariableDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PpRuleSystemVariableJpaDAO extends JpaRepository<PpRuleSystemVariableDO,Long> {

    PpRuleSystemVariableDO findByVariableName(String variableName);

    PpRuleSystemVariableDO findById(Long id);
}
