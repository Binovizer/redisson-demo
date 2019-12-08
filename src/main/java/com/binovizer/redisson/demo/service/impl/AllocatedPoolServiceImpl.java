package com.binovizer.redisson.demo.service.impl;

import com.binovizer.redisson.demo.constant.ErrorCodes;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import com.binovizer.redisson.demo.db.redis.repo.AllocatedPoolRepo;
import com.binovizer.redisson.demo.dto.request.AddVirtualNumbersRequest;
import com.binovizer.redisson.demo.dto.response.VirtualNumberResponse;
import com.binovizer.redisson.demo.exception.PoolException;
import com.binovizer.redisson.demo.service.AllocatedPoolService;
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
public class AllocatedPoolServiceImpl implements AllocatedPoolService {

    @Autowired
    private AllocatedPoolRepo allocatedPoolRepo;

    @Override
    public Optional<VirtualNumberRedisEntity> find(String phoneNumber) {
        return allocatedPoolRepo.find(phoneNumber);
    }

    @Override
    public VirtualNumberRedisEntity save(String phoneNumber, VirtualNumberRedisEntity toBeAllocated) {
        int i = 1/0;
        return allocatedPoolRepo.save(phoneNumber, toBeAllocated);
    }

    @Override
    public void remove(String phoneNumber) {
        allocatedPoolRepo.remove(phoneNumber);
    }
}
