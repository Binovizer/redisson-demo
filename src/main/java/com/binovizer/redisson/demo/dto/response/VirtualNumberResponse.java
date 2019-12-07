package com.binovizer.redisson.demo.dto.response;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
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
    private List<String> virtualNumbers;

    @JsonProperty("region_id")
    private Long regionId;

    private VirtualNumberType type;

    @JsonProperty("phone_number")
    private String phoneNumber;

    public static VirtualNumberResponse from(List<String> virtualNumbers, VirtualNumberType type, Long regionId){
        return VirtualNumberResponse.builder()
                .regionId(regionId)
                .virtualNumbers(virtualNumbers)
                .type(type)
                .build();
    }

}
