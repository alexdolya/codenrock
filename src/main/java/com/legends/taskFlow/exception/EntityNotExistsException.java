package com.legends.taskFlow.exception;

public class EntityNotExistsException extends RuntimeException {
    public EntityNotExistsException(String message) {
        super(message);
    }
}
