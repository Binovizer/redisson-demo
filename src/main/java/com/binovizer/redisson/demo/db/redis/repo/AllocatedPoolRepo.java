package com.binovizer.redisson.demo.db.redis.repo;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;

import java.util.Optional;

/**
 * The type VirtualNumberRepo
 *
 * @author Mohd Nadeem
 */
public interface AllocatedPoolRepo {

    VirtualNumberRedisEntity save(String phoneNumber, VirtualNumberRedisEntity entity);

    Optional<VirtualNumberRedisEntity> find(String phoneNumber);

    void remove(String phoneNumber);
}
