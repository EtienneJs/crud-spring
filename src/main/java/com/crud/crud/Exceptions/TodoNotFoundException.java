package com.crud.crud.Exceptions;


public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(String id) {
        super("Todo not found with id: " + id);
    }
}
