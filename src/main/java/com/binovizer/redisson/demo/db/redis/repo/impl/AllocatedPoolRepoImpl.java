package com.binovizer.redisson.demo.db.redis.repo.impl;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.repo.AllocatedPoolRepo;
import com.binovizer.redisson.demo.utils.StringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;

/**
 * The type VirtualNumberRepoImpl
 *
 * @author Mohd Nadeem
 */
@Slf4j
@Repository
public class AllocatedPoolRepoImpl implements AllocatedPoolRepo {

    @Value("${redis.virtual.number.occupied.pool.hash.key}")
    private String allocatedPoolCacheHashKey;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public VirtualNumberRedisEntity save(String phoneNumber, VirtualNumberRedisEntity entity) {
        RMap<String, String> map = redissonClient.getMap(allocatedPoolCacheHashKey);
        try {
            map.fastPut(phoneNumber, StringSerializer.serialize(entity));
        } catch (IOException e) {
            log.error("Unable to serialize entity.");
        }
        return entity;
    }

    @Override
    public Optional<VirtualNumberRedisEntity> find(String key) {
        VirtualNumberRedisEntity redisEntity = null;
        RMap<String, String> map = redissonClient.getMap(allocatedPoolCacheHashKey);
        try {
            String entityString = map.get(key);
            if(!StringUtils.isEmpty(entityString)){
                redisEntity = StringSerializer.deserialize(entityString, VirtualNumberRedisEntity.class);
            }
        } catch (IOException e) {
            log.error("Unable to serialize entity.");
        }
        return Optional.ofNullable(redisEntity);
    }

    @Override
    public void remove(String phoneNumber) {
        RMap<String, String> map = redissonClient.getMap(allocatedPoolCacheHashKey);
        map.fastRemove(phoneNumber);
    }


}
