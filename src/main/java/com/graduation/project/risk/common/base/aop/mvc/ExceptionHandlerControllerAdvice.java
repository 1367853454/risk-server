package com.graduation.project.risk.common.base.aop.mvc;

import java.util.stream.Collectors;

import com.graduation.project.risk.common.core.biz.BizCoreException;
import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.biz.util.LoggerUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice("com.graduation.project.risk.project.web.rest")
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        //log first
        LoggerUtil.error(ex.getLocalizedMessage(), ex);

        //envelope with CommonResponseResult
        CommonRestResult envelop = new CommonRestResult(false, ex.getMessage(), null);

        // the catch ex
        if(ex instanceof BizCoreException){
            envelop.setCode(((BizCoreException) ex).getCode().getCode());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //force 200 response
        return handleExceptionInternal(ex, envelop, headers, HttpStatus.OK, request);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                      HttpStatus status, WebRequest request) {
        //log first
        LoggerUtil.error(ex.getLocalizedMessage() + "[" + ex.getFieldErrors() + "]", ex);

        CommonRestResult envelop = new CommonRestResult(false, ex.getFieldErrors().toString(), null);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(ex, envelop, headers, HttpStatus.OK, request);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        //log first
        LoggerUtil.error(ex.getLocalizedMessage() + "[" + ex.getBindingResult().getFieldErrors()
                + "]", ex);

        String message = org.apache.commons.lang3.StringUtils.join(
                ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()), ",");

        CommonRestResult envelop = new CommonRestResult(false, message, null);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(ex, envelop, headers, HttpStatus.OK, request);
    }
}
