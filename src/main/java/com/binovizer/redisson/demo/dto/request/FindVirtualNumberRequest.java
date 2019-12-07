package com.binovizer.redisson.demo.dto.request;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * The type VirtualNumberRequest
 *
 * @author Mohd Nadeem
 */
@Data
public class FindVirtualNumberRequest {

    @JsonProperty("region_id")
    private Long regionId;

    private VirtualNumberType type;

}
