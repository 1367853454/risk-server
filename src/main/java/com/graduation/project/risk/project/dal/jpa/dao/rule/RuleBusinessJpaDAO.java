package com.graduation.project.risk.project.dal.jpa.dao.rule;


import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleBusinessDO;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleBusinessJpaDAO extends JpaRepository<RuleBusinessDO,Long> {

    RuleBusinessDO findByBusinessCodeAndStatus(String businessCode,CommonStatus commonStatus);

    List<RuleBusinessDO> findByStatusAndBusinessName(CommonStatus status, String businessName);
}

