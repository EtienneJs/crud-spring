package com.crud.crud.Repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.crud.crud.Entity.TodoEntity;

public interface TodoRepository extends MongoRepository<TodoEntity, String> {
    @Query("{'title': ?0}")
    TodoEntity findByTitle(String title);

    @Query("{'completed': ?0}")
    List<TodoEntity> findByCompleted(boolean completed);

    @Query("{'title': {'$regex': ?0}}")
    List<TodoEntity> findByTitleRegex(String title);

    @Query("{'description': {'$regex': ?0}}")
    List<TodoEntity> findByDescriptionRegex(String description);
}
