package com.binovizer.redisson.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type ApiData
 *
 * @author Mohd Nadeem
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiData {

  private String headerMessage;
  private String message;

  public static ApiData message(String message) {
    return ApiData.builder().message(message).build();
  }
}
