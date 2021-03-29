package com.whoisxmlapi.whoisapi.exception;

public class NetworkException extends BaseException {
    public NetworkException() {
    }

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable err) {
        super(message, err);
    }
}
