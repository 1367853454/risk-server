package com.graduation.project.risk.project.web.rest.rule;

import com.graduation.project.risk.common.core.biz.RestBusinessTemplate;
import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.web.form.rule.*;
import com.graduation.project.risk.project.biz.enums.TriggerLinkEnums;
import com.graduation.project.risk.project.biz.manager.RuleProcessManager;
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
@RequestMapping("api/admin/process")
@Api(value = "API - RuleProcessController", description = "RuleProcessController")
public class RuleProcessController {

    private Logger logger = LoggerFactory.getLogger(RuleProcessController.class);

    @Autowired
    private RuleProcessManager ruleProcessManager;
    @Autowired
    private RuleVariableManager ruleVariableManager;



    @ApiOperation(value = "规则流程新增", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/ruleprocessadd")
//    @RequiresPermissions("blacklist:manage")
    public CommonRestResult ruleProcessAdd(@RequestBody @Valid ProcessAddForm processAddForm){
        return RestBusinessTemplate.transaction(() ->{
            logger.info("ruleProcessAdd Request");
            return ruleProcessManager.ruleProcessAdd(processAddForm);
        });
    }
    /*
    *规则流程管理列表页查询
    * @param RuleProcessForm
    * @return
     */
    @ApiOperation(value = "规则流程列表页查询", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/search")
    public CommonRestResult processSearch(RuleProcessForm ruleProcessForm){
        return RestBusinessTemplate.execute(() ->{
            return ruleProcessManager.ruleProcessSearch(ruleProcessForm);
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
    *规则流程删除
    * @param ProcessDeleteForm
    * @return boolean
     */
    @ApiOperation(value = "规则流程删除",httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/ruleprocessdelete")
    public CommonRestResult ruleProcessDelete(@RequestBody @Valid ProcessDeleteForm processDeleteForm){
        return RestBusinessTemplate.transaction(() ->{
            return ruleProcessManager.ruleProcessDelete(processDeleteForm);
        });
    }
    /*
    *规则流程发布
    * @param ProcessReleaseForm
     */
    @ApiOperation(value = "规则流程发布", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/release")
    public CommonRestResult release(@RequestBody @Valid ProcessReleaseForm processReleaseForm){
        return RestBusinessTemplate.transaction(() -> {
           ruleProcessManager.release(processReleaseForm);
           return true;
        });
    }
    /*
     *规则流程下线
     * @param ProcessOfflineForm
     */
    @ApiOperation(value = "规则流程下线", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/offline")
    public CommonRestResult release(@RequestBody @Valid ProcessOfflineForm processOfflineForm){
        return RestBusinessTemplate.transaction(() -> {
            ruleProcessManager.offline(processOfflineForm);
            return true;
        });
    }
    /*
     *获取规程流程具体信息
     * @param ProcessGetInfoForm
     * @return ProcessEditVO
     */
    @ApiOperation(value = "获取规程流程具体信息", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/processinfo")
    public CommonRestResult getProcessInfo(@Valid ProcessGetInfoForm processGetInfoForm){
        return RestBusinessTemplate.execute(() ->{
          return ruleProcessManager.getProcessInfo(processGetInfoForm);
        });
    }
    /*
     *编辑
     * @param ProcessEditForm
     */
    @ApiOperation(value = "规则流程编辑", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/edit")
    public CommonRestResult edit(@RequestBody @Valid ProcessEditForm processEditForm){
        return RestBusinessTemplate.transaction(() ->{
           ruleProcessManager.edit(processEditForm);
           return true;
        });
    }
    /*
    *获取触发环节
     */
    @ApiOperation(value = "获取触发环节", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/triggerlink")
    public CommonRestResult getTriggerLink(){
        return RestBusinessTemplate.execute(() ->{

            Map<String, List<KeyValueVO>> result = new HashMap<>();
            result.put("TriggerLinkEnums", TriggerLinkEnums.list());

            return result;
        });
    }
    /*
    *找规则包
    * @param ProcessPackageSearchForm
    * @return
     */
    @ApiOperation(value = "找规则包" ,notes = "@param packageCode businessCode",httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/packagesearch")
    public CommonRestResult packageSearch(ProcessPackageSearchForm processPackageSearchForm){
        return RestBusinessTemplate.execute(() ->{
           return ruleProcessManager.packageSearch(processPackageSearchForm);
        });
    }

    @ApiOperation(value = "规则流程管理", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/ruleprocessmanage")
//    @RequiresPermissions("blacklist:manage")
    public CommonRestResult ruleProcessManage(@RequestBody @Valid ProcessManageForm processManageForm){
        return RestBusinessTemplate.transaction(() ->{
            logger.info("ruleProcessManage Request");
            return ruleProcessManager.ruleProcessManage(processManageForm);
        });
    }

    /*
     *规则包管理前查看
     * @param ProcessPackageInfoForm
     * @return
     */
    @ApiOperation(value = "规则包管理前查看" ,notes = "",httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/package")
    public CommonRestResult packageSearch(@Valid ProcessPackageInfoForm processPackageInfoForm){
        return RestBusinessTemplate.execute(() ->{
            return ruleProcessManager.processPackage(processPackageInfoForm);
        });
    }

}
