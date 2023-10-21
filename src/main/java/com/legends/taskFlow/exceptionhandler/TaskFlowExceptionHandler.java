package com.legends.taskFlow.exceptionhandler;

import com.legends.taskFlow.exception.EndDateBeforeStartDateException;
import com.legends.taskFlow.exception.EntityAlreadyExistsException;
import com.legends.taskFlow.exception.NotFoundException;
import com.legends.taskFlow.exception.EntityNotExistsException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class TaskFlowExceptionHandler {

    @ExceptionHandler({EntityNotExistsException.class, EndDateBeforeStartDateException.class})
    public ResponseEntity<String> badRequest(Exception exception) {
        log.error("Exception: " + exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> notFound(Exception exception) {
        log.error("Exception: " + exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<String> conflict(Exception exception) {
        log.error("Exception: " + exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

}
