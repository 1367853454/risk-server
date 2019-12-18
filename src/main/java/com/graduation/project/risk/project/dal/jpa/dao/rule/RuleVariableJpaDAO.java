package com.graduation.project.risk.project.dal.jpa.dao.rule;

import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleVariableDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface RuleVariableJpaDAO extends JpaRepository<RuleVariableDO,Long> {

    RuleVariableDO findByBusinessCodeAndVariableName(String businessCode,String variableName);

    RuleVariableDO findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from RuleVariableDO a where a.id = ?1")
    RuleVariableDO getOneById(Long id);
}
