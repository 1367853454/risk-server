package com.graduation.project.risk.project.dal.jpa.dao.sys;

import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysUserRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface SysUserRoleJpaDAO extends JpaRepository<SysUserRoleDO,Long> {

    List<SysUserRoleDO> findAllByUserId(Long id);

    @Transactional
    @Query(value = "delete from sys_user_role where user_id = ?1 and role_id = ?2",nativeQuery = true)
    @Modifying
    void deleteByUserIdAndRoleId(Long userId, Long RoleId);

    @Transactional
    @Modifying
    @Query("update SysUserRoleDO set update_time = ?1 where user_id = ?2 and role_id = ?3 ")
    public int updateUpdatetime(Date date, Long userId, Long RoleId);

}
