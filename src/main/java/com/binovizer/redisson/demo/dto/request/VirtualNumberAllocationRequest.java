package com.binovizer.redisson.demo.dto.request;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type VirtualNumberAllocationRequest
 *
 * @author Mohd Nadeem
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualNumberAllocationRequest {

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("type")
    private VirtualNumberType virtualNumberType;

    @JsonProperty("region_id")
    private Long virtualNumberRegionId;

}
