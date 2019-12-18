package com.graduation.project.risk.project.dal.jpa.dataobject.blacklist;


import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import com.graduation.project.risk.project.biz.enums.OperatorType;
import com.graduation.project.risk.project.biz.enums.CommonType;

import java.util.Date;

@Data
@Entity
@Table(name = "blacklist_record")
@EqualsAndHashCode(callSuper = true)
public class BlackListRecordDO extends BaseEntity {

    private String name;

    private String idNumber;

    private String phoneNumber;

    private Long operatorId;

    private Date operatorTime;

    @Enumerated(EnumType.STRING)
    private OperatorType operatorType;

    private  String reason;

    @Enumerated(EnumType.STRING)
    private CommonType type;

}
