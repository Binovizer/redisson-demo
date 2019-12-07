package com.binovizer.redisson.demo.service;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import com.binovizer.redisson.demo.dto.response.VirtualNumberResponse;

import java.util.List;

/**
 * The type VirtualNumberService
 *
 * @author Mohd Nadeem
 */
public interface UnallocatedPoolService {

    VirtualNumberResponse save(Long regionId, VirtualNumberType type, List<VirtualNumberRedisEntity> entities);

    List<VirtualNumberRedisEntity> find(Long regionId, VirtualNumberType virtualNumberType);

}
