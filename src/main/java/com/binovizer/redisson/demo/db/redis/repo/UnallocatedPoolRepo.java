package com.binovizer.redisson.demo.db.redis.repo;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;

import java.util.List;

/**
 * The type VirtualNumberRepo
 *
 * @author Mohd Nadeem
 */
public interface UnallocatedPoolRepo {

    List<VirtualNumberRedisEntity> find(String key);

    void save(String key, List<VirtualNumberRedisEntity> toBeSaved);


}
