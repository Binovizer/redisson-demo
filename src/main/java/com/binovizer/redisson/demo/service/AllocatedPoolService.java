package com.binovizer.redisson.demo.service;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;

import java.util.Optional;

/**
 * The type VirtualNumberService
 *
 * @author Mohd Nadeem
 */
public interface AllocatedPoolService {

    Optional<VirtualNumberRedisEntity> find(String phoneNumber);

    VirtualNumberRedisEntity save(String phoneNumber, VirtualNumberRedisEntity toBeAllocated);

    void remove(String phoneNumber);

}
