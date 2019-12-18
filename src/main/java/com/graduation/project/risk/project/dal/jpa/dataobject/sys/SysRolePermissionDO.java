package com.graduation.project.risk.project.dal.jpa.dataobject.sys;


import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_role_permission")
@EqualsAndHashCode(callSuper = true)
public class SysRolePermissionDO extends BaseEntity {

    private Long roleId;

    private Long permissionId;

}
