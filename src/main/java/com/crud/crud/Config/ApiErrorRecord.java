package com.crud.crud.Config;

import java.time.LocalDateTime;

public record ApiErrorRecord(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path
) {
    
}
