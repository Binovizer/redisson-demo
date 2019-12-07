package com.binovizer.redisson.demo.service.impl;

import com.binovizer.redisson.demo.constant.ErrorCodes;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import com.binovizer.redisson.demo.db.redis.repo.VirtualNumberRepo;
import com.binovizer.redisson.demo.dto.request.VirtualNumberRequest;
import com.binovizer.redisson.demo.dto.response.VirtualNumberResponse;
import com.binovizer.redisson.demo.exception.PoolException;
import com.binovizer.redisson.demo.service.VirtualNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type VirtualNumberServiceImpl
 *
 * @author Mohd Nadeem
 */
@Service
public class VirtualNumberServiceImpl implements VirtualNumberService {

    @Autowired
    private VirtualNumberRepo virtualNumberRepo;

    @Override
    public VirtualNumberResponse save(VirtualNumberRequest virtualNumberRequest) {
        Long regionId = virtualNumberRequest.getRegionId();
        VirtualNumberType type = virtualNumberRequest.getType();
        List<VirtualNumberRedisEntity> entities = virtualNumberRequest.getVirtualNumbers().stream()
                .map(number -> VirtualNumberRedisEntity.from(number, type, regionId))
                .collect(Collectors.toList());
        virtualNumberRepo.save(regionId, type, entities);
        return VirtualNumberResponse.from(virtualNumberRequest.getVirtualNumbers(), type, regionId);
    }

    @Override
    public VirtualNumberRedisEntity find(Long regionId, VirtualNumberType virtualNumberType) {
        List<VirtualNumberRedisEntity> entities = virtualNumberRepo.find(regionId, virtualNumberType);
        if(CollectionUtils.isEmpty(entities)){
            throw new PoolException(ErrorCodes.NOT_FOUND);
        }
        return entities.get(0);
    }

}
