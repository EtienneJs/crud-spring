package com.crud.crud.Entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.crud.crud.Enums.StateEnum;

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
    private String createdAt;
    private String endDate;
    private StateEnum state;

    public TodoEntity(String title, String description, String endDate, String createdAt) {
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.state = StateEnum.BACKLOG;
    }
}
