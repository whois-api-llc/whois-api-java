package com.whoisxmlapi.whoisapi.exception;

public class BaseException extends Exception {
    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable err) {
        super(message, err);
    }
}
