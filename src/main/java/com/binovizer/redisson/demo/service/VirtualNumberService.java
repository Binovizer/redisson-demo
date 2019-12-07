package com.binovizer.redisson.demo.service;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import com.binovizer.redisson.demo.dto.request.FindVirtualNumberRequest;
import com.binovizer.redisson.demo.dto.request.VirtualNumberRequest;
import com.binovizer.redisson.demo.dto.response.VirtualNumberResponse;

/**
 * The type VirtualNumberService
 *
 * @author Mohd Nadeem
 */
public interface VirtualNumberService {

    VirtualNumberResponse save(VirtualNumberRequest virtualNumberRequest);

    VirtualNumberRedisEntity find(Long regionId, VirtualNumberType virtualNumberType);
}
