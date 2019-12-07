package com.binovizer.redisson.demo.service.impl;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import com.binovizer.redisson.demo.db.redis.repo.UnallocatedPoolRepo;
import com.binovizer.redisson.demo.dto.response.VirtualNumberResponse;
import com.binovizer.redisson.demo.service.UnallocatedPoolService;
import com.binovizer.redisson.demo.utils.FreePoolCacheKeyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type VirtualNumberServiceImpl
 *
 * @author Mohd Nadeem
 */
@Service
public class UnallocatedPoolServiceImpl implements UnallocatedPoolService {

    @Autowired
    private UnallocatedPoolRepo unallocatedPoolRepo;

    @Override
    public VirtualNumberResponse save(Long regionId, VirtualNumberType virtualNumberType,
                                      List<VirtualNumberRedisEntity> entities) {
        String key = FreePoolCacheKeyBuilder.buildKey(regionId, virtualNumberType);
        unallocatedPoolRepo.save(key, entities);
        return VirtualNumberResponse.from(entities);
    }

    @Override
    public List<VirtualNumberRedisEntity> find(Long regionId,
                                               VirtualNumberType virtualNumberType) {
        String key = FreePoolCacheKeyBuilder.buildKey(regionId, virtualNumberType);
        return unallocatedPoolRepo.find(key);
    }

}
