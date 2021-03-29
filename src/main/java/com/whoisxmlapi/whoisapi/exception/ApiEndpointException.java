package com.whoisxmlapi.whoisapi.exception;

public class ApiEndpointException extends BaseException {
    public ApiEndpointException() {
    }

    public ApiEndpointException(String message) {
        super(message);
    }

    public ApiEndpointException(String message, Throwable err) {
        super(message, err);
    }
}
