package com.graduation.project.risk.project.web.rest.home;


import com.graduation.project.risk.common.core.biz.RestBusinessTemplate;
import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.biz.manager.HomeManager;
import com.graduation.project.risk.project.web.form.home.HomeSearchForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/home")
@Api(value = "API - HomeController", description = "主页面控制器")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HomeManager homeManager;

    @ApiOperation(value = "主页面今日数据查询", notes = "", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @RequestMapping("/today")
    public CommonRestResult todayData(){
        return RestBusinessTemplate.execute(() ->{
            return homeManager.getTodayData();
        });
    }

    @ApiOperation(value = "主页面统计数据查询", notes = "", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @RequestMapping("/search")
    public CommonRestResult statisticsData(HomeSearchForm homeSearchForm){
        return RestBusinessTemplate.execute(() ->{
            return homeManager.getStatisticsData(homeSearchForm);
        });
    }


}
