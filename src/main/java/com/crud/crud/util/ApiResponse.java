package com.crud.crud.util;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
