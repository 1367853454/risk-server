package com.graduation.project.risk.project.dal.jpa.dataobject.pp;

import com.graduation.project.risk.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "pp_risk_user_login_record")
@EqualsAndHashCode(callSuper = true)
public class PpRiskUserLoginRecordDO extends BaseEntity {

    private String account;

    private String deviceId;

    private String ip;

    private Date loginTime;

}
