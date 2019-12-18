package com.graduation.project.risk.project.dal.jpa.dao.rule;

import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleProcessDO;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import com.graduation.project.risk.project.biz.enums.TriggerLinkEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleProcessJpaDAO extends JpaRepository<RuleProcessDO, Long> {

    RuleProcessDO findByProcessCodeAndFlag(String processCode, Integer flag);

    RuleProcessDO findByBusinessCodeAndProcessCodeAndTriggerLinkAndPublishStatusAndFlag(String businessCode, String processCode, TriggerLinkEnums triggerLinkEnums, PublishStatus publishStatus, Integer flag);

    RuleProcessDO findByIdAndFlag(Long processId, Integer flag);

    RuleProcessDO findByBusinessNameAndTriggerLinkAndPublishStatus(String businessName, TriggerLinkEnums triggerLink, PublishStatus status);

    List<RuleProcessDO> findAllByFlag(Integer flag);


//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("select a from RuleProcessDO a where a.id = ?1")
//    RuleProcessDO getOneById(Long id);


}
