package com.graduation.project.risk.common.security.token;

import com.alibaba.fastjson.JSON;
import com.graduation.project.risk.common.base.config.JHipsterProperties;
import com.graduation.project.risk.common.security.AppAuthentication;
import com.graduation.project.risk.common.security.SecurityApp;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private final Logger        log             = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private String              secretKey;

    @Inject
    private JHipsterProperties jHipsterProperties;

    @PostConstruct
    public void init() {
        this.secretKey = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
    }

    public String createToken(AppAuthentication authentication) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        String appId = authentication.getAppId();

        return Jwts.builder().setId(appId)
            .setSubject(JSON.toJSONString(authentication.getPrincipal()))
            .claim(AUTHORITIES_KEY, authorities).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        SecurityApp user = JSON.parseObject(claims.get(Claims.SUBJECT).toString(),
            SecurityApp.class);

        user.setAuthorities(authorities);

        AppAuthentication authentication = new AppAuthentication(user);

        return authentication;
    }

    public boolean validateToken(String authToken) throws MalformedJwtException {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
        return true;
    }
}
