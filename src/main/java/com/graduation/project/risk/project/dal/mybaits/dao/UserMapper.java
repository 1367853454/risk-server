package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysPermissionDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysRoleDO;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysUserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    SysUserDO getUserByAccount(String account);

    List<SysRoleDO> getSysRoleByUserId(Long userId);

    List<SysPermissionDO> getPermissionByUserId(Long userId);
}
