package com.graduation.project.risk.project.web.vo.decision;

import lombok.Data;

/**
 * 规则模拟返回结果
 */
@Data
public class RuleSimulationListVO {

    private String orderCode;

    private String businessName;//对应产品

    private String fullName;//客户姓名

    private String mobile;

    private String applicationAmount;

    private String termLoan;//借款期限

    private String createTime;

    private String simulationResult;//模拟结果

}
