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
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * The type VirtualNumberServiceImpl
 *
 * @author Mohd Nadeem
 */
@Service
@Slf4j
public class VirtualNumberPoolServiceImpl implements VirtualNumberPoolService {

    private static final String REDIS_LOCK = "redis_lock";

    @Autowired
    private RedissonClient redissonClient;

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

        VirtualNumberRedisEntity toBeAllocated = null;
        RLock rLock = redissonClient.getFairLock(REDIS_LOCK);
        try {
            boolean locked = rLock.tryLock(100, 10, TimeUnit.SECONDS);
            if(locked) {
                log.info("Redis is locked.");
                List<VirtualNumberRedisEntity> redisEntities =
                        unallocatedPoolService.find(virtualNumberRegionId, virtualNumberType);
                if(CollectionUtils.isEmpty(redisEntities)){
                    throw new PoolException(ErrorCodes.POOL_EXHAUSTED);
                }
                toBeAllocated = redisEntities.remove(0);
                toBeAllocated.setPhoneNumber(phoneNumber);
                unallocatedPoolService.save(virtualNumberRegionId, virtualNumberType, redisEntities);
                allocatedPoolService.save(phoneNumber, toBeAllocated);
            }
        } catch (InterruptedException e) {
            log.error("Unable to take lock on redis.", e);
            throw new PoolException(ErrorCodes.REDIS_LOCKING_FAILED);
        } finally {
            log.info("Releasing lock on redis.");
            rLock.unlock();
        }
        return toBeAllocated;
    }

    @Override
    public VirtualNumberRedisEntity deallocate(VirtualNumberDeallocationRequest deallocationRequest) {
        String phoneNumber = deallocationRequest.getPhoneNumber();
        Long virtualNumberRegionId = deallocationRequest.getVirtualNumberRegionId();
        VirtualNumberType virtualNumberType = deallocationRequest.getVirtualNumberType();

        VirtualNumberRedisEntity toBeDeleted = null;
        RLock rLock = redissonClient.getFairLock(REDIS_LOCK);
        try {
            boolean locked = rLock.tryLock(100, 10, TimeUnit.SECONDS);
            if(locked){
                log.info("Redis is locked.");
                Optional<VirtualNumberRedisEntity> entityMaybe = allocatedPoolService.find(phoneNumber);
                toBeDeleted =
                        entityMaybe.orElseThrow(() -> new PoolException(ErrorCodes.VIRTUAL_NUMBER_NOT_FOUND));
                toBeDeleted.setPhoneNumber(null);
                List<VirtualNumberRedisEntity> entities =
                        unallocatedPoolService.find(virtualNumberRegionId, virtualNumberType);
                entities.add(toBeDeleted);
                allocatedPoolService.remove(phoneNumber);
                unallocatedPoolService.save(virtualNumberRegionId, virtualNumberType, entities);
            }
        } catch (InterruptedException e) {
            log.error("Unable to take lock on redis.", e);
            throw new PoolException(ErrorCodes.REDIS_LOCKING_FAILED);
        } finally {
            log.info("Releasing lock on redis.");
            rLock.unlock();
        }
        return toBeDeleted;
    }

    private void validateIfAlreadyAllocated(String phoneNumber) {
        Optional<VirtualNumberRedisEntity> entityMaybe = allocatedPoolService.find(phoneNumber);
        if(entityMaybe.isPresent()){
            throw new PoolException(ErrorCodes.ALREADY_ALLOCATED);
        }
    }

}
