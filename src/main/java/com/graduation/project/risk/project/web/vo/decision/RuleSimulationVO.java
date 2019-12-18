package com.graduation.project.risk.project.web.vo.decision;

import lombok.Data;

import java.util.List;

/**
 * 规则模拟返回结果
 */
@Data
public class RuleSimulationVO {

    private Integer testUserNumber;//测试用户数

    private Integer failNumber;//授信失败数

    private Integer successNumber;//授信成功数

    private String successRate;//授信成功率

    private String maxCreditAmount;//最大授信额度

    private String averageCreditAmount;//每个用户的平均授信额度

    List<RuleSimulationListVO> ruleSimulationListVOList;//审核结果列表

}
