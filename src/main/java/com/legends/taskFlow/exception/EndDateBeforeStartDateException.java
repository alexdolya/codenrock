package com.legends.taskFlow.exception;

public class EndDateBeforeStartDateException extends RuntimeException {
    public EndDateBeforeStartDateException(String message) {
        super(message);
    }
}
