package com.graduation.project.risk.project.web.rest.rule;

import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.web.form.rule.RuleVariableForm;
import com.graduation.project.risk.common.core.biz.BizCoreException;
import com.graduation.project.risk.common.core.biz.ErrorCode;
import com.graduation.project.risk.common.core.biz.RestBusinessTemplate;
import com.graduation.project.risk.project.biz.enums.DataType;
import com.graduation.project.risk.project.biz.enums.VariableType;
import com.graduation.project.risk.project.biz.manager.RuleVariableManager;
import com.graduation.project.risk.project.dal.jpa.dataobject.rule.RuleVariableDO;
import com.graduation.project.risk.project.web.form.rule.VariableAddForm;
import com.graduation.project.risk.project.web.form.rule.VariableUpdateForm;
import com.graduation.project.risk.project.web.vo.KeyValueVO;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/variable")
@Api(value = "API - RuleVariableController", description = "RuleVariableController")
public class RuleVariableController {

    private Logger logger = LoggerFactory.getLogger(RuleVariableController.class);

    @Autowired
    private RuleVariableManager ruleVariableManager;

    /**
     * 变量管理列表页
     *
     * @return
     */
    @GetMapping("/page")
    public ModelAndView page() {
        return new ModelAndView("rule/var-page");
    }

    /**
     * 查看 / 编辑页面
     *
     * @param model
     * @param varId  变量ID，有ID时编辑，无ID时新增
     * @param edit   1=编辑，0=查看
     * @return
     */
    @GetMapping("/editPage")
    public ModelAndView editPage(Model model, @RequestParam(required = false) Long varId, @RequestParam Integer edit) {

        ModelAndView mv = new ModelAndView("rule/var-edit");

        model.addAttribute("varId", varId);
        model.addAttribute("edit", edit);

        if (varId != null && varId != 0) {
            RuleVariableDO var = ruleVariableManager.getOne(varId);
            model.addAttribute("ruleVar", var);
        }

        return mv;
    }

    /**
     * 变量管理列表页查询
     *
     * @param ruleVariableForm
     * @return
     */
    @ApiOperation(value = "变量管理列表页查询", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/search")
    public CommonRestResult variableSearch(RuleVariableForm ruleVariableForm){
        return RestBusinessTemplate.execute(() ->{

            return ruleVariableManager.ruleVariableSearch(ruleVariableForm);
        });
    }

    /**
     * 获取下拉选内容
     *
     * @param type 0 = 获取业务、变量类型、数据类型下拉选内容
     *             1 = 获取业务
     *             2 = 获取变量类型
     *             3 = 获取数据类型
     * @return
     */
    @ApiOperation(value = "获取下拉选内容", notes = "0 = 获取业务、变量类型、数据类型下拉选内容，1 = 获取业务，2 = 获取变量类型，3 = 获取数据类型", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/params")
    public CommonRestResult getParams(@RequestParam(required = false, defaultValue = "0") Integer type){
        return RestBusinessTemplate.execute(() ->{

            if (type == null) {
                throw new BizCoreException(ErrorCode.PARAM_MISS);
            }
            Map<String, List<KeyValueVO>> result = new HashMap<>();
            switch (type) {
                case 0:
                    result.put("bizList", ruleVariableManager.getBizList());
                    result.put("varTypeList", VariableType.list());
                    result.put("dataTypeList", DataType.list());
                    break;
                case 1:
                    result.put("bizList", ruleVariableManager.getBizList());
                    break;
                case 2:
                    result.put("varList", VariableType.list());
                    break;
                case 3:
                    result.put("dataTypeList", DataType.list());
                    break;
                default:
                    throw new BizCoreException(ErrorCode.PARAM_MISS);
            }

            return result;
        });
    }

    @ApiOperation(value = "变量编辑", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/variableupdate")
//    @RequiresPermissions("blacklist:manage")
    public CommonRestResult variableUpdate(@RequestBody @Valid VariableUpdateForm variableUpdateForm){
        return RestBusinessTemplate.transaction(() ->{
            logger.info("variableUpdate Request");
            return ruleVariableManager.variableUpdate(variableUpdateForm);
        });
    }


    @ApiOperation(value = "变量新增", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/variableadd")
//    这是能否进行新增的权限认证
//    @RequiresPermissions("blacklist:manage")
    public CommonRestResult variableAdd(@RequestBody @Valid VariableAddForm variableAddForm){
        return RestBusinessTemplate.transaction(() ->{
            logger.info("variableadd Request");
            return ruleVariableManager.variableAdd(variableAddForm);
        });
    }

    @ApiOperation(value = "获取变量信息", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/rulevariableinfo")
    public CommonRestResult getRuleVariableInfo(@RequestParam Long variableId){
        return RestBusinessTemplate.execute(() ->{

            return ruleVariableManager.getRuleVariableInfo(variableId);
        });
    }


}
