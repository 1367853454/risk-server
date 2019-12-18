package com.graduation.project.risk.project.dal.jpa.dataobject.sys;


import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_user_role")
@EqualsAndHashCode(callSuper = true)
public class SysUserRoleDO extends BaseEntity {

    private Long userId;

    private Long roleId;

}
