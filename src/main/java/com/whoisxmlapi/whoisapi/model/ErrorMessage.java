package com.whoisxmlapi.whoisapi.model;

import java.util.Optional;

public class ErrorMessage extends BaseRecord {
    protected String errorCode;
    protected String msg;

    public Optional<String> getErrorCode() {
        boolean isEmpty = this.errorCode == null || this.errorCode.isEmpty();
        return isEmpty ? Optional.empty() : Optional.of(this.errorCode);
    }

    public Optional<String> getMsg() {
        boolean isEmpty = this.msg == null || this.msg.isEmpty();
        return isEmpty ? Optional.empty() : Optional.of(this.msg);
    }
}
