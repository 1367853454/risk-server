package com.graduation.project.risk.project.dal.jpa.dataobject;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "login_record")
@EqualsAndHashCode(callSuper = true)
public class LoginRecordDO extends BaseEntity {

    private Date loginTime;

    private Long sysUserId;

    private String sysUserAccount;


}
