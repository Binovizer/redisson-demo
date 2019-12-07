package com.binovizer.redisson.demo.db.redis.repo.impl;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.repo.UnallocatedPoolRepo;
import com.binovizer.redisson.demo.utils.StringSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type VirtualNumberRepoImpl
 *
 * @author Mohd Nadeem
 */
@Slf4j
@Repository
public class UnallocatedPoolRepoImpl implements UnallocatedPoolRepo {

    @Value("${redis.virtual.number.free.pool.hash.key}")
    private String unallocatedPoolCacheHashKey;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<VirtualNumberRedisEntity> find(String key) {
        List<VirtualNumberRedisEntity> entities = new ArrayList<>();
        RMap<String, String> map = redissonClient.getMap(unallocatedPoolCacheHashKey);
        String entitiesString = map.get(key);
        if(!StringUtils.isEmpty(entitiesString)){
            try {
                entities = StringSerializer.deserialize(entitiesString,
                        new TypeReference<List<VirtualNumberRedisEntity>>() {});
            } catch (IOException e) {
                log.error("Unable to deserialize redis entity. entity : {}", entitiesString);
            }
        }
        return entities;
    }

    @Override
    public void save(String key, List<VirtualNumberRedisEntity> toBeSaved) {
        RMap<String, String> map = redissonClient.getMap(unallocatedPoolCacheHashKey);
        try {
            map.fastPut(key, StringSerializer.serialize(toBeSaved));
        } catch (IOException e) {
            log.error("Unable to serialize entity.", e);
        }
    }

}
