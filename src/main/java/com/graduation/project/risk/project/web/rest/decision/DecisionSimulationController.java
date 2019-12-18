package com.graduation.project.risk.project.web.rest.decision;

import com.graduation.project.risk.common.core.biz.RestBusinessTemplate;
import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.biz.enums.DecisionSimulationEnums;
import com.graduation.project.risk.project.biz.manager.DecisionSimulationManager;
import com.graduation.project.risk.project.biz.manager.RuleVariableManager;
import com.graduation.project.risk.project.web.form.decision.OrderSearchForm;
import com.graduation.project.risk.project.web.form.decision.RuleSimulationForm;
import com.graduation.project.risk.project.web.form.decision.SimulationSearchForOneForm;
import com.graduation.project.risk.project.web.form.decision.SimulationSearchForm;
import com.graduation.project.risk.project.web.vo.KeyValueVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/decisionsimulation")
@Api(value = "API - DecisionSimulationController", description = "DecisionSimulationController")
public class DecisionSimulationController {

    private Logger logger = LoggerFactory.getLogger(DecisionSimulationController.class);

    @Autowired
    private DecisionSimulationManager decisionSimulationManager;

    @Autowired
    private RuleVariableManager ruleVariableManager;

    /*
     *决策模拟列表页查询
     * @param SimulationSearchForm
     * @return
     */
    @ApiOperation(value = "决策模拟列表页查询", notes = "@param businessCode or processCode", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/search")
    public CommonRestResult processSearch(SimulationSearchForm simulationSearchForm){
        return RestBusinessTemplate.execute(() ->{
           return decisionSimulationManager.processSearch(simulationSearchForm);
        });
    }
    /*
     * 获取业务信息
     *
     * @return
     */
    @ApiOperation(value = "获取业务", notes = "获取业务", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/params")
    public CommonRestResult getParams(){
        return RestBusinessTemplate.execute(() ->{

            Map<String, List<KeyValueVO>> result = new HashMap<>();
            result.put("bizList", ruleVariableManager.getBizList());

            return result;
        });
    }
    /*
     *点击规则模拟进入选择页面
     *@param SimulationSearchForm
     * @return
     */
    @ApiOperation(value = "点击规则模拟进入选择页面",notes = "",httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("editSearch")
    public CommonRestResult editSearch(@Valid SimulationSearchForOneForm simulationSearchForOneForm){
        return RestBusinessTemplate.execute(() ->{
           return decisionSimulationManager.editSearch(simulationSearchForOneForm);
        });
    }
    /*
     *订单查询 只能查7天
     * @param OrderSearchForm
     * @return
     */
    @ApiOperation(value = "订单查询",notes = "notes",httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/riskordersearch")
    public CommonRestResult riskOrderSearch(@Valid OrderSearchForm orderSearchForm){
        return RestBusinessTemplate.execute(() -> {
           return decisionSimulationManager.riskOrderSearch(orderSearchForm);
        });
    }
    /*
    *获取规则模拟前的数据类型选择列表
     */
    @ApiOperation(value = "获取规则模拟前的数据类型选择列表",notes = "notes",httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/datatype")
    public CommonRestResult dateType(){
        return RestBusinessTemplate.execute(() ->{
           Map<String,List<KeyValueVO>> dataType = new HashMap<>();
           dataType.put("dataType", DecisionSimulationEnums.list());
           return dataType;
        });
    }


    /**
     * 规则模拟
     * @return
     */
    @ApiOperation(value = "规则模拟",notes = "notes",httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/rulesimulation")
    public CommonRestResult ruleSimulation(@RequestBody @Valid RuleSimulationForm ruleSimulationForm){
        return RestBusinessTemplate.execute(() ->{
            return decisionSimulationManager.ruleSimulation(ruleSimulationForm);
        });
    }
    
}
