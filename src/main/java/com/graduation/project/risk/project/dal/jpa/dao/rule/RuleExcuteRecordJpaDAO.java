package com.graduation.project.risk.project.dal.jpa.dao.rule;

import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleExcuteRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleExcuteRecordJpaDAO extends JpaRepository<RuleExcuteRecordDO,Long> {


}
