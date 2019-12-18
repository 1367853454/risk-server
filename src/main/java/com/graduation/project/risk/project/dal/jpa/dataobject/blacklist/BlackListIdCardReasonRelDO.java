package com.graduation.project.risk.project.dal.jpa.dataobject.blacklist;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "blacklist_idcard_reason_rel")
@EqualsAndHashCode(callSuper = true)
public class BlackListIdCardReasonRelDO extends BaseEntity {

    private Long blacklistIdcardId;

    private Long blacklistSourceId;

    private Long blacklistSourceReasonId;

}
