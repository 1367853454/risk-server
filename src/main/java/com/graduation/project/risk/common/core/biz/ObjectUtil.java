package com.graduation.project.risk.common.core.biz;

import com.alibaba.fastjson.JSON;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanUtils;

public class ObjectUtil {
    public ObjectUtil() {
    }

    public static String toString(Object obj) {
        return ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static String toJSON(Object obj) {
        try {
            return JSON.toJSONString(obj);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static Map<String, Object> toMap(Object... objs) {
        Map<String, Object> map = new HashMap();
        Object[] var2 = objs;
        int var3 = objs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object obj = var2[var4];
            if (obj instanceof Map) {
                map.putAll((Map)obj);
            } else {
                toMap(map, obj);
            }
        }

        return map;
    }

    public static Map<String, Object> objToMap(Object obj, String... ignoreProperties) {
        try {
            Map<String, Object> map = new HashMap();
            toMap(map, obj);
            if (ignoreProperties != null && ignoreProperties.length != 0) {
                String[] var3 = ignoreProperties;
                int var4 = ignoreProperties.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    String name = var3[var5];
                    map.remove(name);
                }

                return map;
            } else {
                return map;
            }
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    public static <T> T fromJSON(Class<T> cls, String json) {
        try {
            return JSON.parseObject(json, cls);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void copyProperties(Object source, Object target) {
        try {
            BeanUtils.copyProperties(source, target);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void copyPropertiesUnderLineCase(Object source, Object target, String... ignoreProperties) {
        try {
            Set<String> ignorePropertieSet = new HashSet();
            Iterator var4 = Arrays.asList(ignoreProperties).iterator();

            while(var4.hasNext()) {
                String propertyName = (String)var4.next();
                ignorePropertieSet.add(propertyName);
                ignorePropertieSet.add(StringUtil.toCamelCase(propertyName));
            }

            Map<String, Object> srcMap = toMap(target);
            PropertyDescriptor[] tpds = BeanUtils.getPropertyDescriptors(target.getClass());
            PropertyDescriptor[] var6 = tpds;
            int var7 = tpds.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                PropertyDescriptor pd = var6[var8];
                String propertyName = pd.getName();
                if (!ignorePropertieSet.contains(propertyName)) {
                    if (srcMap.containsKey(propertyName)) {
                        FieldUtil.writeField(target, propertyName, srcMap.get(propertyName));
                    }

                    if (srcMap.containsKey(StringUtil.toCamelCase(propertyName))) {
                        FieldUtil.writeField(target, propertyName, srcMap.get(StringUtil.toCamelCase(propertyName)));
                    }
                }
            }

            BeanUtils.copyProperties(source, target);
        } catch (Exception var11) {
            throw new RuntimeException(var11);
        }
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        try {
            BeanUtils.copyProperties(source, target, ignoreProperties);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public static <T> T copy(Object source, Class<T> targetClass) {
        try {
            T object = targetClass.newInstance();
            copyProperties(source, object);
            return object;
        } catch (InstantiationException var3) {
            throw new RuntimeException(var3);
        } catch (IllegalAccessException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static void setPropertyIfNull(Object source, String property, Object value) {
        Object val = getProperty(source, property);
        if (val == null) {
            setProperty(source, property, value);
        }

    }

    public static final Object newInstance(Class objectClass) {
        try {
            return objectClass.newInstance();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static void setProperty(Object source, String property, Object value) {
        try {
            FieldUtils.writeField(source, property, value, true);
        } catch (IllegalAccessException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static Object getProperty(Object source, String propertyName) {
        return getFieldValue(source, propertyName);
    }

    public static Object getFieldValue(Object source, String fieldName) {
        try {
            return FieldUtils.readField(source, fieldName, true);
        } catch (IllegalAccessException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static boolean equals(Object object1, Object object2) {
        return ArrayUtil.equals(object1, object2);
    }

    public static String objToString(Object object) {
        return object == null ? "" : ReflectionToStringBuilder.toString(object, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

