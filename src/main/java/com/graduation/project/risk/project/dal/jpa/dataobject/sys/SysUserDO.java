package com.graduation.project.risk.project.dal.jpa.dataobject.sys;

import com.graduation.project.risk.project.web.form.system.AddUserForm;
import com.graduation.project.risk.common.base.entity.BaseEntity;
import com.graduation.project.risk.common.core.biz.CommonUtil;
import com.graduation.project.risk.project.biz.constants.CommonConstant;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Data
@Entity
@Table(name = "sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUserDO extends BaseEntity {

    private String name;

    private String account;

    private String password;

    private String remark;

    @Enumerated(EnumType.STRING)
    private CommonStatus status;

    private Long mobile;

    private Integer flag;

    public SysUserDO(){
    }

    public SysUserDO(AddUserForm addUserForm){
        this.account = addUserForm.getAccount();
        this.status = CommonStatus.normal;
        this.remark = "";
        this.password = "e10adc3949ba59abbe56e057f20f883e";
        this.flag = CommonConstant.NORMAL_FLAG;
        if (!CommonUtil.isEmpty(addUserForm.getName())){
            this.name = addUserForm.getName();
        }
        if (!CommonUtil.isEmpty(addUserForm.getMobile())){
            this.mobile = addUserForm.getMobile();
        }

    }

}
