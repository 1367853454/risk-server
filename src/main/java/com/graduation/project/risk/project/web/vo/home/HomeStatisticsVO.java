package com.graduation.project.risk.project.web.vo.home;


import com.graduation.project.risk.project.dal.mybaits.dataobject.HomeStatisticsDO;
import lombok.Data;

import java.util.List;

@Data
public class HomeStatisticsVO {

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

    /** 每日的统计详情列表. */
    List<HomeStatisticsDO> statisticsList;




}
