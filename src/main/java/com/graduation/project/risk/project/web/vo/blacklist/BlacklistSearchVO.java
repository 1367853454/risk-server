package com.graduation.project.risk.project.web.vo.blacklist;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class BlacklistSearchVO {

    private Long blacklistIdcardId;

    private String name;

    private List<BlacklistSearchPhoneNumberVO> phoneNumbers;

    private String idNumber;

    private Timestamp applicationTime;

    private String state;

}
