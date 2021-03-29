package com.whoisxmlapi.whoisapi.exception;

public class ApiAuthorizationException extends BaseException {
    public ApiAuthorizationException() {
    }

    public ApiAuthorizationException(String message) {
        super(message);
    }

    public ApiAuthorizationException(String message, Throwable err) {
        super(message, err);
    }
}
