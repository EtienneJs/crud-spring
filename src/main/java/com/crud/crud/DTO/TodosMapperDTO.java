package com.crud.crud.DTO;

import java.util.List;
import com.crud.crud.Entity.TodoEntity;
import com.crud.crud.Enums.StateEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TodosMapperDTO {
    private StateEnum state;
    private List<TodoEntity> todos;
}
