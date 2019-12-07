package com.binovizer.redisson.demo.web.controller;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.binovizer.redisson.demo.dto.request.AddVirtualNumbersRequest;
import com.binovizer.redisson.demo.dto.request.VirtualNumberAllocationRequest;
import com.binovizer.redisson.demo.dto.request.VirtualNumberDeallocationRequest;
import com.binovizer.redisson.demo.dto.response.VirtualNumberResponse;
import com.binovizer.redisson.demo.service.VirtualNumberPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type VirtualNumberController
 *
 * @author Mohd Nadeem
 */
@RestController
@RequestMapping("/v1/number")
public class VirtualNumberController {

    @Autowired
    private VirtualNumberPoolService virtualNumberPoolService;

    @PostMapping
    public ResponseEntity<?> addVirtualNumbers(@RequestBody AddVirtualNumbersRequest addVirtualNumbersRequest){
        VirtualNumberResponse savedNumber = virtualNumberPoolService.saveVirtualNumbers(addVirtualNumbersRequest);
        return ResponseEntity.ok(savedNumber);
    }

    @PostMapping("/allocate")
    public ResponseEntity<?> findVirtualNumber(@RequestBody VirtualNumberAllocationRequest allocationRequest){
        VirtualNumberRedisEntity found = virtualNumberPoolService.allocate(allocationRequest);
        return ResponseEntity.ok(found);
    }

    @PostMapping("/deallocate")
    public ResponseEntity<?> findVirtualNumber(@RequestBody VirtualNumberDeallocationRequest deallocationRequest){
        VirtualNumberRedisEntity found = virtualNumberPoolService.deallocate(deallocationRequest);
        return ResponseEntity.ok(found);
    }

}
