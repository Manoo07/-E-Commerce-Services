package com.ecommerce.amazon.common.util;

import com.ecommerce.amazon.common.dto.ApiResponse;

import java.time.LocalDateTime;

public class ResponseBuilder {

    private ResponseBuilder(){}

    public static <T> ApiResponse<T> success(
            String message,
            T data
    ){
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse<Object> error(
            String message,
            Object errors
    ){
        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .success(false)
                .message(message)
                .errors(errors)
                .build();
    }
}
