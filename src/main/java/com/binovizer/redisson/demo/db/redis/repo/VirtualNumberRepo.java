package com.binovizer.redisson.demo.db.redis.repo;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;

import java.util.List;
import java.util.Map;

/**
 * The type VirtualNumberRepo
 *
 * @author Mohd Nadeem
 */
public interface VirtualNumberRepo {

    void save(Long regionId, VirtualNumberType virtualNumberType, List<VirtualNumberRedisEntity> toBeSaved);

    List<VirtualNumberRedisEntity> find(Long regionId, VirtualNumberType virtualNumberType);

    List<VirtualNumberRedisEntity> find(String key);

}
