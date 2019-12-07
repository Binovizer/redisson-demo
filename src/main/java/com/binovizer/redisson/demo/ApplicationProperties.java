package com.binovizer.redisson.demo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The type ApplicationProperties
 *
 * @author Mohd Nadeem
 */
@Configuration
@Getter
public class ApplicationProperties {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.db.index}")
    private Integer dbIndex;

    @Value("${redis.connect.time.out}")
    private Integer connectTimeOutInMs;

    @Value("${redis.connection.pool.max.size}")
    private Integer maxPoolSize;

    @Value("${redis.connection.pool.min.idle}")
    private Integer minimumIdle;

    @Value("${redis.connection.pool.max.wait.ms}")
    private Integer maxWaitMillis;

    @Value("${redis.virtual.number.free.pool.hash.key}")
    private String freePoolCache;

    @Value("${redis.virtual.number.occupied.pool.hash.key}")
    private String occupiedPoolCache;

}
