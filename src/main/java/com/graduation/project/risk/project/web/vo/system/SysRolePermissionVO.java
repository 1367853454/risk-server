package com.graduation.project.risk.project.web.vo.system;

import lombok.Data;

@Data
public class SysRolePermissionVO {

    private Long id; //permissionId

    private String label; //permission 页面显示的内容, 这两个字段名称不要改，vue语法

}
