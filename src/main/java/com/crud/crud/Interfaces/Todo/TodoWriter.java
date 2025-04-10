package com.crud.crud.Interfaces.Todo;

import com.crud.crud.RequestRecord.CreateTodoRecord;
import com.crud.crud.RequestRecord.UpdateTodoRecord;

public interface TodoWriter {
    String createTodo(CreateTodoRecord todo);
    String updateTodo(String id, UpdateTodoRecord todo);
    String deleteTodo(String id);
}
