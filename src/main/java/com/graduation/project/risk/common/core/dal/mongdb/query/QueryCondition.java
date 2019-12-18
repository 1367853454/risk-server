package com.graduation.project.risk.common.core.dal.mongdb.query;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD})
@Retention(RUNTIME)
@Documented
public @interface QueryCondition {

    /**
     * param name
     */
    String name() default "";

    /**
     *
     */
    Condition condition() default Condition.EQUALS;
}
