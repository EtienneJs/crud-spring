package com.crud.crud.RequestRecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTodoRecord(
    @NotBlank(message = "Title is required") @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters") String title,
    @NotBlank(message = "Description is required") @Size(min = 3, max = 200, message = "Description must be between 3 and 100 characters") String description
) {
    
}
