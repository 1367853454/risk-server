package com.graduation.project.risk.common.core.web.filter;

import com.graduation.project.risk.common.core.web.CommonRequestDataHolder;

import javax.servlet.*;
import java.io.IOException;


//@Order(1)
//@Component
//@ServletComponentScan
//@Profile("dev")
//@WebFilter(urlPatterns = "/*", filterName = "mockCommonRequestDataFilter")
public class MockCommonRequestDataFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                                                                             throws IOException,
                                                                                             ServletException {

        CommonRequestDataHolder.set(null);

    }

    @Override
    public void destroy() {

    }
}
