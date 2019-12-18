package com.graduation.project.risk.project.web.rest.dto;

import com.graduation.project.risk.common.core.biz.RestBusinessTemplate;
import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.biz.manager.DTOManager;
import com.graduation.project.risk.project.web.form.DTO.RiskOrderForm;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/dto")
@Api(value = "API - DTOController", description = "description dto")
public class DTOController {

    private Logger logger = LoggerFactory.getLogger(DTOController.class);

    @Autowired
    private DTOManager dtoManager;

    @ApiOperation(value = "dto", notes = "dto", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/dto")
    public CommonRestResult dto(@RequestBody @Valid RiskOrderForm form){
        return RestBusinessTemplate.transaction(() ->{
            dtoManager.dto(form);
            return true;
        });
    }


}
