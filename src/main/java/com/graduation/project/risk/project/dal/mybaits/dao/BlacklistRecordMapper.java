package com.graduation.project.risk.project.dal.mybaits.dao;

import com.graduation.project.risk.project.dal.mybaits.dataobject.BlackListRecordSearchDO;
import com.graduation.project.risk.project.web.form.blacklist.BlacklistRecordForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlacklistRecordMapper {

    List<BlackListRecordSearchDO> getBlacklistRecordPage(BlacklistRecordForm blacklistRecordForm);
}
