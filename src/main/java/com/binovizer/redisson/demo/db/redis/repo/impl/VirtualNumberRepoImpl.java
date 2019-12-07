package com.binovizer.redisson.demo.db.redis.repo.impl;

import com.binovizer.redisson.demo.ApplicationProperties;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import com.binovizer.redisson.demo.db.redis.repo.VirtualNumberRepo;
import com.binovizer.redisson.demo.utils.FreePoolCacheKeyBuilder;
import com.binovizer.redisson.demo.utils.StringSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VirtualNumberRepoImpl implements VirtualNumberRepo {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void save(Long regionId, VirtualNumberType virtualNumberType, List<VirtualNumberRedisEntity> toBeSaved) {
        String key = FreePoolCacheKeyBuilder.buildKey(regionId, virtualNumberType);
        String freePoolCache = applicationProperties.getFreePoolCache();
        RMap<String, String> map = redissonClient.getMap(freePoolCache);
        List<VirtualNumberRedisEntity> entities = find(key);
        entities.addAll(toBeSaved);
        try {
            map.put(key, StringSerializer.serialize(entities));
        } catch (IOException e) {
            log.error("Unable to serialize entity.");
        }
    }

    @Override
    public List<VirtualNumberRedisEntity> find(Long regionId, VirtualNumberType virtualNumberType) {
        String key = FreePoolCacheKeyBuilder.buildKey(regionId, virtualNumberType);
        return find(key);
    }

    @Override
    public List<VirtualNumberRedisEntity> find(String key) {
        List<VirtualNumberRedisEntity> entities = new ArrayList<>();
        String freePoolCache = applicationProperties.getFreePoolCache();
        RMap<String, String> map = redissonClient.getMap(freePoolCache);
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


}
