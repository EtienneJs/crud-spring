package com.crud.crud.Enums;

public enum StateEnum {
    BACKLOG("Backlog"),
    NO_COMPLETED("No Completed"),
    COMPLETED("Completed"),
    IN_PROGRESS("In Progress"),
    ALL("All");

    private String value;

    StateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
