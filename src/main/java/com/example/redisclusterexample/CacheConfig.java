package com.example.redisclusterexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfig {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public CacheManager cacheManager () {
        RedisCacheManager redisCacheManager =
                RedisCacheManager.RedisCacheManagerBuilder
                        .fromConnectionFactory(connectionFactory)
                        .cacheDefaults(defaultConfiguration()) // 디폴트 설정
                        .withInitialCacheConfigurations(customConfigurationMap()) // @Cacheable value 별로 따로 설정
                        .build();

        return redisCacheManager;
    }

    private RedisCacheConfiguration defaultConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofMinutes(30));
    }

    private Map<String, RedisCacheConfiguration> customConfigurationMap() {
        Map<String, RedisCacheConfiguration> customConfigurationMap = new HashMap<>();
        customConfigurationMap.put("testCache2", defaultConfiguration().entryTtl(Duration.ofSeconds(3)));
        customConfigurationMap.put("testCache3", defaultConfiguration().entryTtl(Duration.ofDays(1)));
        return customConfigurationMap;
    }

}
