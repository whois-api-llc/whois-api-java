package com.whoisxmlapi.whoisapi.exception;

import com.whoisxmlapi.whoisapi.model.ErrorMessage;

public class ApiErrorMessageException extends BaseException {

    ErrorMessage errorMessage;

    public ApiErrorMessageException() {
    }

    public ApiErrorMessageException(String message) {
        super(message);
    }

    public ApiErrorMessageException(String message, Throwable err) {
        super(message, err);
    }

    public ApiErrorMessageException(String message, ErrorMessage errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessageEntity() {
        return errorMessage;
    }

    public String getApiErrorCode() {
        if (this.errorMessage != null && this.errorMessage.getErrorCode().isPresent()) {
            return this.errorMessage.getErrorCode().get();
        }
        return "";
    }

    public String getApiErrorMessage() {
        if (this.errorMessage != null && this.errorMessage.getMsg().isPresent()) {
            return this.errorMessage.getMsg().get();
        }
        return "";
    }
}
