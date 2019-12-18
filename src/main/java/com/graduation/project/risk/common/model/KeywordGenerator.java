package com.graduation.project.risk.common.model;

import java.util.Arrays;

import com.graduation.project.risk.common.annotation.KeywordField;
import com.graduation.project.risk.common.core.biz.FieldUtil;
import org.springframework.core.annotation.AnnotationUtils;

public interface KeywordGenerator {
    default String keywordGenerator() {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(this.getClass().getFields()).forEach((item) -> {
            if (AnnotationUtils.getAnnotation(item, KeywordField.class) != null) {
                Object value = FieldUtil.readField(this, item.getName());
                if (value != null) {
                    sb.append(value.toString());
                }
            }

        });
        return sb.toString();
    }
}
