package com.graduation.project.risk.project.dal.jpa.dataobject.blacklist;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "blacklist_mobile_reason_rel")
@EqualsAndHashCode(callSuper = true)
public class BlackListMobileReasonRelDO extends BaseEntity {

    private Long blacklistMobileId;

    private Long blacklistSourceId;

    private Long blacklistSourceReasonId;


}
