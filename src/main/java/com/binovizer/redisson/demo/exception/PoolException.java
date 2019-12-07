package com.binovizer.redisson.demo.exception;

import com.binovizer.redisson.demo.constant.ErrorCodes;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type PoolException
 *
 * @author Mohd Nadeem
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PoolException extends RuntimeException {

    private ErrorCodes errorCodes;

    public PoolException(String message) {
        super(message);
    }

    public PoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolException(ErrorCodes errorCodes, Throwable cause) {
        super(errorCodes.getMessage(), cause);
        this.errorCodes = errorCodes;
    }

    public PoolException(ErrorCodes errorCodes) {
        this.errorCodes = errorCodes;
    }

    @Override
    public String getMessage() {
        return String.format(
                "Pool exception occurred. message ::" + " %s, error code :: %s",
                errorCodes.getMessage(), errorCodes.getErrorCode());
    }
}
