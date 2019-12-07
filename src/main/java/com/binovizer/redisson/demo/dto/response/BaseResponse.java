package com.binovizer.redisson.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type BaseResponse
 *
 * @author Mohd Nadeem
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"success", "data"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {

    private Boolean success;

    private T data;

    public static <T> BaseResponse<T> success(T data){
        return BaseResponse.<T>builder().success(true).data(data).build();
    }

    public static <T> BaseResponse<T> failure(T data) {
        return BaseResponse.<T>builder().success(false).data(data).build();
    }
}
