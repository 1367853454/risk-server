package com.graduation.project.risk.project.dal.jpa.dao.sys;


import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysPermissionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPermissionJpaDAO extends JpaRepository<SysPermissionDO,Long> {

}
