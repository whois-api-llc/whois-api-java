package com.whoisxmlapi.whoisapi.exception;

public class UnparsableRecordException extends BaseException {
    public UnparsableRecordException() {
    }

    public UnparsableRecordException(String message) {
        super(message);
    }

    public UnparsableRecordException(String message, Throwable err) {
        super(message, err);
    }
}
