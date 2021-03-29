package com.whoisxmlapi.whoisapi.model;

import com.whoisxmlapi.whoisapi.util.OptionalBuilder;

import java.util.Optional;

public class RegistryData extends BaseWhoisRecord {
    protected String status;
    protected Integer parseCode;
    protected String custom1FieldName;
    protected String custom1FieldValue;
    protected String custom2FieldName;
    protected String custom2FieldValue;
    protected String custom3FieldName;
    protected String custom3FieldValue;
    protected String registrarName;
    protected String whoisServer;
    protected String referralURL;
    protected String createdDateNormalized;
    protected String updatedDateNormalized;
    protected String expiresDateNormalized;

    public Optional<String> getStatus() {
        return OptionalBuilder.buildOptionalString(status);
    }

    public Optional<Integer> getParseCode() {
        return OptionalBuilder.buildOptionalInt(parseCode);
    }

    public Optional<String> getCustom1FieldName() {
        return OptionalBuilder.buildOptionalString(custom1FieldName);
    }

    public Optional<String> getCustom1FieldValue() {
        return OptionalBuilder.buildOptionalString(custom1FieldValue);
    }

    public Optional<String> getCustom2FieldName() {
        return OptionalBuilder.buildOptionalString(custom2FieldName);
    }

    public Optional<String> getCustom2FieldValue() {
        return OptionalBuilder.buildOptionalString(custom2FieldValue);
    }

    public Optional<String> getCustom3FieldName() {
        return OptionalBuilder.buildOptionalString(custom3FieldName);
    }

    public Optional<String> getCustom3FieldValue() {
        return OptionalBuilder.buildOptionalString(custom3FieldValue);
    }

    public Optional<String> getRegistrarName() {
        return OptionalBuilder.buildOptionalString(registrarName);
    }

    public Optional<String> getWhoisServer() {
        return OptionalBuilder.buildOptionalString(whoisServer);
    }

    public Optional<String> getReferralURL() {
        return OptionalBuilder.buildOptionalString(referralURL);
    }

    public Optional<String> getCreatedDateNormalized() {
        return OptionalBuilder.buildOptionalString(createdDateNormalized);
    }

    public Optional<String> getUpdatedDateNormalized() {
        return OptionalBuilder.buildOptionalString(updatedDateNormalized);
    }

    public Optional<String> getExpiresDateNormalized() {
        return OptionalBuilder.buildOptionalString(expiresDateNormalized);
    }
}
