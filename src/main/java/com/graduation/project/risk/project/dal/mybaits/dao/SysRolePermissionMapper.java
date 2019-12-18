package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.web.vo.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface SysRolePermissionMapper {

    Set<SysRole> getPermission(Long id);

}
