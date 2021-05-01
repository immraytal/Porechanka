package com.kisel.Porechanka.controller.config.security.exception;

import com.kisel.Porechanka.util.MyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<JsonExceptionResponse> handleJsonProcessingException(JsonProcessingException e) {
        LOG.error("Couldn't read json", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new JsonExceptionResponse(500, "Couldn't read json, " + getTime() + ",  " + e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<JsonExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        LOG.error("Entity not found", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new JsonExceptionResponse(500, "Incorrect entity id, can't get object by id, " + getTime() + ",  " + e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<JsonExceptionResponse> handleValidationException(ValidationException e) {
        LOG.error("Not valid", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new JsonExceptionResponse(500, "Validation error, " + getTime() + ",  " + e.getMessage()));
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<JsonExceptionResponse> handleNoResultException(NoResultException e) {
        LOG.error("Not found in database", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new JsonExceptionResponse(500, "Not found in database, " + getTime() + ",  " + e.getMessage()));
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity<JsonExceptionResponse> handleMyException(MyException e) {
        LOG.error("Service exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new JsonExceptionResponse(500, "Exception in service, see logs, " + getTime() + ",  " + e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonExceptionResponse> handleException(Exception e) {
        LOG.error("Unchecked exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new JsonExceptionResponse(500, "Unchecked exception, see logs, " + getTime() + ",  " + e.getMessage()));
    }

    private String getTime() {
        StringBuilder time = new StringBuilder();
        time.append(LocalDate.now());
        time.append("  ");
        time.append(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return time.toString();
    }

}
