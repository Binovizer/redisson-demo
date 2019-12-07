package com.binovizer.redisson.demo.web.controller;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import com.binovizer.redisson.demo.dto.request.VirtualNumberRequest;
import com.binovizer.redisson.demo.dto.response.VirtualNumberResponse;
import com.binovizer.redisson.demo.service.VirtualNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type VirtualNumberController
 *
 * @author Mohd Nadeem
 */
@RestController
@RequestMapping("/v1/number")
public class VirtualNumberController {

    @Autowired
    private VirtualNumberService virtualNumberService;

    @PostMapping
    public ResponseEntity<?> createVirtualNumber(@RequestBody VirtualNumberRequest virtualNumberRequest){
        VirtualNumberResponse savedNumber = virtualNumberService.save(virtualNumberRequest);
        return ResponseEntity.ok(savedNumber);
    }

    @GetMapping
    public ResponseEntity<?> findVirtualNumber(@RequestParam("region_id") Long regionId,
                                               @RequestParam("type") String type){
        VirtualNumberType virtualNumberType = VirtualNumberType.fromName(type);
        VirtualNumberRedisEntity found = virtualNumberService.find(regionId, virtualNumberType);
        return ResponseEntity.ok(found);
    }

}
