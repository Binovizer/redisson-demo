package com.binovizer.redisson.demo.db.redis.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * The type VirtualNumberType
 *
 * @author Mohd Nadeem
 */
@Getter
@RequiredArgsConstructor
public enum VirtualNumberType {

    UNDEFINED("undefined", 0),
    EXOTEL("exotel", 1),
    OZONTEL("ozontel", 2);

    @JsonValue
    private final String name;
    private final Integer code;

    public static VirtualNumberType fromCode(Integer code){
        Optional<VirtualNumberType> typeMaybe = Arrays.stream(VirtualNumberType.values())
                .filter(virtualNumberType -> virtualNumberType.getCode().equals(code))
                .findFirst();
        return typeMaybe.orElse(VirtualNumberType.UNDEFINED);
    }

    public static VirtualNumberType fromName(String name){
        Optional<VirtualNumberType> typeMaybe = Arrays.stream(VirtualNumberType.values())
                .filter(virtualNumberType -> virtualNumberType.getName().equalsIgnoreCase(name))
                .findFirst();
        return typeMaybe.orElse(VirtualNumberType.UNDEFINED);
    }

}
