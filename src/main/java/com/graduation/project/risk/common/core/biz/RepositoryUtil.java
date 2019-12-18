package com.graduation.project.risk.common.core.biz;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.DependsOn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value = "applicationContextUtil")
public class RepositoryUtil {

    private static Repositories repositories = null;

    @PostConstruct
    public void init() {

        if (repositories == null) {
            repositories = new Repositories(ApplicationContextUtil.getContext());
        }
    }

    public static CrudRepository getRepositoryFor(Class entityClass) {
        return (CrudRepository) repositories.getRepositoryFor(entityClass);
    }
}
