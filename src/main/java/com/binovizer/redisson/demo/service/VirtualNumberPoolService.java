package com.binovizer.redisson.demo.service;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.dto.request.AddVirtualNumbersRequest;
import com.binovizer.redisson.demo.dto.request.VirtualNumberAllocationRequest;
import com.binovizer.redisson.demo.dto.request.VirtualNumberDeallocationRequest;
import com.binovizer.redisson.demo.dto.response.VirtualNumberResponse;

/**
 * The type VirtualNumberService
 *
 * @author Mohd Nadeem
 */
public interface VirtualNumberPoolService {

    VirtualNumberResponse saveVirtualNumbers(AddVirtualNumbersRequest addVirtualNumbersRequest);

    VirtualNumberRedisEntity allocate(VirtualNumberAllocationRequest allocationRequest);

    VirtualNumberRedisEntity deallocate(VirtualNumberDeallocationRequest deallocationRequest);

}
