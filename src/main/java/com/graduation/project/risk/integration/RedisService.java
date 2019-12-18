package com.graduation.project.risk.integration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplateJDKSerialization;

    private StringRedisSerializer serializer = new StringRedisSerializer();

    /**
     *
     *
     * @param key   key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz) {

        String value = redisTemplateJDKSerialization.opsForValue().get(key);
        return parseJson(value, clazz);
    }

    public String get(String key) {
        return redisTemplateJDKSerialization.opsForValue().get(key);
    }

    /**
     * @param key
     * @param obj
     * @param ttl expired time , time unit seconds
     * @param <T>
     */
    public <T> void set(String key, T obj, long ttl) {

        if (StringUtils.isBlank(key)) return;

        if (obj == null) return;

        String data;

        if (obj instanceof java.lang.String) {
            data = (String) obj;
        } else {
            data = toJson(obj);
        }


        redisTemplateJDKSerialization.opsForValue().set(key, data);

        if (ttl != -1) {
            expire(key, ttl);
        }
    }

    /**
     * set expired time
     *
     * @param key key
     * @param ttl
     * @return
     */
    public void expire(final String key, long ttl) {
        redisTemplateJDKSerialization.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     *
     *
     * @param key key
     */
    public void delete(String key) {
        redisTemplateJDKSerialization.delete(key);
    }

    // ------------------------------------------ private method ------------------------------------------

    private String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.SortField);
    }

    private <T> T parseJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * repeat request lock
     *
     * @param key    redis key，default value is "lock"
     * @param expire seconds
     * @return true/false
     */
    public Boolean setLock(String key, Integer expire) {
        Long result = redisTemplateJDKSerialization.execute((RedisCallback<Long>) connection -> {

            StringBuilder lua = new StringBuilder();

            lua.append("local key = KEYS[1]");
            lua.append("local value = ARGV[1]");
            lua.append("local ttl = ARGV[2]");
            lua.append("local ok = redis.call('setnx', key, value)");
            lua.append("if ok == 1 then");
            lua.append("    redis.call('expire', key, ttl)");
            lua.append("end ");
            lua.append("return ok");

            logger.info("Repeat request check，redis key: {}, expired time：{}s", key, expire);

            return connection.eval(serializer.serialize(lua.toString()), ReturnType.INTEGER, 1, serializer.serialize(key), serializer.serialize("lock"), serializer.serialize(expire.toString()));
        });

        return result != null && result == 1;
    }

}
