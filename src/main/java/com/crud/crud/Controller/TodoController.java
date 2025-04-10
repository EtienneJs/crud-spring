package com.crud.crud.Controller;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.crud.Entity.TodoEntity;
import com.crud.crud.RequestRecord.CreateTodoRecord;
import com.crud.crud.RequestRecord.GetAllTodosRecord;
import com.crud.crud.RequestRecord.UpdateTodoRecord;
import com.crud.crud.Service.TodoService;
import com.crud.crud.util.ApiResponse;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<Page<TodoEntity>> getAllTodos(@Validated GetAllTodosRecord getAllTodosRecord) {
        return ResponseEntity.ok(todoService.getAllTodos(getAllTodosRecord));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoEntity>> getTodoById(@PathVariable @NotBlank(message = "Id is required") String id) {
        TodoEntity todo = todoService.getTodoById(id);
        ApiResponse<TodoEntity> apiResponse = new ApiResponse<>("Todo retrieved successfully", todo);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createTodo(@RequestBody @Validated CreateTodoRecord todo) {
        String createdTodoResponse = todoService.createTodo(todo);
        ApiResponse<String> apiResponse = new ApiResponse<>(createdTodoResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateTodo(@PathVariable String id, @RequestBody @Validated UpdateTodoRecord todo) {
        String updatedTodoResponse = todoService.updateTodo(id, todo);
        ApiResponse<String> apiResponse = new ApiResponse<>(updatedTodoResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTodo(@PathVariable @NotBlank(message = "Id is required") String id) {
        String deletedTodoResponse = todoService.deleteTodo(id);
        ApiResponse<String> apiResponse = new ApiResponse<>(deletedTodoResponse);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
    
    
}