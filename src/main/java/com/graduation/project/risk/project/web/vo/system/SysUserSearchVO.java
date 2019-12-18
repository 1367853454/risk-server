package com.graduation.project.risk.project.web.vo.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserSearchVO {

    private Long userId;

    private String name;

    private String account;

    private String status;

    private Long mobile;

    private Date createTime;

    private Date updateTime;

}
