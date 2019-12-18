package com.graduation.project.risk.project.dal.jpa.dataobject.blacklist;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.CommonInd;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import com.graduation.project.risk.project.biz.enums.CommonType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "blacklist_mobile")
@EqualsAndHashCode(callSuper = true)
public class BlackListMobileDO extends BaseEntity {

    private String mobile;

    private String name;

    private Long blacklistIdcardId;

    private String blacklistReasons;

    private String severity;

    private Date blacklistTime;

    @Enumerated(EnumType.STRING)
    private CommonInd corpBlacklistInd;

    private Date corpBlacklistTime;

    @Enumerated(EnumType.STRING)
    private CommonInd hjBlacklistInd;

    private Date hjBlacklistTime;

    private long operatorId;

    @Enumerated(EnumType.STRING)
    private CommonStatus status;

    @Enumerated(EnumType.STRING)
    private CommonType type;

}
