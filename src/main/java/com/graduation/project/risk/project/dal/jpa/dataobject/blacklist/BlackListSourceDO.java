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
@Table(name = "blacklist_source")
@EqualsAndHashCode(callSuper = true)
public class BlackListSourceDO extends BaseEntity {

    private String name;

    private String nameOut;

    @Enumerated(EnumType.STRING)
    private CommonStatus status;

}
