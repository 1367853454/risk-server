package com.graduation.project.risk.project.web.vo.home;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class HomeTodayVO {

    /** 今日拒绝事件数量. */
    private Integer rejectEventNumber;

    /** 今日通过事件数量. */
    private Integer passEventNumber;

    /** 今日事件总量. */
    private Integer totalNumber;

    /** 事件通过率. */
    private BigDecimal passRate;

    /** 事件拒绝率. */
    private BigDecimal rejectRate;

    /** 放款金额. */
    private BigDecimal applicationAmount;

    /** 件均额度. */
    private BigDecimal averageAmount;


}
