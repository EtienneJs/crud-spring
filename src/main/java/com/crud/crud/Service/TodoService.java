package com.crud.crud.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.crud.crud.Entity.TodoEntity;
import com.crud.crud.Repository.TodoRepository;
import com.crud.crud.RequestRecord.CreateTodoRecord;
import com.crud.crud.RequestRecord.GetAllTodosRecord;
import com.crud.crud.RequestRecord.UpdateTodoRecord;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MongoTemplate mongoTemplate;

    public Page<TodoEntity> getAllTodos(GetAllTodosRecord getAllTodosRecord) {
        Pageable pageable = PageRequest.of(getAllTodosRecord.page(), getAllTodosRecord.size());

        Query query = new Query();
        if (getAllTodosRecord.title() != null) {
            query.addCriteria(Criteria.where("title").regex(getAllTodosRecord.title()));
        }
        if (getAllTodosRecord.description() != null) {
            query.addCriteria(Criteria.where("description").regex(getAllTodosRecord.description()));
        }
        if (getAllTodosRecord.completed() != null) {
            query.addCriteria(Criteria.where("completed").is(getAllTodosRecord.completed()));
        }
        long total = mongoTemplate.count(query, TodoEntity.class);

        List<TodoEntity> todos = mongoTemplate.find(query, TodoEntity.class);
        return new PageImpl<>(todos, pageable, total);
    }

    public TodoEntity getTodoById(String id) {
        return todoRepository.findById(id).orElse(null);
    }

    public String createTodo(CreateTodoRecord todo) {
        TodoEntity newTodo = new TodoEntity(todo.title(), todo.description());
        todoRepository.save(newTodo);
        return "Todo created successfully";
    }

    public String updateTodo(String id, UpdateTodoRecord todo) {
        todoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Todo not found"));
            
        
        TodoEntity updatedTodo = TodoEntity.builder()
            .id(id)
            .title(todo.title())
            .description(todo.description())
            .completed(todo.completed())
            .build();
        
        todoRepository.save(updatedTodo);
        return "Todo updated successfully";
    }

    public String deleteTodo(String id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found");
        }
        todoRepository.deleteById(id);
        return "Todo deleted successfully";
    }
}