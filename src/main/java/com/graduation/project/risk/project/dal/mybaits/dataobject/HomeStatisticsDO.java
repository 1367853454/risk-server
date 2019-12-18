package com.graduation.project.risk.project.dal.mybaits.dataobject;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class HomeStatisticsDO {

    /** 统计的日期 */
    private String staticticsDate;

    /** 拒绝事件数量. */
    private Integer rejectEventNumber;

    /** 通过事件数量. */
    private Integer passEventNumber;

    /** 复议事件数量. */
    private Integer reviewEventNumber;

    /** 事件总量. */
    private Integer totalNumber;

    /** 事件通过率. */
    private Double passRate;

    /** 事件拒绝率. */
    private Double rejectRate;

    /** 事件复议率. */
    private Double reviewRate;


}
