package com.crud.crud.RequestRecord;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.crud.crud.Enums.StateEnum;

public record UpdateTodoRecord(
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters") String title,
    @Size(min = 3, max = 200, message = "Description must be between 3 and 100 characters") String description,
    @NotNull(message = "State is required") StateEnum state,
    @NotNull(message = "End Date is required") String endDate
) {
    
}
