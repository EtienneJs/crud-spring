package com.crud.crud.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.crud.Entity.TodoEntity;
import com.crud.crud.Enums.StateEnum;

public interface TodoRepository extends MongoRepository<TodoEntity, String> {
    List<TodoEntity> findByState(StateEnum  state);
}
