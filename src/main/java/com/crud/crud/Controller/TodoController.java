package com.crud.crud.Controller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.data.domain.Pageable;
import com.crud.crud.Entity.TodoEntity;
import com.crud.crud.Enums.StateEnum;
import com.crud.crud.RequestRecord.CreateTodoRecord;
import com.crud.crud.RequestRecord.GetAllTodosRecord;
import com.crud.crud.RequestRecord.UpdateTodoRecord;
import com.crud.crud.Service.TodoServiceImpl;
import com.crud.crud.util.ApiResponse;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoServiceImpl todoService;

    @GetMapping
    public ResponseEntity<Page<TodoEntity>> getAllTodos(@Validated GetAllTodosRecord getAllTodosRecord) {
        Pageable pageable = PageRequest.of(getAllTodosRecord.page(), getAllTodosRecord.size());
        return ResponseEntity.ok(todoService.findAllWithFilters(getAllTodosRecord, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoEntity>> getTodoById(@PathVariable @NotBlank(message = "Id is required") String id) {
        TodoEntity todo = todoService.getTodoById(id);
        ApiResponse<TodoEntity> apiResponse = new ApiResponse<>("Todo retrieved successfully", todo);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<ApiResponse<List<TodoEntity>>> getTodosByState(@PathVariable StateEnum state ) {
        List<TodoEntity> todos = todoService.getTodosByState(state);
        ApiResponse<List<TodoEntity>> apiResponse = new ApiResponse<>("Todos retrieved successfully", todos);
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
        String statePast = todoService.updateTodo(id, todo);
        Set<String> stateUpdate = new HashSet<>();
        stateUpdate.add(todo.state().name());
        stateUpdate.add(statePast);
        ApiResponse<String> apiResponse = new ApiResponse<String>("Todo Update Succefully",String.join(",", stateUpdate));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTodo(@PathVariable @NotBlank(message = "Id is required") String id) {
        String deletedTodoResponse = todoService.deleteTodo(id);
        ApiResponse<String> apiResponse = new ApiResponse<>(deletedTodoResponse);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
    
    
}