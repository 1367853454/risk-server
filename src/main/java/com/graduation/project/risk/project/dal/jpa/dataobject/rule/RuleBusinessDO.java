package com.graduation.project.risk.project.dal.jpa.dataobject.rule;


import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @Description 规则业务表
 **/
@Data
@Entity
@Table(name = "rule_business")
@EqualsAndHashCode(callSuper = true)
public class RuleBusinessDO extends BaseEntity {

    private String businessCode;

    private String businessName;

    @Enumerated(EnumType.STRING)
    private CommonStatus status;


}
