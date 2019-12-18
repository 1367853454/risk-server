package com.graduation.project.risk.common.base.aop.logging;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class ControllerAOP {

    private final static Logger LOG = LoggerFactory.getLogger(ControllerAOP.class);


    ThreadLocal<Long> startTime = new ThreadLocal<>();


    @Pointcut("execution(public * com.graduation.project.risk.project.web.rest.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void before(JoinPoint joinPoint) {

        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        LOG.info("request url -> {}, method -> {}, ip -> {}, class method -> {}", request.getRequestURL(), request.getMethod(), request.getRemoteAddr()
                , joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        Object[] o = joinPoint.getArgs();
        if (null != o) {
            for (Object object : o) {
                if (object instanceof HttpServletResponse || object instanceof HttpServletRequest) {
                    LOG.info("arg -> response");
                } else {
                    LOG.info("arg -> {}", JSON.toJSONString(object));
                }
            }
        }

    }

    @AfterReturning(returning = "response", pointcut = "log()")
    public void after(Object response) {

    }


}
