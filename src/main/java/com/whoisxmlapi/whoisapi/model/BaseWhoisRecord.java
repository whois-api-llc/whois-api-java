package com.whoisxmlapi.whoisapi.model;

import com.whoisxmlapi.whoisapi.util.DateParser;
import com.whoisxmlapi.whoisapi.util.OptionalBuilder;

import java.time.ZonedDateTime;
import java.util.Optional;

public abstract class BaseWhoisRecord extends BaseRecord {
    protected Audit audit;
    protected AdministrativeContact administrativeContact;
    protected BillingContact billingContact;
    protected NameServers nameServers;
    protected Registrant registrant;
    protected RegistryData registryData;
    protected TechnicalContact technicalContact;
    protected ZoneContact zoneContact;
    protected String createdDate;
    protected String updatedDate;
    protected String expiresDate;
    protected String rawText;
    protected String header;
    protected String strippedText;
    protected String footer;

    public Audit getAudit() {
        return this.audit;
    }

    public AdministrativeContact getAdministrativeContact() {
        return administrativeContact;
    }

    public BillingContact getBillingContact() {
        return billingContact;
    }

    public NameServers getNameServers() {
        return nameServers;
    }

    public Registrant getRegistrant() {
        return registrant;
    }

    public RegistryData getRegistryData() {
        return registryData;
    }

    public TechnicalContact getTechnicalContact() {
        return technicalContact;
    }

    public ZoneContact getZoneContact() {
        return zoneContact;
    }

    public Optional<String> getCreatedDateRawString() {
        return OptionalBuilder.buildOptionalString(this.createdDate);
    }

    public Optional<String> getUpdatedDateRawString() {
        return OptionalBuilder.buildOptionalString(this.updatedDate);
    }

    public Optional<String> getExpiresDateRawString() {
        return OptionalBuilder.buildOptionalString(this.expiresDate);
    }

    public Optional<ZonedDateTime> getCreatedDate() {
        Optional<String> date = this.getCreatedDateRawString();
        if (date.isPresent()) {
            return this.buildDateTimeObject(date.get());
        }
        return Optional.empty();
    }

    public Optional<ZonedDateTime> getUpdatedDate() {
        Optional<String> date = this.getUpdatedDateRawString();
        if (date.isPresent()) {
            return this.buildDateTimeObject(date.get());
        }
        return Optional.empty();
    }

    public Optional<ZonedDateTime> getExpiresDate() {
        Optional<String> date = this.getExpiresDateRawString();
        if (date.isPresent()) {
            return this.buildDateTimeObject(date.get());
        }
        return Optional.empty();
    }

    public Optional<String> getRawText() {
        return OptionalBuilder.buildOptionalString(this.rawText);
    }

    public Optional<String> getFooter() {
        return OptionalBuilder.buildOptionalString(this.footer);
    }

    public Optional<String> getHeader() {
        return OptionalBuilder.buildOptionalString(this.header);
    }

    public Optional<String> getStrippedText() {
        return OptionalBuilder.buildOptionalString(this.strippedText);
    }

    protected Optional<ZonedDateTime> buildDateTimeObject(String date) {
        return Optional.ofNullable(DateParser.parseIsoDate(date));
    }
}
