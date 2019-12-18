package com.graduation.project.risk.project.dal.jpa.dao.sys;


import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleJpaDAO extends JpaRepository<SysRoleDO,Long> {

    SysRoleDO findByName(String name);

    SysRoleDO findById(Long id);

}
