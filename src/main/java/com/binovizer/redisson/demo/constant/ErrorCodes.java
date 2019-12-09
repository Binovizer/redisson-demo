package com.binovizer.redisson.demo.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The type ErrorCode
 *
 * @author Mohd Nadeem
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCodes {

    SOMETHING_WENT_WRONG("5xx", "Something went wrong."),
    NOT_FOUND("404", "Resource not found."),
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found."),
    POOL_EXHAUSTED("POOL_EXHAUSTED", "Pool exhausted. No available resource."),
    ALREADY_ALLOCATED("ALREADY_ALLOCATED", "A virtual number is already allocated to this phone number."),
    REGION_NOT_FOUND("REGION_NOT_FOUND", "Virtual Number Region not found."),
    FREE_POOL_SAVE_FAILED("FREE_POOL_SAVE_FAILED", "Failed to save entities to free pool."),
    REDIS_LOCKING_FAILED("REDIS_LOCKING_FAILED", "Failed to take lock on redis."),
    OCCUPIED_POOL_SAVE_FAILED("OCCUPIED_POOL_SAVE_FAILED", "Failed to save entities to free pool."),
    OCCUPIED_POOL_REMOVE_FAILED("OCCUPIED_POOL_REMOVE_FAILED", "Failed to remove entity from occupied pool."),
    VIRTUAL_NUMBER_NOT_FOUND("VIRTUAL_NUMBER_NOT_FOUND", "No virtual number assigned to this phone number.");

    private final String errorCode;
    private final String message;
}
