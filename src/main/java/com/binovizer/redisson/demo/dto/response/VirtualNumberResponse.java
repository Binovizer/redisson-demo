package com.binovizer.redisson.demo.dto.response;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberRedisEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type VirtualNumberRequest
 *
 * @author Mohd Nadeem
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VirtualNumberResponse {

    @JsonProperty("virtual_numbers")
    private List<VirtualNumberRedisEntity> virtualNumbers;

    public static VirtualNumberResponse from(List<VirtualNumberRedisEntity> virtualNumbers){
        return VirtualNumberResponse.builder()
                .virtualNumbers(virtualNumbers)
                .build();
    }

}
