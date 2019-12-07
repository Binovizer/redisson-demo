package com.binovizer.redisson.demo.service.impl;

import com.binovizer.redisson.demo.constant.ErrorCodes;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import com.binovizer.redisson.demo.dto.request.AddVirtualNumbersRequest;
import com.binovizer.redisson.demo.dto.request.VirtualNumberAllocationRequest;
import com.binovizer.redisson.demo.dto.request.VirtualNumberDeallocationRequest;
import com.binovizer.redisson.demo.dto.response.VirtualNumberResponse;
import com.binovizer.redisson.demo.exception.PoolException;
import com.binovizer.redisson.demo.service.AllocatedPoolService;
import com.binovizer.redisson.demo.service.UnallocatedPoolService;
import com.binovizer.redisson.demo.service.VirtualNumberPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type VirtualNumberServiceImpl
 *
 * @author Mohd Nadeem
 */
@Service
public class VirtualNumberPoolServiceImpl implements VirtualNumberPoolService {

    @Autowired
    private AllocatedPoolService allocatedPoolService;

    @Autowired
    private UnallocatedPoolService unallocatedPoolService;

    @Override
    public VirtualNumberResponse saveVirtualNumbers(AddVirtualNumbersRequest addVirtualNumbersRequest) {
        Long regionId = addVirtualNumbersRequest.getRegionId();
        VirtualNumberType type = addVirtualNumbersRequest.getType();
        List<VirtualNumberRedisEntity> entities = addVirtualNumbersRequest.getVirtualNumbers().stream()
                .map(number -> VirtualNumberRedisEntity.from(number, type, regionId))
                .collect(Collectors.toList());
        return unallocatedPoolService.save(regionId, type, entities);
    }

    @Override
    public VirtualNumberRedisEntity allocate(VirtualNumberAllocationRequest allocationRequest) {
        String phoneNumber = allocationRequest.getPhoneNumber();
        Long virtualNumberRegionId = allocationRequest.getVirtualNumberRegionId();
        VirtualNumberType virtualNumberType = allocationRequest.getVirtualNumberType();

        validateIfAlreadyAllocated(phoneNumber);

        List<VirtualNumberRedisEntity> redisEntities =
                unallocatedPoolService.find(virtualNumberRegionId, virtualNumberType);
        if(CollectionUtils.isEmpty(redisEntities)){
            throw new PoolException(ErrorCodes.POOL_EXHAUSTED);
        }
        VirtualNumberRedisEntity toBeAllocated = redisEntities.remove(0);
        toBeAllocated.setPhoneNumber(phoneNumber);

        unallocatedPoolService.save(virtualNumberRegionId, virtualNumberType, redisEntities);
        allocatedPoolService.save(phoneNumber, toBeAllocated);
        return toBeAllocated;
    }

    @Override
    public VirtualNumberRedisEntity deallocate(VirtualNumberDeallocationRequest deallocationRequest) {
        String phoneNumber = deallocationRequest.getPhoneNumber();
        Long virtualNumberRegionId = deallocationRequest.getVirtualNumberRegionId();
        VirtualNumberType virtualNumberType = deallocationRequest.getVirtualNumberType();

        Optional<VirtualNumberRedisEntity> entityMaybe = allocatedPoolService.find(phoneNumber);
        VirtualNumberRedisEntity toBeDeleted =
                entityMaybe.orElseThrow(() -> new PoolException(ErrorCodes.VIRTUAL_NUMBER_NOT_FOUND));
        toBeDeleted.setPhoneNumber(null);

        List<VirtualNumberRedisEntity> entities =
                unallocatedPoolService.find(virtualNumberRegionId, virtualNumberType);
        entities.add(toBeDeleted);

        allocatedPoolService.remove(phoneNumber);
        unallocatedPoolService.save(virtualNumberRegionId, virtualNumberType, entities);

        return toBeDeleted;
    }

    private void validateIfAlreadyAllocated(String phoneNumber) {
        Optional<VirtualNumberRedisEntity> entityMaybe = allocatedPoolService.find(phoneNumber);
        if(entityMaybe.isPresent()){
            throw new PoolException(ErrorCodes.ALREADY_ALLOCATED);
        }
    }

}
