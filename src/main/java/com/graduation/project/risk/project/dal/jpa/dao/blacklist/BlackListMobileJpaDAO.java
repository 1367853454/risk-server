package com.graduation.project.risk.project.dal.jpa.dao.blacklist;

import com.graduation.project.risk.project.dal.jpa.dataobject.blacklist.BlackListMobileDO;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlackListMobileJpaDAO extends JpaRepository<BlackListMobileDO,Long> {

    List<BlackListMobileDO> findByMobile(String mobile);

    BlackListMobileDO findByMobileAndStatus(String mobile, CommonStatus status);


}
