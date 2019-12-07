package com.binovizer.redisson.demo.exception;

import com.binovizer.redisson.demo.dto.response.ApiData;
import com.binovizer.redisson.demo.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The type GenericExceptionHandler
 *
 * @author Mohd Nadeem
 */
@ControllerAdvice
public class GenericExceptionHandler {

    @ResponseBody
    @ExceptionHandler(PoolException.class)
    public BaseResponse<?> handlePoolException(PoolException ex){
        return BaseResponse.failure(ApiData.message(ex.getMessage()));
    }

}
