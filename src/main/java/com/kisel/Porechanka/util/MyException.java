package com.kisel.Porechanka.util;

public class MyException extends RuntimeException {

    public MyException(Throwable e) {
        initCause(e);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(String message) {
        super(message);
    }
}