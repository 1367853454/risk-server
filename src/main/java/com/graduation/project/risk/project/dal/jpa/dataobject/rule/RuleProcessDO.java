package com.graduation.project.risk.project.dal.jpa.dataobject.rule;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import com.graduation.project.risk.project.biz.enums.TriggerLinkEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rule_process")
@EqualsAndHashCode(callSuper = true)
public class RuleProcessDO extends BaseEntity {

    private String businessCode;

    private String businessName;

    private String processCode;

    private String processName;

    private String processDescribe;

    @Enumerated(EnumType.STRING)
    private TriggerLinkEnums triggerLink;

    @Enumerated(EnumType.STRING)
    private PublishStatus publishStatus;

    private Integer flag;

}
