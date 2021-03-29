package com.whoisxmlapi.whoisapi.model;

import com.whoisxmlapi.whoisapi.util.OptionalBuilder;

import java.util.Optional;

public class WhoisRecord extends BaseWhoisRecord {
    protected String domainName;
    protected String domainAvailability;
    protected String contactEmail;
    protected String domainNameExt;
    protected String estimatedDomainAge;
    protected String dataError;

    public Optional<String> getDomainName() {
        return OptionalBuilder.buildOptionalString(this.domainName);
    }

    public Optional<String> getDomainAvailability() {
        return OptionalBuilder.buildOptionalString(this.domainAvailability);
    }

    public Optional<String> getContactEmail() {
        return OptionalBuilder.buildOptionalString(this.contactEmail);
    }

    public Optional<String> getDomainNameExt() {
        return OptionalBuilder.buildOptionalString(this.domainNameExt);
    }

    public Optional<String> getEstimatedDomainAge() {
        return OptionalBuilder.buildOptionalString(this.estimatedDomainAge);
    }

    public Optional<String> getDataError() {
        return OptionalBuilder.buildOptionalString(this.dataError);
    }
}
