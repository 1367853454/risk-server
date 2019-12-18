package com.graduation.project.risk.project.web.rest.user;


import com.graduation.project.risk.common.core.biz.RestBusinessTemplate;
import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.biz.manager.BlacklistManager;
import com.graduation.project.risk.project.web.form.blacklist.*;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;


@RestController
@RequestMapping("/api/admin/blacklist")
@Api(value = "API - BlacklistController", description = "BlacklistController")
public class BlacklistController {

    private Logger logger = LoggerFactory.getLogger(BlacklistController.class);

    @Autowired
    private BlacklistManager blacklistManager;

    @GetMapping("/page")
    public ModelAndView page(Model model) {

        ModelAndView mv = new ModelAndView("blk-management");

        model.addAttribute("currentTime", new Date());

        return mv;
    }

    @ApiOperation(value = "add", notes = "notes", httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "value", dataType = "LONG")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/adduserintoblacklist")
    @RequiresPermissions("blacklist:manage")
    public CommonRestResult addUserintoBlacklist(@RequestBody @Valid AdduserIntoBlacklistForm adduserIntoBlacklistForm){

        return RestBusinessTemplate.transaction(() ->{
            logger.info("addUserIntoBlacklist Request");
            blacklistManager.add(adduserIntoBlacklistForm);
           //String msg = addUserIntoBlacklistService.getMessage(adduserIntoBlacklistForm);
            return true;
        });
    }


    /**
     * @Description get blacklist record
     * @Param [blacklistRecordForm]
     * @return
     **/
    @ApiOperation(value = "blacklist record", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/blacklistrecord")
    @RequiresPermissions("blacklist:log")
    public CommonRestResult getBlacklistRecord(@RequestBody @Valid BlacklistRecordForm blacklistRecordForm){
        return RestBusinessTemplate.execute(() ->{
            logger.info("blacklistrecord Request"+blacklistRecordForm);
            return blacklistManager.getBlacklistRecord(blacklistRecordForm);
        });
    }


    /**
     * @Description search for blacklis
     * @Param [blacklistSearchForm]
     * @return
     **/
    @ApiOperation(value = "blacklist search", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/blacklistsearch")
    @RequiresPermissions("blacklist:manage")
    public CommonRestResult blacklistSearch(@RequestBody @Valid BlacklistSearchForm blacklistSearchForm){
        return RestBusinessTemplate.execute(() ->{
            logger.info("blacklistSearch Request");
            return blacklistManager.blacklistSearch(blacklistSearchForm);
        });
    }


    /**
     * @Description remove blacklist
     * @Param [blacklistRemoveForm]
     * @return
     **/
    @ApiOperation(value = "blacklist remove", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/blacklistremove")
    @RequiresPermissions("blacklist:manage")
    public CommonRestResult blacklistRemove(@RequestBody @Valid BlacklistRemoveForm blacklistRemoveForm){
        return RestBusinessTemplate.transaction(() ->{
            logger.info("blacklistRemove Request");
            return blacklistManager.blacklistRemove(blacklistRemoveForm);
        });
    }

    @ApiOperation(value = "blacklist source id", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/getblacklistsourceid")
    public CommonRestResult getReasonIdList(@RequestBody @Valid SourceIdForm sourceIdForm){
        return RestBusinessTemplate.execute(() ->{
            return blacklistManager.getReasonIdList(sourceIdForm.getBlacklistSourceId());
        });
    }

    @GetMapping("/blacklistmanage")
    public ModelAndView blacklistManage() {
        return new ModelAndView("blacklist/blk-manage");
    }

    @GetMapping("/blacklistlog")
    public ModelAndView blacklistLog() {
        return new ModelAndView("blacklist/blk-log");
    }
}
