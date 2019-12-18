package com.graduation.project.risk.project.web.rest.rule;


import com.graduation.project.risk.common.core.biz.RestBusinessTemplate;
import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.web.form.rule.*;
import com.graduation.project.risk.project.biz.enums.DecisionSimulationEnums;
import com.graduation.project.risk.project.biz.manager.RulePackageManager;
import com.graduation.project.risk.project.biz.manager.RuleVariableManager;
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
@RequestMapping("api/admin/package")
@Api(value = "API - RulePackageController", description = "RulePackageController")
public class RulePackageController {

    private Logger logger = LoggerFactory.getLogger(RulePackageController.class);

    @Autowired
    private RulePackageManager rulePackageManager;

    @Autowired
    private RuleVariableManager ruleVariableManager;

    /*
    *规则包列表页查询
    *
    * @param RulePackageForm
    * @return
    *
     */
    @ApiOperation(value = "规则包列表页查询", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/search")
    public CommonRestResult rulePackageSearch(RulePackageForm rulePackageForm){
        return RestBusinessTemplate.execute(() ->{

            return rulePackageManager.rulePackageSearch(rulePackageForm);
        });
    }
    /*
     * 获取业务
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


    @ApiOperation(value = "规则包新增", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/rulepackageadd")
//    @RequiresPermissions("blacklist:manage")
    public CommonRestResult rulePackageAdd(@RequestBody @Valid PackageAddForm packageAddForm){
        return RestBusinessTemplate.transaction(() ->{
            logger.info("rulePackageAdd Request");
            return rulePackageManager.rulePackageAdd(packageAddForm);
        });
    }

    @ApiOperation(value = "规则包删除", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/rulepackagedelete")
//    @RequiresPermissions("blacklist:manage")
    public CommonRestResult rulePackageDelete(@RequestBody @Valid PackageDeleteForm packageDeleteForm){
        return RestBusinessTemplate.transaction(() ->{
            logger.info("rulePackageDelete Request");
            return rulePackageManager.rulePackageDelete(packageDeleteForm);
        });
    }


    @ApiOperation(value = "规则包编辑", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/rulepackageupdate")
//    @RequiresPermissions("blacklist:manage")
    public CommonRestResult rulePackageUpdate(@RequestBody @Valid PackageUpdateForm packageUpdateForm){
        return RestBusinessTemplate.transaction(() ->{
            logger.info("rulePackageUpdate Request");
            return rulePackageManager.rulePackageUpdate(packageUpdateForm);
        });
    }
    /*
     *发布规则包前检查、选择发布版本
     * @param String businessCode String packageCode  Long id
     * @return  BusinessName PackageCode PackageVersion
     */
    @ApiOperation(value = "发布规则包前检查、选择发布版本", notes = "Long id", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/packageinfo")
    //@RequiresPermissions("")
    public CommonRestResult getPackageInfo(@Valid PackageReleaseBeforeForm packageReleaseBeforeForm){

        return RestBusinessTemplate.execute(() ->{
            return rulePackageManager.getPackageInfo(packageReleaseBeforeForm);
        });
    }
    /*
     * 获取全部版本及其发布状态
     *@param String Code, String package
     * @return
     */
    @ApiOperation(value = "获取全部版本", notes = "@param String Code, String package", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/versions")
    public CommonRestResult getVersions(PackageVersionForm packageVersionForm){

        return RestBusinessTemplate.execute(() ->{
            return rulePackageManager.getVersionAndStatus(packageVersionForm);
        });
    }
    /*
     * 获取全部变量、变量处理
     *@param String Code, String package
     * @return
     */
    @ApiOperation(value = "获取全部变量、变量处理", notes = "", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/variables")
    public CommonRestResult variables(){
        return RestBusinessTemplate.execute(() ->{

            Map<String, List<KeyValueVO>> result = new HashMap<>();
            result.put("variables", rulePackageManager.getVariables());
            result.put("resultType", DecisionSimulationEnums.list());

            return result;
        });
    }
    /*
    *规则包发布
    * @param Long id
    *
     */
    @ApiOperation(value = "规则包发布", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/release")
    public CommonRestResult release(@RequestBody @Valid PackageReleaseForm packageReleaseForm){
        return RestBusinessTemplate.execute(() ->{

             rulePackageManager.release(packageReleaseForm);
             return true;
        });
    }
    /*
     *规则包下线
     * @param Long id
     *
     */
    @ApiOperation(value = "规则包下线", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/offline")
    public CommonRestResult offline(@RequestBody @Valid PackageOfflineForm packageOfflineForm){
        return RestBusinessTemplate.execute(() ->{

            rulePackageManager.offline(packageOfflineForm);
            return true;
        });
    }
    /*
    *规则状态变更
    * @param id
     */
    @ApiOperation(value = "规则状态变更",httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/startandstop")
    public CommonRestResult startAndStop(@RequestBody @Valid PackageStartAndStopForm packageStartAndStopForm){
        return RestBusinessTemplate.transaction(() ->{
            rulePackageManager.stopAndStart(packageStartAndStopForm);
            return true;
        });
    }
    /*
     *规则包编辑前查看
     * @param PackageSearchForm PackageId packageVersion
     * @return
     */
    @ApiOperation(value = "编辑前查看信息",notes = "@param packageId",httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("editsearch")
    public CommonRestResult editSearch(@Valid PackageSearchForm packageSearchForm){
        return RestBusinessTemplate.execute(() ->{
           return rulePackageManager.ruleInfo(packageSearchForm);
        });
    }
    /*
    *规则删除
    * @param RuleDeleteForm ruleId
     */
    @ApiOperation(value = "规则删除",notes = "@param ruleId", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/ruledelete")
    public CommonRestResult ruleDelete(@RequestBody @Valid RuleDeleteForm ruleDeleteForm){
        return RestBusinessTemplate.transaction(() ->{
             rulePackageManager.ruleDelete(ruleDeleteForm);
             return true;
        });
    }
}
