package com.graduation.project.risk.project.dal.jpa.dataobject.rule;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.PublishStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rule_package")
@EqualsAndHashCode(callSuper = true)
public class RulePackageDO extends BaseEntity {

    private String businessCode;

    private String businessName;

    private String packageCode;

    private String packageName;

    private String packageDescribe;

    private String packageVersion;

    @Enumerated(EnumType.STRING)
    private PublishStatus publishStatus;

    private Integer flag;

}
