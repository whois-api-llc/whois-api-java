package com.whoisxmlapi.whoisapi.exception;

public class InvalidRequestParameterException extends BaseException {
    public InvalidRequestParameterException() {
    }

    public InvalidRequestParameterException(String message) {
        super(message);
    }

    public InvalidRequestParameterException(String message, Throwable err) {
        super(message, err);
    }
}
