package com.graduation.project.risk.common.base.config;

import javax.inject.Inject;

import com.graduation.project.risk.common.security.AuthoritiesConstants;
import com.graduation.project.risk.common.security.jwt.JWTConfigurer;
import com.graduation.project.risk.common.security.token.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class JwtTokenSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private TokenProvider tokenProvider;

    @Autowired
    private JHipsterProperties jHipsterProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/bower_components/**")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/test/**")
                .antMatchers("/h2-console/**")
                .antMatchers("/remote/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/admin/**").permitAll()
                .antMatchers("/api/**").permitAll()
                //.antMatchers("/api/**").authenticated()
                .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .antMatchers("/swagger-resources/configuration/ui").permitAll()
                .and()
                .apply(securityConfigurerAdapter());
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    @ConditionalOnMissingBean({ TextEncryptor.class })
    public TextEncryptor textEncryptor() {
        return Encryptors.text(jHipsterProperties.getSecurity().getEncrypt().getKey(),
                jHipsterProperties.getSecurity().getEncrypt().getSalt());
    }
}
