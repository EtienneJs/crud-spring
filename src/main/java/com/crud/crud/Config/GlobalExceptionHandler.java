package com.crud.crud.Config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crud.crud.Config.Errors.EntityNotFoundException;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorRecord> handleRuntime(RuntimeException ex) {
        ApiErrorRecord apiErrorRecord = new ApiErrorRecord(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", ex.getMessage(), "/todos");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorRecord);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorRecord> handleNotFound(EntityNotFoundException ex) {
        ApiErrorRecord apiErrorRecord = new ApiErrorRecord(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage(), "/todos");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorRecord);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorRecord> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();   
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        
        String jsonErrors;
        try {
            jsonErrors = objectMapper.writeValueAsString(errors);
        } catch (Exception e) {
            jsonErrors = errors.toString(); // fallback to toString if JSON conversion fails
        }
        
        ApiErrorRecord apiErrorRecord = new ApiErrorRecord(
            LocalDateTime.now(), 
            HttpStatus.BAD_REQUEST.value(), 
            "Bad Request", 
            jsonErrors, 
            "/todos"
        );
        return ResponseEntity.badRequest().body(apiErrorRecord);
    }
}