package com.graduation.project.risk.project.dal.jpa.dataobject.rule;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.CommonRuleEnums;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rule")
@EqualsAndHashCode(callSuper = true)
public class RuleDO extends BaseEntity {

    private String ruleName;

    private String ruleDescribe;

    private String ruleTips;


    @Enumerated(EnumType.STRING)
    private PublishStatus status;

    private Long rulePackageId;

    @Enumerated(EnumType.STRING)
    private CommonRuleEnums executeCondition;

    private String result;

    private Integer flag;

}
