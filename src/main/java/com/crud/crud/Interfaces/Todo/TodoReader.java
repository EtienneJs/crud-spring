package com.crud.crud.Interfaces.Todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.crud.crud.Entity.TodoEntity;
import com.crud.crud.RequestRecord.GetAllTodosRecord;

public interface TodoReader {
    Page<TodoEntity> findAllWithFilters(GetAllTodosRecord filters, Pageable pageable);
    TodoEntity getTodoById(String id);
}
