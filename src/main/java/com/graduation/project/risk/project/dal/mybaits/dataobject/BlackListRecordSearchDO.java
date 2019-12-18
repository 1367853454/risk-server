package com.graduation.project.risk.project.dal.mybaits.dataobject;


import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.CommonType;
import com.graduation.project.risk.project.biz.enums.OperatorType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
public class BlackListRecordSearchDO extends BaseEntity {

    private String name;

    private String idNumber;

    private String phoneNumber;

    private String operatorName;

    private Date operatorTime;

    @Enumerated(EnumType.STRING)
    private OperatorType operatorType;

    private  String reason;

    @Enumerated(EnumType.STRING)
    private CommonType type;

}
