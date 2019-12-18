package com.graduation.project.risk.common.core.biz;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ComponentAutowireFactory implements ApplicationContextAware {

    private static ApplicationContext _applicationContext;

    public static void autowire(Object bean) {
        _applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        _applicationContext = applicationContext;
    }
}
