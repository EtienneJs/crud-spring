package com.crud.crud.RequestRecord;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTodoRecord(
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters") String title,
    @Size(min = 3, max = 200, message = "Description must be between 3 and 100 characters") String description,
    @NotNull(message = "Completed is required") Boolean completed
) {
    
}
