package com.graduation.project.risk.common.core.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class VersionControlFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


//        String version = CommonRequestDataHolder.getVersion();
//        String from = CommonRequestDataHolder.getFrom();

//        if (StringUtils.equals(from, "com.pasarpinjam.android")
//                && !VersionUtil.isSupportMinVersion(version, "0.5.3")) {
//            throw new BizCoreException(ErrorCode.VERSION_ERROR);
//        } else if (StringUtils.equals(from, "com.pasarpinjam.android")
//                && !VersionUtil.isSupportMinVersion(version, "0.5")) {
//            throw new BizCoreException(ErrorCode.VERSION_ERROR);
//        }
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }


}
