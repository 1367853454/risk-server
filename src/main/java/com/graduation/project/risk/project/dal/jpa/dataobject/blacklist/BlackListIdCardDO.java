package com.graduation.project.risk.project.dal.jpa.dataobject.blacklist;


import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import com.graduation.project.risk.project.biz.enums.CommonInd;
import com.graduation.project.risk.project.biz.enums.CommonType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "blacklist_idcard")
@EqualsAndHashCode(callSuper = true)
public class BlackListIdCardDO extends BaseEntity {

    private String idcard;

    private String name;

    private String blacklistReasons;

    private String severity;

    private Date blacklistTime;

    private Date corpBlacklistTime;

    private Date hjBlacklistTime;

    @Enumerated(EnumType.STRING)
    private CommonStatus status;

    @Enumerated(EnumType.STRING)
    private CommonType type;

    @Enumerated(EnumType.STRING)
    private CommonInd corpBlacklistInd;

    @Enumerated(EnumType.STRING)
    private CommonInd hjBlacklistInd;


}
