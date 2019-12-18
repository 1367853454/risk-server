package com.graduation.project.risk.project.dal.jpa.dataobject.rule;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rule_process_package_rel")
@EqualsAndHashCode(callSuper = true)
public class RuleProcessPackageRelDO extends BaseEntity {

    private Long ruleProcessId;

    private String rulePackageCode;

    private Integer rulePackageOrderedNumber;

    private Integer flag;

}
