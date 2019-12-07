package com.binovizer.redisson.demo.config;

import com.binovizer.redisson.demo.ApplicationProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * The type RedissonConfiguration
 *
 * @author Mohd Nadeem
 */
@Configuration
public class RedissonConfiguration {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + applicationProperties.getHost() + ":" + applicationProperties.getPort())
                .setConnectTimeout(applicationProperties.getConnectTimeOutInMs())
                .setSubscriptionConnectionMinimumIdleSize(applicationProperties.getMinimumIdle())
                .setSubscriptionConnectionPoolSize(applicationProperties.getMaxPoolSize())
                .setIdleConnectionTimeout(applicationProperties.getMaxWaitMillis());
        config.setCodec(StringCodec.INSTANCE);
        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
        return new RedissonSpringCacheManager(redissonClient, config);
    }
}
