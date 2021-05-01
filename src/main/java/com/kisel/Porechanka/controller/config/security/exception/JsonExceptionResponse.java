package com.kisel.Porechanka.controller.config.security.exception;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("JsonExceptionResponse")
public class JsonExceptionResponse {

    private Integer error;
    private String message;

    public JsonExceptionResponse(Integer error, String message) {
        this.error = error;
        this.message = message;
    }

    public Integer getError() {
        return error;
    }

    public JsonExceptionResponse setError(Integer error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonExceptionResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
