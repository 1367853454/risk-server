package com.graduation.project.risk.project.dal.jpa.dataobject.sys;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.project.biz.enums.CommonPermission;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_permission")
@EqualsAndHashCode(callSuper = true)
public class SysPermissionDO extends BaseEntity {

    private Long parentId;

    private String name;

    private String href;

    @Enumerated(EnumType.STRING)
    private CommonPermission type;

    private String permission;
}
