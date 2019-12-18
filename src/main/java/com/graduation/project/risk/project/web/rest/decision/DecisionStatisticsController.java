package com.graduation.project.risk.project.web.rest.decision;

import com.graduation.project.risk.common.core.biz.RestBusinessTemplate;

import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.biz.manager.DecisionStatisticsManager;
import com.graduation.project.risk.project.web.form.decision.StatisticsSearchForm;
import com.graduation.project.risk.project.web.vo.KeyValueVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/admin/decisionstatistics")
@Api(value = "API - DecisionStatisticsController", description = "DecisionStatisticsController")
public class DecisionStatisticsController {

    private Logger logger = LoggerFactory.getLogger(DecisionStatisticsController.class);

    @Autowired
    private DecisionStatisticsManager decisionStatisticsManager;


    /*
     * 获取参数
     * @return
     */
    @ApiOperation(value = "获取规则包名称、规则流程名称", notes = "", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/params")
    public CommonRestResult params(){
        return RestBusinessTemplate.execute(() ->{

            Map<String, List<KeyValueVO>> result = new HashMap<>();
            result.put("packageName", decisionStatisticsManager.getPackageList());
            result.put("processName",decisionStatisticsManager.getProcessCode());

            return result;
        });
    }
    /*
     * 决策统计上面部分
     * @param StatisticsSearchForm
     * @return
     */
    @ApiOperation(value = "决策统计上面部分", notes = "", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/packagevo")
    public CommonRestResult packageVO(StatisticsSearchForm statisticsSearchForm){
        return RestBusinessTemplate.execute(() ->{
            return decisionStatisticsManager.packageVO(statisticsSearchForm);
        });
    }
    /*
     * 决策统计下面部分
     * @param StatisticsSearchForm
     * @return
     */
    @ApiOperation(value = "决策统计下面部分", notes = "", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/statistics")
    public CommonRestResult statistics(StatisticsSearchForm statisticsSearchForm){
        return RestBusinessTemplate.execute(() ->{
            return decisionStatisticsManager.statistics(statisticsSearchForm);
        });
    }
    /*
     * 决策统计漏斗部分
     * @param StatisticsSearchForm
     * @return
     */
    @ApiOperation(value = "决策统计漏斗部分", notes = "", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/funnel")
    public CommonRestResult funnel(StatisticsSearchForm statisticsSearchForm){
        return RestBusinessTemplate.execute(() ->{
            return decisionStatisticsManager.funnel(statisticsSearchForm);
        });
    }


}
