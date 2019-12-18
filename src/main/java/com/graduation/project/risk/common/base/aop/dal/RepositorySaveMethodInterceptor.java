package com.graduation.project.risk.common.base.aop.dal;

import java.util.Date;

import com.graduation.project.risk.common.core.biz.FieldUtil;
import com.graduation.project.risk.common.core.dal.AbstractAuditingEntity;
import com.graduation.project.risk.common.model.IdGenerator;
import com.graduation.project.risk.common.model.KeywordGenerator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Aspect
@Component
public class RepositorySaveMethodInterceptor {

    @Pointcut("execution(public * com.graduation.project.risk.project.dal.**.save(..))")
    public void save() {
    }

    @Before("save()")
    @SuppressWarnings("unchecked")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        Object param = joinPoint.getArgs()[0];

        if (param instanceof Iterable) {
            ((Iterable) param).forEach(this::onBeforeConvert);
        } else {
            onBeforeConvert(param);
        }

    }

    public void onBeforeConvert(Object source) {

        if (source != null && source instanceof IdGenerator) {
            ReflectionUtils.doWithFields(source.getClass(), field -> {
                ReflectionUtils.makeAccessible(field);
                if (field.isAnnotationPresent(Id.class) && field.get(source) == null) {
                    Object id = ((IdGenerator) source).generator();
                    field.set(source, id);

                }
            });

        }

        if (source != null && source instanceof KeywordGenerator) {
            ReflectionUtils.doWithFields(source.getClass(), field -> {

                if (field.getName().equals("keyword")
                        && FieldUtil.readField(source, field.getName()) == null) {
                    FieldUtil.writeField(source, field.getName(),
                            ((KeywordGenerator) source).keywordGenerator());
                }
            });

        }

        if (source != null && source instanceof AbstractAuditingEntity) {

            AbstractAuditingEntity entity = (AbstractAuditingEntity) source;

            if (entity.getCreateTime() == null) {
                entity.setCreateTime(new Date());
            }

            entity.setUpdateTime(new Date());
        }

    }

}
