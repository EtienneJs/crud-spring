package com.crud.crud.Service;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.crud.Entity.TodoEntity;
import com.crud.crud.Exceptions.TodoNotFoundException;
import com.crud.crud.Interfaces.Todo.TodoService;
import com.crud.crud.Repository.TodoRepository;
import com.crud.crud.RequestRecord.CreateTodoRecord;
import com.crud.crud.RequestRecord.GetAllTodosRecord;
import com.crud.crud.RequestRecord.UpdateTodoRecord;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final MongoTemplate mongoTemplate;
    @Override
    public Page<TodoEntity> findAllWithFilters(GetAllTodosRecord filters, Pageable pageable) {
        Query query = new Query().with(pageable);
        
        if (filters.title() != null && !filters.title().isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(filters.title(), "i"));
        }
        
        if (filters.description() != null && !filters.description().isEmpty()) {
            query.addCriteria(Criteria.where("description").regex(filters.description(), "i"));
        }
        
        if (filters.completed() != null) {
            query.addCriteria(Criteria.where("completed").is(filters.completed()));
        }

        return PageableExecutionUtils.getPage(
            mongoTemplate.find(query, TodoEntity.class),
            pageable,
            () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), TodoEntity.class)
        );
    }

    @Override
    public TodoEntity getTodoById(String id) {
        return todoRepository.findById(id)
            .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Transactional
    public String createTodo(CreateTodoRecord todo) {
        TodoEntity newTodo = new TodoEntity(todo.title(), todo.description());
        todoRepository.save(newTodo);
        return "Todo created successfully";
    }

    @Transactional
    public String updateTodo(String id, UpdateTodoRecord todo) {
        todoRepository.findById(id)
            .orElseThrow(() -> new TodoNotFoundException("Todo not found"));
            
        
        TodoEntity updatedTodo = TodoEntity.builder()
            .id(id)
            .title(todo.title())
            .description(todo.description())
            .completed(todo.completed())
            .build();
        
        todoRepository.save(updatedTodo);
        return "Todo updated successfully";
    }

    @Transactional
    public String deleteTodo(String id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo not found");
        }
        todoRepository.deleteById(id);
        return "Todo deleted successfully";
    }
}