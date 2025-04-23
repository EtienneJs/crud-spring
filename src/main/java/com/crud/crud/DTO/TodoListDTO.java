package com.crud.crud.DTO;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class TodoListDTO {
    private List<TodosMapperDTO> todos;
}
