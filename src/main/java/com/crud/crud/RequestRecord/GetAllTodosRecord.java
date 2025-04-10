package com.crud.crud.RequestRecord;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


public record GetAllTodosRecord(
    @Size(min = 0, max = 100, message = "Title must be between 3 and 100 characters") String title,
    @Size(min = 0, max = 200, message = "Description must be between 3 and 100 characters") String description,
    Boolean completed,
    @Min(value = 0, message = "Page must be greater than 0") Integer page,
    @Min(value = 1, message = "Size must be greater than 0") Integer size
) {
    
}
