package com.graduation.project.risk.project.dal.jpa.dataobject.rule;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "rule_record")
@EqualsAndHashCode(callSuper = true)
public class RuleRecordDO extends BaseEntity {

    private Long ruleVariableId;

    private Long rulePackageId;

    private Long ruleProcessId;

    private Long operatorId;

    private Date operatorTime;

    private String operatorType;

}
