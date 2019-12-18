package com.graduation.project.risk.common.core.biz;

import java.lang.reflect.Field;
import org.apache.commons.lang3.reflect.FieldUtils;

public class FieldUtil {
    public FieldUtil() {
    }

    public static void writeField(Object target, String fieldName, Object value) {
        try {
            FieldUtils.writeField(target, fieldName, value, true);
        } catch (IllegalAccessException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static Object readField(Object target, String fieldName) {
        try {
            return FieldUtils.readField(target, fieldName, true);
        } catch (IllegalAccessException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static Field getField(Class<?> cls, String fieldName) {
        return FieldUtils.getField(cls, fieldName, true);
    }
}

