package com.graduation.project.risk.common.rpc;

import com.graduation.project.risk.common.security.token.TokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class HessianServerProxyExporter extends HessianServiceExporter {

    private static final Logger log = LoggerFactory.getLogger(HessianServerProxyExporter.class);

    private TokenProvider tokenProvider;

    public HessianServerProxyExporter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String authorization = request.getHeader("authorization");
        if (StringUtils.isEmpty(authorization)) {
            throw new NestedServletException("Auth Is Empty!");
        }
        String[] authArr = authorization.trim().split(" ");
        String auth = authArr[1];
        auth = new String(Base64.getDecoder().decode(auth));
        String[] namePwdArr = auth.split(":");
        String pwd = namePwdArr[1];

//        try {
//            if (org.springframework.util.StringUtils.hasText(pwd) && tokenProvider.validateToken(pwd)) {
//                Authentication authentication = this.tokenProvider.getAuthentication(pwd);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch(MalformedJwtException e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            throw new ServletException("authorize failed");
//        }

        super.handleRequest(request, response);
    }


}
