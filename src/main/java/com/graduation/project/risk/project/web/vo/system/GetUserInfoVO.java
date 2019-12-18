package com.graduation.project.risk.project.web.vo.system;

import lombok.Data;

import java.util.List;

@Data
public class GetUserInfoVO {

    private String name;

    private String account;

    private Long mobile;

    private List<RoleVO> role;

}
