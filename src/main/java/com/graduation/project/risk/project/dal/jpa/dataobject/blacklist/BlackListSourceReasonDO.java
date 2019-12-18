package com.graduation.project.risk.project.dal.jpa.dataobject.blacklist;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "blacklist_source_reason")
@EqualsAndHashCode(callSuper = true)
public class BlackListSourceReasonDO extends BaseEntity {

    private Long blacklistSourceId;

    private String name;

    private String level;

    @Enumerated(EnumType.STRING)
    private CommonStatus status;

}
