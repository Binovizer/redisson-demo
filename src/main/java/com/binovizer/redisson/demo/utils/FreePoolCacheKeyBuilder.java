package com.binovizer.redisson.demo.utils;

import com.binovizer.redisson.demo.db.redis.entity.VirtualNumberType;
import lombok.experimental.UtilityClass;

/**
 * The type FreePoolCacheKeyBuilder
 *
 * @author Mohd Nadeem
 */
@UtilityClass
public class FreePoolCacheKeyBuilder {

    public static String buildKey(Long regionId, VirtualNumberType virtualNumberType) {
        return regionId + ":" + virtualNumberType.getCode();
    }

}
