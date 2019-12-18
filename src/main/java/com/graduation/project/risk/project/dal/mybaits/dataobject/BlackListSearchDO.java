package com.graduation.project.risk.project.dal.mybaits.dataobject;


import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BlackListSearchDO extends BaseEntity {

    private String idNumber;

    private Long blacklistIdcardId;

    private String mobile;

    private Long mobileId;

    private String name;

    private String status;

    private Timestamp applicationTime;


}
