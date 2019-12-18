package com.graduation.project.risk.common.core.web.filter;

import com.alibaba.fastjson.JSON;
import com.graduation.project.risk.common.core.biz.BizCoreException;
import com.graduation.project.risk.common.core.biz.ErrorCode;
import com.graduation.project.risk.common.core.web.CommonRequestData;
import com.graduation.project.risk.common.core.web.CommonRequestDataHolder;
import com.graduation.project.risk.common.model.CommonRestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

//@Order(1)
//@Component
//@ServletComponentScan
//@WebFilter(urlPatterns = "/*", filterName = "commonRequestDataFilter")
public class CommonRequestDataFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(CommonRequestDataFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException,
            ServletException {

        try {

            CommonRequestData commonData = parserCommonData((HttpServletRequest) request);

            CommonRequestDataHolder.set(commonData);

            chain.doFilter(request, response);

        } catch (BizCoreException e) {
            CommonRestResult restResult = new CommonRestResult();
            restResult.setCode(ErrorCode.SYSTEM_EXCEPTION.getCode());
            restResult.setStatus(CommonRestResult.FAIL);
            restResult.setMessage(ErrorCode.SYSTEM_EXCEPTION.getDesc());
            response.getWriter().append(JSON.toJSONString(restResult));

        } catch (Exception e) {
            throw new ServletException("construct common data error", e);
        } finally {

            CommonRequestDataHolder.clear();
        }

    }

    private CommonRequestData parserCommonData(HttpServletRequest request) {

        CommonRequestData data = new CommonRequestData();
        BeanWrapper dataBean = new BeanWrapperImpl(data);

        CommonRequestData.SensorsCommonParam sensors = new CommonRequestData.SensorsCommonParam();
        BeanWrapper sensorsBean = new BeanWrapperImpl(sensors);

        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {

            String headerName = headers.nextElement();

            if (headerName.startsWith(CommonRequestData.HEADER_PREFIX)) {
                String propertyName = headerName.substring(
                        CommonRequestData.HEADER_PREFIX.length(), headerName.length());

                // logger.info("header-key: [{}], header-value: [{}]", propertyName, request.getHeader(headerName));

                if (dataBean.isWritableProperty(propertyName)) {
                    dataBean.setPropertyValue(propertyName, request.getHeader(headerName));
                }

            }

            if (headerName.startsWith(CommonRequestData.SENSOR_PREFIX)) {
                String propertyName = headerName.substring(
                        CommonRequestData.SENSOR_PREFIX.length(), headerName.length());

                if (sensorsBean.isWritableProperty(propertyName)) {
                    sensorsBean.setPropertyValue(propertyName, request.getHeader(headerName));
                }
            }

        }

        data.setSensors((CommonRequestData.SensorsCommonParam) sensorsBean.getWrappedInstance());

        return data;
    }

    @Override
    public void destroy() {

    }
}
