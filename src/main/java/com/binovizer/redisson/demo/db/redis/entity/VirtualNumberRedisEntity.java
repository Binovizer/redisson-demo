package com.binovizer.redisson.demo.db.redis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type VirtualNumberEntity
 *
 * @author Mohd Nadeem
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VirtualNumberRedisEntity implements Serializable {

    @JsonProperty("virtual_number")
    private String virtualNumber;

    @JsonProperty("virtual_number_region_id")
    private Long regionId;

    private VirtualNumberType type;

    @JsonProperty("phone_number")
    private String phoneNumber;

    public static VirtualNumberRedisEntity from(String virtualNumber, VirtualNumberType type, Long regionId) {
        return VirtualNumberRedisEntity.builder()
                .virtualNumber(virtualNumber)
                .regionId(regionId)
                .type(type)
                .build();
    }

}
