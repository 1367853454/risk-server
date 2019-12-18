package com.graduation.project.risk.project.dal.jpa.dao.blacklist;

import com.graduation.project.risk.project.dal.jpa.dataobject.blacklist.BlackListIdCardDO;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListIdCardJpaDAO extends JpaRepository<BlackListIdCardDO,Long> {

    BlackListIdCardDO findByIdcardAndStatus(String idCard, CommonStatus status);


}
