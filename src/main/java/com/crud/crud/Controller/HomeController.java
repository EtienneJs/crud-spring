package com.crud.crud.Controller;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.crud.crud.DTO.TodosMapperDTO;
import com.crud.crud.Entity.TodoEntity;
import com.crud.crud.Enums.StateEnum;
import com.crud.crud.RequestRecord.CreateTodoRecord;
import com.crud.crud.Service.TodoServiceImpl;

@Controller
public class HomeController {
    private final TodoServiceImpl todoService;

    public HomeController(TodoServiceImpl todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/home")
    public String index(Model model) {
        List<TodoEntity> todos = todoService.searchTodos();
        
        TodosMapperDTO backlog_todos = new TodosMapperDTO(StateEnum.BACKLOG, todos.stream().filter(todo -> todo.getState().equals(StateEnum.BACKLOG)).collect(Collectors.toList()));
        TodosMapperDTO in_progress_todos = new TodosMapperDTO(StateEnum.IN_PROGRESS, todos.stream().filter(todo -> todo.getState().equals(StateEnum.IN_PROGRESS)).collect(Collectors.toList()));
        TodosMapperDTO completed_todos = new TodosMapperDTO(StateEnum.COMPLETED, todos.stream().filter(todo -> todo.getState().equals(StateEnum.COMPLETED)).collect(Collectors.toList()));
        TodosMapperDTO not_completed_todos = new TodosMapperDTO(StateEnum.NO_COMPLETED, todos.stream().filter(todo -> todo.getState().equals(StateEnum.NO_COMPLETED)).collect(Collectors.toList()));
        
        List<TodosMapperDTO> todoListDTO = Arrays.asList(backlog_todos, in_progress_todos, completed_todos, not_completed_todos);

        model.addAttribute("todos", todoListDTO);
        
        return "index";
    }
    @PostMapping("/home")
    public String index(Model model, @RequestBody @Validated CreateTodoRecord todo) {
        todoService.createTodo(todo);
        model.addAttribute("todos", todoService.searchTodos());
        return "index";
    }
    
}
