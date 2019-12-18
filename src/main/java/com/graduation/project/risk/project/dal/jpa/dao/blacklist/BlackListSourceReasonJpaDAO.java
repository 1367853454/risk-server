package com.graduation.project.risk.project.dal.jpa.dao.blacklist;

import com.graduation.project.risk.project.dal.jpa.dataobject.blacklist.BlackListSourceReasonDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlackListSourceReasonJpaDAO extends JpaRepository<BlackListSourceReasonDO,Long> {

    BlackListSourceReasonDO findById(Long blacklistSourceId);

    List<BlackListSourceReasonDO> findAllByBlacklistSourceId(Long blacklistSourceId);
}
