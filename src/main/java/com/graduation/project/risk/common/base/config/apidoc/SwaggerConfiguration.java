package com.graduation.project.risk.common.base.config.apidoc;

import static springfox.documentation.builders.PathSelectors.regex;

import com.graduation.project.risk.common.base.config.JHipsterProperties;
import com.graduation.project.risk.common.constants.Constants;
import com.graduation.project.risk.common.core.web.CommonRequestData;
import com.graduation.project.risk.common.model.CommonRestResult;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StopWatch;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Springfox Swagger configuration.
 *
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
 * case, you can use a specific Spring profile for this class, so that only front-end developers
 * have access to the Swagger view.
 */
@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
@Profile(Constants.SPRING_PROFILE_SWAGGER)
public class SwaggerConfiguration extends WebMvcConfigurerAdapter {

    private final Logger       log                     = LoggerFactory
            .getLogger(SwaggerConfiguration.class);

    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    /**
     * Swagger Springfox configuration.
     *
     * @param jHipsterProperties the properties of the application
     * @return the Swagger Springfox configuration
     */
    @Bean
    public Docket swaggerSpringfoxDocket(JHipsterProperties jHipsterProperties) {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(jHipsterProperties.getSwagger().getContactName(),
                jHipsterProperties.getSwagger().getContactUrl(), jHipsterProperties.getSwagger()
                .getContactEmail());

        ApiInfo apiInfo = new ApiInfo(jHipsterProperties.getSwagger().getTitle(),
                jHipsterProperties.getSwagger().getDescription(), jHipsterProperties.getSwagger()
                .getVersion(), jHipsterProperties.getSwagger().getTermsOfServiceUrl(), contact,
                jHipsterProperties.getSwagger().getLicense(), jHipsterProperties.getSwagger()
                .getLicenseUrl());


        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        // init header param
        Arrays.asList(FieldUtils.getAllFields(CommonRequestData.class)).forEach(
                item -> {
                    ApiModelProperty annotation = item.getAnnotation(ApiModelProperty.class);
                    if (annotation != null) {
                        pars.add(new ParameterBuilder()
                                .name(CommonRequestData.HEADER_PREFIX + item.getName())
                                .description(annotation.value())
                                .modelRef(new ModelRef(annotation.dataType())).parameterType("header")
                                .required(false).hidden(true).defaultValue(annotation.example()).build());
                    }
                });

        Arrays.asList(FieldUtils.getAllFields(CommonRequestData.SensorsCommonParam.class)).forEach(
                item -> {

                    ApiModelProperty annotation = item.getAnnotation(ApiModelProperty.class);
                    if (annotation != null) {
                        pars.add(new ParameterBuilder()
                                .name(CommonRequestData.SENSOR_PREFIX + item.getName())
                                .description(annotation.value())
                                .modelRef(new ModelRef(annotation.dataType())).parameterType("header")
                                .hidden(true).required(false).defaultValue(annotation.example()).build());
                    }

                });

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .genericModelSubstitutes(CommonRestResult.class)
                .globalOperationParameters(pars)
                .select()
                .paths(
                        regex(DEFAULT_INCLUDE_PATTERN)
                )
                .build();
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("login.html").addResourceLocations("classpath:/public/");

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }

}
