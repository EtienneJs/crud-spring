package com.crud.crud.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "todos")
public class TodoEntity {
    @Id
    private String id;
    private String title;
    private String description;

    private boolean completed;

    public TodoEntity(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }
}
