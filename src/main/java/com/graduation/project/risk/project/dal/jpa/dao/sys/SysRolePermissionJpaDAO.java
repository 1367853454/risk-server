package com.graduation.project.risk.project.dal.jpa.dao.sys;

import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysRolePermissionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface SysRolePermissionJpaDAO extends JpaRepository<SysRolePermissionDO,Long> {

    List<SysRolePermissionDO> findAllByRoleId(Long id);

    @Transactional
    @Query(value = "delete from sys_role_permission where role_id = ?1 and permission_id = ?2",nativeQuery = true)
    @Modifying
    void deleteByUserIdAndRoleId(Long roleId, Long permissionId);

    @Transactional
    @Modifying
    @Query("update SysRolePermissionDO set update_time = ?1 where role_id = ?2 and permission_id = ?3 ")
    public int updateUpdatetime(Date date, Long roleId, Long permissionId);

}
