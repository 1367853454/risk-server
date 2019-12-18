package com.graduation.project.risk.common.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SessionSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private JHipsterProperties         jHipsterProperties;

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

                .antMatchers("/h2-console/**");
    }

    //    filter chain; firing Filter: 'WebAsyncManagerIntegrationFilter'
    //    filter chain; firing Filter: 'SecurityContextPersistenceFilter'
    //    filter chain; firing Filter: 'HeaderWriterFilter'
    //    filter chain; firing Filter: 'LogoutFilter'
    //    filter chain; firing Filter: 'UsernamePasswordAuthenticationFilter'
    //    filter chain; firing Filter: 'SecurityContextHolderAwareRequestFilter'
    //    filter chain; firing Filter: 'AnonymousAuthenticationFilter'
    //    filter chain; firing Filter: 'SessionManagementFilter'
    //    filter chain; firing Filter: 'ExceptionTranslationFilter'
    //    filter chain; firing Filter: 'FilterSecurityInterceptor'

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()

                .headers().frameOptions().disable()

                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)

                .and()

                .authorizeRequests()

                .antMatchers("/api/admin/**").hasRole("ADMIN")

                .antMatchers("/admin/**").hasRole("ADMIN")

                .antMatchers("/management/**").hasRole("ADMIN")

                .antMatchers("/api/**").hasRole("USER")

                .antMatchers("/swagger-resources/configuration/ui").permitAll()

                .antMatchers("/**").permitAll()

                .and()

                .formLogin().loginPage("/admin/login").defaultSuccessUrl("/admin")
                .failureUrl("/admin/login?error").permitAll()

                .and()

                .logout().logoutUrl("/admin/logout").permitAll();

        //.and().apply(securityConfigurerAdapter());
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(inMemoryUserDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    @ConditionalOnMissingBean({ TextEncryptor.class })
    public TextEncryptor textEncryptor() {
        return Encryptors.text(jHipsterProperties.getSecurity().getEncrypt().getKey(),
                jHipsterProperties.getSecurity().getEncrypt().getSalt());
    }

//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.userDetailsService(inMemoryUserDetailsService);
//
//    }

}
