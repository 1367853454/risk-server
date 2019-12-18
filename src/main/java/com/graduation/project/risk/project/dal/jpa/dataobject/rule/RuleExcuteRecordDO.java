package com.graduation.project.risk.project.dal.jpa.dataobject.rule;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rule_excute_record")
@EqualsAndHashCode(callSuper = true)
public class RuleExcuteRecordDO extends BaseEntity {

    private String businessCode;

    private String processCode;

    private String orderCode;

    private String packageCode;

    private Long ruleId;

    private String result;

    private String resultJson;


    public RuleExcuteRecordDO(String businessCode, String processCode, String orderCode) {
        this.businessCode = businessCode;
        this.processCode = processCode;
        this.orderCode = orderCode;
    }

    public RuleExcuteRecordDO() {
    }
}
