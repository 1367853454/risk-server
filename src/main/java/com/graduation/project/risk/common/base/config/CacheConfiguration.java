package com.graduation.project.risk.common.base.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

/**
 * system cache
 *
 * you should config it in application.yml, spring.cache.cache-names
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    /**
     * default cache
     */
    public static final String DEFAULT = "DEFAULT";

    private static List<String> list = new ArrayList<>(10);

    static {

        if (0 == list.size()) {
            list.add(DEFAULT);
        }

    }

    @Bean
    public CacheManager dedao() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<GuavaCache> caches = new ArrayList<>();
        for(String c : list) {
            caches.add(new GuavaCache(c, CacheBuilder.newBuilder().maximumSize(50000)
                    .expireAfterWrite(1, TimeUnit.DAYS).build()));
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }

}
